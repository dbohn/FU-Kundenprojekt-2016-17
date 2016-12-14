<?php

namespace humhub\modules\bcs\auth;

class PasswordChecker
{

    public function validatePassword($user, $password)
    {
        // TODO: If this primary client stuff works, we should implement stronger passwords
        $hash = $user['password'];

        return password_verify($password, $hash);
    }
}
