package com.example.myapplication.utils.Manager.Awards;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AwardsDBHelper extends SQLiteOpenHelper {
    public AwardsDBHelper(Context context) {
        super(context, "awards.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AwardsTable.CREATE_TAB);
        db.execSQL("insert into awards values(null, '201708024103', '王平', '计科171', '蓝桥杯校赛', '一等奖', '计算机学院')");
        db.execSQL("insert into awards values(null, '201708024104', '侯晓', '计科171', '蓝桥杯校赛', '二等奖', '计算计学院')");
        db.execSQL("insert into awards values(null, '201708024105', '王二', '计科171', '蓝桥杯校赛', '冠军', '计算计学院')");
        db.execSQL("insert into awards values(null, '201708024106', '赵信', '计科171', '蓝桥杯校赛', '二等奖', '计算计学院')");
        db.execSQL("insert into awards values(null, '201708024107', '刘海', '计科172', '蓝桥杯校赛', '三等奖', '计算计学院')");
        db.execSQL("insert into awards values(null, '201708024108', '刘晓', '计科172', 'acm新生赛', '一等奖', '计算计学院')");
        db.execSQL("insert into awards values(null, '201708024109', '张高', '计科173', 'acm新生赛', '二等奖', '计算计学院')");
        db.execSQL("insert into awards values(null, '201708024110', '孙吴', '计科174', 'acm新生赛', '冠军', '理学院')");
        db.execSQL("insert into awards values(null, '201708024111', '张楚', '计科175', 'acm新生赛', '一等奖', '计算计学院')");
        db.execSQL("insert into awards values(null, '201708024112', '张良', '计科176', 'acm新生赛', '二等奖', '能源与环境学院')");
        db.execSQL("insert into awards values(null, '201708024113', '刘辉', '计科177', 'acm新生赛', '冠军', '计算计学院')");
        db.execSQL("insert into awards values(null, '201708024114', '黄静', '计科178', 'acm校赛', '二等奖', '理学院')");
        db.execSQL("insert into awards values(null, '201708024115', '孙策贤', '计科179', 'acm校赛', '一等奖', '新闻与传播学院')");
        db.execSQL("insert into awards values(null, 'user', '用户', '计科179', 'acm校赛', '一等奖', '计算机学院')");
        db.execSQL("insert into awards values(null, 'user', '用户', '计科179', '蓝桥杯校赛', '一等奖', '计算机学院')");
        db.execSQL("insert into awards values(null, 'user', '用户', '计科179', 'acm选拔赛', '一等奖', '计算机学院')");
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
