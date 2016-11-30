<?php


use yii\db\Migration;

class m161130_115217_create_user_bcs_table extends Migration
{


    public function up()
    {
        $this->createTable('user_bcs', [
            'id' => 'pk',
            'user_id' => 'integer',
            'bcs_id' => 'varchar(255)',
        ], '');
    }

    public function down()
    {
        $this->dropTable('user_bcs');
    }

}
