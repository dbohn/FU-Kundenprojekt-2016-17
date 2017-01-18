<?php

namespace humhub\modules\bcs\repositories;

use yii\db\Connection;

class BcsUserRepository
{

    /**
     * @var Connection
     */
    private $postgres;

    public function __construct(Connection $postgres)
    {
        $this->postgres = $postgres;
    }

    /**
     * @param $username
     * @return array|false
     */
    public function getUserByUsername($username)
    {
        return $this->postgres
            ->createCommand("SELECT * FROM users WHERE username=:username")
            ->bindParam(":username", $username)
            ->queryOne();
    }

    public function getGroups()
    {
        return $this->postgres
            ->createCommand("SELECT * FROM roles")
            ->queryAll();
    }

    /**
     * Returns all names for groups the given user (bcs id) joined
     *
     * @param $guid
     * @return array
     */
    public function getGroupsForUser($guid)
    {
        return array_map(function ($role) {
            return $role['name'];
        }, $this->postgres
            ->createCommand("SELECT * FROM roles INNER JOIN role_user ON roles.id = role_user.role_id AND role_user.user_id=:guid")
            ->bindParam(":guid", $guid)
            ->queryAll());
    }

    /**
     * Returns all guids for spaces the given user (bcs id) joined
     *
     * @param $guid
     * @return array
     */
    public function getSpacesForUser($guid)
    {
        return array_map(function ($role) {
            return $role['id'];
        }, $this->postgres
            ->createCommand("SELECT * FROM projects INNER JOIN project_user ON projects.id = project_user.project_id AND project_user.user_id=:guid")
            ->bindParam(":guid", $guid)
            ->queryAll());
    }
}
