<?php

use yii\db\Schema;
use yii\db\Migration;

class m161124_174900_createbcs_token extends Migration
{

    public function up()
    {
        $this->createTable('bcs_token', array(
            'id' => 'pk',
            'token' => 'varchar(255)',
            'comment' => 'text',
        ), '');
    }

    public function down()
    {
        $this->dropTable('bcs_token');
    }
}
