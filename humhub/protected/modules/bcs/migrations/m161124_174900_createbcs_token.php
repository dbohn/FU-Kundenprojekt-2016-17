<?php

use yii\db\Migration;

class m161124_174900_createbcs_token extends Migration
{

    public function up()
    {
        $this->createTable('bcs_token', [
            'id' => 'pk',
            'token' => 'varchar(255)',
            'comment' => 'text',
            'created_at' => 'timestamp',
            'updated_at' => 'timestamp',
            'created_by' => 'integer',
        ], '');
    }

    public function down()
    {
        $this->dropTable('bcs_token');
    }
}
