<?php

namespace humhub\modules\bcs;

use humhub\modules\bcs\repositories\BcsUserRepository;
use humhub\modules\space\models\Space;
use humhub\modules\user\models\Group;
use humhub\modules\user\models\User;
use Yii;
use yii\db\ActiveQuery;
use yii\db\Connection;
use yii\helpers\Url;

class Events extends \yii\base\Object
{

    /**
     * @param $event
     */
    public static function onAdminMenuInit($event)
    {
        if (Yii::$app->user->isGuest) {
            return;
        }

        $event->sender->addItem(array(
            'label' => Yii::t('BcsModule.base', 'BCS'),
            'url' => Url::to(['/bcs/settings/index']),
            'icon' => '<i class="fa fa-cog"></i>',
            'isActive' => (Yii::$app->controller->module && Yii::$app->controller->module->id == 'bcs'),
            'sortOrder' => 300,
        ));
    }

    public static function onUserDelete($event)
    {
        //
    }

    /**
     * @param $event
     */
    public static function onUserCreate($event)
    {
        /** @var User $user */
        $user = $event->identity;

        if ($user->auth_mode != 'bcs') {
            return;
        }

        static::syncGroups($user);
        static::syncSpaces($user);
    }

    /**
     * @param $event
     */
    public static function onUserUpdate($event)
    {
        /** @var User $user */
        $user = $event->identity;

        if ($user->auth_mode != 'bcs') {
            return;
        }

        static::syncGroups($user);
        static::syncSpaces($user);
    }

    public static function onConsoleInit($event)
    {
        $application = $event->sender;
        $application->controllerMap['bcs'] = commands\Setup::className();
    }

    private static function syncGroups(User $user)
    {
        $repo = static::userBceRepository();

        $bcsGroupNames = $repo->getGroupsForUser($user->guid);

        /** @var ActiveQuery $query */
        $groups = $user->getGroups()->all();

        /** @var Group $group */
        foreach ($groups as $group) {
            // skip HumHub groups
            if ($group->description != 'bcs_group') {
                continue;
            }

            if (in_array($group->name, $bcsGroupNames)) {
                // remove already joined groups
                unset($bcsGroupNames[array_keys($bcsGroupNames, $group->name)[0]]);
            } else {
                // unjoin group (user is in group inside of humhub but not in bcs
                $group->removeUser($user);
            }
        }

        /**
         * $bcsGroupNames contains all remaining groups not handled above
         *
         * Join all remaining groups
         */
        foreach ($bcsGroupNames as $groupName) {
            // Is group already imported
            if ($group = Group::findOne(['name' => $groupName])) {
                $group->addUser($user);
            } else {
                // TODO: auto import group
            }
        }
    }

    private static function syncSpaces(User $user)
    {
        $repo = static::userBceRepository();

        $bcsSpacesGuids = $repo->getSpacesForUser($user->guid);

        /** @var ActiveQuery $query */
        $spaces = $user->getSpaces()->all();

        /** @var Space $space */
        foreach ($spaces as $space) {
            if (in_array($space->guid, $bcsSpacesGuids)) {
                // remove already joined groups
                unset($bcsSpacesGuids[array_keys($bcsSpacesGuids, $space->guid)[0]]);
            }
        }

        /**
         * $bcsSpacesGuids contains all remaining groups not handled above
         *
         * Join all remaining groups
         */
        foreach ($bcsSpacesGuids as $spaceGuid) {
            // Is group already imported
            if ($space = Space::findOne(['guid' => $spaceGuid])) {
                $space->addMember($user->id);
            } else {
                // TODO: auto import group
            }
        }
    }

    /**
     * Connect to the Postgres Database
     * @return Connection
     */
    protected static function getConnection()
    {
        return new Connection([
            'dsn' => 'pgsql:host=database;port=5432;dbname=humhub',
            'username' => 'humhub',
            'password' => '1234',
            'charset' => 'utf8',
        ]);
    }

    protected static $repo = null;

    /**
     * @return BcsUserRepository
     */
    protected static function userBceRepository()
    {
        if (static::$repo === null) {
            static::$repo = new BcsUserRepository(static::getConnection());
        }

        return static::$repo;
    }
}
