package com.example.myapplication.utils.Manager.Notice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class NoticeDBHelper extends SQLiteOpenHelper {

    public NoticeDBHelper(Context context) {
        super(context, "notice.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NoticeTable.CREATE_TAB);
        db.execSQL("insert into notice values(1, '比赛通知','新生校赛还有3个小时就要开始了，请比赛选手注意！','2020.6.24 15:37','201708026119')");
        db.execSQL("insert into notice values(4, '123','1234','2001','5855')");
        db.execSQL("insert into notice values(3, '1243','41234','2101','7789')");
        db.execSQL("insert into notice values(2, '1239','15234','24001','66554')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
