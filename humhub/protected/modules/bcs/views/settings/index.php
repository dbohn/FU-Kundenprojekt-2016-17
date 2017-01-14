<?php

use yii\helpers\Url;

?>
<div class="panel panel-default">
    <div class="panel-heading">
        BCS Rollen
    </div>

    <hr>

    <div class="panel-body">
        <table class="table">
            <thead>
            <tr>
                <th>#</th>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>
            <?php foreach ($groups as $group): ?>
                <tr>
                    <td><?= $group->bcs->id ?></td>
                    <td><?= $group->bcs->name ?></td>
                    <td>
                        <?php if (!$group->humhub): ?>
                            <a href="<?= Url::to('/bcs/settings/sync') ?>?group=<?= $group->bcs->name ?>">
                                <i>importieren</i>
                            </a>
                        <?php else: ?>
                            <i>vorhanden</i>
                        <?php endif; ?>
                    </td>
                </tr>
            <?php endforeach; ?>
            </tbody>
        </table>
    </div>
</div>

<div class="panel panel-default">
    <div class="panel-heading">
        BCS Einstellugen
    </div>

    <hr>

    <div class="panel-body">
        <a class="btn btn-primary" href="<?= Url::to('/bcs/settings/add') ?>">
            Create Token
        </a>

        <table class="table">
            <thead>
            <tr>
                <th>#</th>
                <th>token</th>
                <th>comment</th>
                <th>created at</th>
            </tr>
            </thead>
            <tbody>
            <?php foreach ($tokens as $token): ?>
                <tr>
                    <td><?= $token->id ?></td>
                    <td><code><?= $token->token ?></code></td>
                    <td><?= $token->comment ?></td>
                    <td><?= $token->created_at ?></td>
                </tr>
            <?php endforeach; ?>
            </tbody>
        </table>
    </div>
</div>