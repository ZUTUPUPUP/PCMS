package com.example.myapplication.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

//和管理员聊天，存放所有人和管理员聊天记录的database
public class ContactDBHelper extends SQLiteOpenHelper {


    public ContactDBHelper(Context context) {
        super(context, "contact.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactTable.CREATE_TAB);
        db.execSQL("insert into contact values(1, '11:11','admin', '12','admin say hello to 12')");
        db.execSQL("insert into contact values(2, '11:11','admin', '12','admin say hello to 12')");
        db.execSQL("insert into contact values(3, '11:11','12', 'admin','admin 12 hello to admin')");
        /*
        for(int i=1;i<=20;i++){
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
            String s0 = dateFormat.format( now );
            String s1 =String.valueOf(i);
            db.execSQL("insert into contact values(i, hehe,s1, s1,s1)");
        }
        */

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
