package com.example.myapplication.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * 用户数据库工具类
 */
public class UserDBHelper extends SQLiteOpenHelper {
    public UserDBHelper(Context context) {
        super(context, "contest.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //身份表status:管理员,普通用户
        db.execSQL(UserStatusTable.CREATE_TAB);
        //insert into status values(null, '管理员');
        db.execSQL("insert into status values(null, '管理员');");
        db.execSQL("insert into status values(null, '普通用户');");
        //用户表user:
        db.execSQL(UserTable.CREATE_TAB);
        //String passWd = MD5Utils.md5("zut_acm54321");
        //Log.v("MyInfo", passWd);
        db.execSQL("insert into user values('admin', 'e15015a3b9df6b0da040e2e557a7150c', 'admin', '女', '1', 1);");
        db.execSQL("insert into user values('user', 'e15015a3b9df6b0da040e2e557a7150c', 'user', '女', '1', 2);");
        //院系表
        db.execSQL(DepTable.CREATE_TAB);
        db.execSQL("insert into dep values(null, '计算机学院');");
        db.execSQL("insert into dep values(null, '理学院');");
        db.execSQL("insert into dep values(null, '能源与环境学院');");
        db.execSQL("insert into dep values(null, '国际教育学院');");
        db.execSQL("insert into dep values(null, '新闻与传播学院');");
        db.execSQL("insert into dep values(null, '电子信息学院');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
