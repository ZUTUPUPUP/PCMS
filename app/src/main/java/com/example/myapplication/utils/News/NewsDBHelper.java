package com.example.myapplication.utils.News;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//和管理员聊天，存放所有人和管理员聊天记录的database
public class NewsDBHelper extends SQLiteOpenHelper {


    public NewsDBHelper(Context context) {
        super(context, "news.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NewsTable.CREATE_TAB);
        // for(int i=1;i<=10;i++)
        db.execSQL("insert into News values(0, -1,'竞赛0\n通知','2020-6-27 21:20', '   这是通知通知通知通知这是通知通知通知通知这是通知通知通知通知这是通知通知通知通知 这是通知通知通知通知 这是通知通知通知通知 这是通知通知通知通知' ," +
                "'第0届竞赛','/sdcard/post.jpg','   222222222','reg','4','5','awa','7','8','9','10','11','12')");
        db.execSQL("insert into News values(1, -1,'竞赛1\n通知','2020-6-27 21:21', '   这是通知通知通知通知这是通知通知通知通知这是通知通知通知通知这是通知通知通知通知 这是通知通知通知通知 这是通知通知通知通知 这是通知通知通知通知' ," +
                "'第1届竞赛','/sdcard/post.jpg','   222222222','reg','4','5','awa','7','8','9','10','11','12')");
        db.execSQL("insert into News values(2, -1,'竞赛2\n通知','2020-6-27 21:22', '   这是通知通知通知通知这是通知通知通知通知这是通知通知通知通知这是通知通知通知通知 这是通知通知通知通知 这是通知通知通知通知 这是通知通知通知通知' ," +
                "'第2届竞赛','/sdcard/post.jpg','   222222222','reg','4','5','awa','7','8','9','10','11','12')");
        db.execSQL("insert into News values(3, 1,'竞赛3\n通知','2020-6-27 21:23', '   这是通知通知通知通知这是通知通知通知通知这是通知通知通知通知这是通知通知通知通知 这是通知通知通知通知 这是通知通知通知通知 这是通知通知通知通知' ," +
                "'第3届竞赛','/sdcard/post.jpg','   222222222','reg','4','5','awa','7','8','9','10','11','12')");
        db.execSQL("insert into News values(4, 2,'竞赛4\n通知','2020-6-27 21:24', '   这是通知通知通知通知这是通知通知通知通知这是通知通知通知通知这是通知通知通知通知 这是通知通知通知通知 这是通知通知通知通知 这是通知通知通知通知' ," +
                "'第4届竞赛','/sdcard/post.jpg','   222222222','reg','4','5','awa','7','8','9','10','11','12')");
        db.execSQL("insert into News values(5, 3,'竞赛5\n通知','2020-6-27 21:25', '   这是通知通知通知通知这是通知通知通知通知这是通知通知通知通知这是通知通知通知通知 这是通知通知通知通知 这是通知通知通知通知 这是通知通知通知通知' ," +
                "'第5届竞赛','/sdcard/post.jpg','   222222222','reg','4','5','awa','7','8','9','10','11','12')");
        db.execSQL("insert into News values(6, 4,'新生赛\n报名和获奖','2020-6-29 22:13', '   这是通知通知通知通知这是通知通知通知通知这是通知通知通知通知这是通知通知通知通知 这是通知通知通知通知 这是通知通知通知通知 这是通知通知通知通知' ," +
                "'中原工学院新生赛','/sdcard/post.jpg','   中原工学院新生赛报名点击跳转','reg','4','中原工学院新生赛获奖情况点击跳转','awa','7','8','9','10','11','12')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
