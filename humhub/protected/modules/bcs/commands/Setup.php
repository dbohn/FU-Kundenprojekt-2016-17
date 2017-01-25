<?php

namespace humhub\modules\bcs\commands;

use humhub\commands\MigrateController;
use humhub\components\Module;
use humhub\components\SettingsManager;
use humhub\libs\DynamicConfig;
use humhub\libs\UUID;
use humhub\modules\admin\commands\ModuleController;
use humhub\modules\installer\libs\InitialData;
use humhub\modules\space\models\Space;
use humhub\modules\user\models\Group;
use humhub\modules\user\models\Password;
use humhub\modules\user\models\User;
use Yii;
use yii\base\Exception;
use yii\console\Controller;
use yii\console\controllers\CacheController;
use yii\db\Expression;
use yii\helpers\Console;

/**
 *
 */
class Setup extends Controller
{

    const ADMIN_USERNAME = "Admin";
    const ADMIN_PASSWORD = "admin";

    /**
     * Perform a silent HumHub installation.
     */
    public function actionInstall()
    {
        if (Yii::$app->params['installed']) {
            $this->error("Application already installed.");
            return self::EXIT_CODE_ERROR;
        }

        $this->info("Migrating the Database:");

        $this->runMigrations();

        $this->flushCache();

        $this->setDatabaseInstalled();

        $this->success("The database was successfully migrated...");

        $this->info("Install some initial data...");
        $this->setupInitialData();

        $this->success("And this is done. Easy, right?");

        $this->setCache();

        $this->info("We have to set some basic configuration values. Give us just a second.");

        $this->setBaseSettings();

        $this->info("We will create an admin user now.");
        $this->info("He will be called " . self::ADMIN_USERNAME . " with password " . self::ADMIN_PASSWORD . ". Please change that!");

        $this->createAdminUser();

        $this->info("Before we go, we will set some sensible default configuration options.");

        $this->configureSystem();

        $this->info("We install the mail extension for the chat component");

        $this->installMailModule();

        $this->setInstalled();

        return self::EXIT_CODE_NORMAL;
    }

    protected function runMigrations()
    {
        $controller = new MigrateController('migrate', Yii::$app);
        $controller->db = Yii::$app->db;
        $controller->interactive = false;
        $controller->includeModuleMigrations = true;
        $controller->color = false;
        $controller->runAction('up');
    }

    private function setupInitialData()
    {
        InitialData::bootstrap();
    }

    protected function info($message)
    {
        return $this->stdout($message . "\n", Console::FG_YELLOW);
    }

    protected function success($message)
    {
        return $this->stdout($message . "\n", Console::FG_GREEN);
    }

    protected function error($message)
    {
        return $this->stderr($message . "\n", Console::FG_RED);
    }

    /**
     * Sets application in installed state (disables installer)
     */
    protected function setInstalled()
    {
        $config = DynamicConfig::load();
        $config['params']['installed'] = true;
        DynamicConfig::save($config);
    }

    /**
     * Sets application database in installed state
     */
    protected function setDatabaseInstalled()
    {
        $config = DynamicConfig::load();
        $config['params']['databaseInstalled'] = true;
        DynamicConfig::save($config);
    }

    private function createAdminUser()
    {
        $user = new User();
        $user->username = self::ADMIN_USERNAME;
        $user->email = 'humhub@example.com';
        $user->status = User::STATUS_ENABLED;
        $user->language = '';
        $user->last_activity_email = new Expression('NOW()');
        if (!$user->save()) {
            throw new Exception("Could not save user");
        }

        // Set basic profile information
        $user->profile->title = "System Administration";
        $user->profile->firstname = "Admin";
        $user->profile->lastname = "Istrator";
        $user->profile->save();

        // Set the user password
        $password = new Password();
        $password->user_id = $user->id;
        $password->setPassword(self::ADMIN_PASSWORD);
        $password->save();

        // Add User to Admin group
        Group::getAdminGroup()->addUser($user);
    }

    private function setBaseSettings()
    {
        /** @var SettingsManager $settings */
        $settings = Yii::$app->settings;
        $settings->set('name', "HumHub");
        $settings->set('mailer.systemEmailName', "humhub@example.com");
        $settings->set('mailer.systemEmailAddress', "humhub@example.com");
        $settings->set('secret', UUID::v4());
        $settings->set('timeZone', 'Europe/Berlin');
    }

    private function configureSystem()
    {
        $this->info("Enable friendship, because you've got a friend in me!");
        Yii::$app->getModule('friendship')->settings->set('enable', true);

        $this->info("But we do not want to allow guest access.");
        Yii::$app->getModule('user')->settings->set('auth.allowGuestAccess', false);

        $this->info("Added users do not need approval.");
        Yii::$app->getModule('user')->settings->set('auth.needApproval', false);
        $this->info("Disable anonymous registration");
        Yii::$app->getModule('user')->settings->set('auth.anonymousRegistration', false);

        $this->info("Disable user invitations via e-mail.");
        Yii::$app->getModule('user')->settings->set('auth.internalUsersCanInvite', false);

        $this->info("The default HumHub theme is beautiful, but the custom one is even better");
        Yii::$app->settings->set('theme', "BCSHumHub");
        Yii::$app->getModule('space')->settings->set('spaceOrder', 0);

        $this->info("Allow Posting from Dashboard View");
        Yii::$app->settings->set('showProfilePostForm', true);

        // read and save colors from current theme
        \humhub\components\Theme::setColorVariables('BCSHumHub');

        $this->info("Create the obligatory welcome space");

        $adminUser = User::findOne(['id' => 1]);

        $space = new Space();
        $space->name = Yii::t("InstallerModule.controllers_ConfigController", "Welcome Space");
        $space->description = Yii::t("InstallerModule.controllers_ConfigController", "Your first sample space to discover the platform.");
        $space->join_policy = Space::JOIN_POLICY_FREE;
        $space->visibility = Space::VISIBILITY_ALL;
        $space->created_by = $adminUser->id;
        $space->auto_add_new_members = 1;
        $space->color = '#6fdbe8';
        $space->save();
    }

    private function flushCache()
    {
        $controller = new CacheController("cache", Yii::$app);

        $controller->runAction('flush-all');
    }

    private function setCache()
    {
        Yii::$app->settings->set('cache.class', 'yii\caching\ApcCache');

        //$this->flushCache();
    }

    private function installMailModule()
    {
        $this->info("Install module...");
        $controller = new ModuleController('module', Yii::$app);

        $controller->runAction('install', ['mail']);

        /** @var Module $module */
        $module = Yii::$app->moduleManager->getModule('mail');

        $this->info("Enable module...");
        $module->enable();

    }
}
