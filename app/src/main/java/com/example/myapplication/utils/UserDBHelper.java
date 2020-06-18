package com.example.myapplication.utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.MD5Utils;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.User;


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
        db.execSQL("insert into user values('admin', 'e15015a3b9df6b0da040e2e557a7150c', 'admin', '', '计算机学院', 1);");
        db.execSQL("insert into user values('user', 'e15015a3b9df6b0da040e2e557a7150c', 'user', '', '计算机学院', 2);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
