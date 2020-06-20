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
        db.execSQL("insert into awards values('201842', '王二','计算机系', '蓝桥杯校赛','冠军')");
        db.execSQL("insert into awards values('201899', '赵信','计算机系', '蓝桥杯校赛','二等奖')");
        db.execSQL("insert into awards values('201803', '刘海','计算机系', '蓝桥杯校赛','三等奖')");
        db.execSQL("insert into awards values('201817', '刘晓','电信专业', 'acm选拔赛','一等奖')");
        db.execSQL("insert into awards values('201827', '张高','物联网专业', 'acm选拔赛','二等奖')");
        db.execSQL("insert into awards values('201832', '孙吴','计算机系', 'acm选拔赛','冠军')");
        db.execSQL("insert into awards values('201877', '张楚','计算机系', 'acm校赛','一等奖')");
        db.execSQL("insert into awards values('201867', '张良','计算机系', 'acm校赛','二等奖')");
        db.execSQL("insert into awards values('201846', '王强','计算机系', 'acm校赛','冠军')");
        db.execSQL("insert into awards values('201866', '黄静','计算机系', 'acm校赛','二等奖')");
        db.execSQL("insert into awards values('201896', '孙策贤','计算机系', 'acm校赛','一等奖')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
