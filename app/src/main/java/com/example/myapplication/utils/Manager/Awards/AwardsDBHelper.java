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
        db.execSQL("insert into awards values('201801', '王平','计算机系', '蓝桥杯校赛','一等奖')");
        db.execSQL("insert into awards values('201802', '侯晓','计算机系', '蓝桥杯校赛','二等奖')");
        db.execSQL("insert into awards values('201803', '刘海','计算机系', '蓝桥杯校赛','三等奖')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
