package com.example.myapplication.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.utils.Manager.Contest.ContestTable;


/**
 * 用户数据库工具类
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
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
        db.execSQL("insert into user values(null, 'admin', 'e15015a3b9df6b0da040e2e557a7150c', 'admin', '女', '1', 1);");
        db.execSQL("insert into user values(null, 'admin1', 'e15015a3b9df6b0da040e2e557a7150c', 'admin1', '男', '1', 1);");
        db.execSQL("insert into user values(null, 'admin2', 'e15015a3b9df6b0da040e2e557a7150c', 'admin2', '男', '1', 1);");
        db.execSQL("insert into user values(null, 'admin3', 'e15015a3b9df6b0da040e2e557a7150c', 'admin3', '男', '1', 1);");
        db.execSQL("insert into user values(null, 'user', 'e15015a3b9df6b0da040e2e557a7150c', '用户', '女', '1', 2);");
        db.execSQL("insert into user values(null, 'user1', 'e15015a3b9df6b0da040e2e557a7150c', '用一', '女', '1', 2);");
        db.execSQL("insert into user values(null, 'user2', 'e15015a3b9df6b0da040e2e557a7150c', '用二', '女', '1', 2);");
        db.execSQL("insert into user values(null, 'user3', 'e15015a3b9df6b0da040e2e557a7150c', '用三', '女', '1', 2);");
        db.execSQL("insert into user values(null, '201708024119', 'e15015a3b9df6b0da040e2e557a7150c', '刘勇', '女', '1', 2);");
        db.execSQL("insert into user values(null, '201708024103', 'e15015a3b9df6b0da040e2e557a7150c', '孙艺苹', '女', '1', 2);");
        db.execSQL("insert into user values(null, '201708024104', 'e15015a3b9df6b0da040e2e557a7150c', '张萌', '女', '1', 2);");
        db.execSQL("insert into user values(null, '201708024105', 'e15015a3b9df6b0da040e2e557a7150c', '刘海铭', '女', '1', 2);");
        db.execSQL("insert into user values(null, '201708024106', 'e15015a3b9df6b0da040e2e557a7150c', '王强强', '女', '1', 2);");
        db.execSQL("insert into user values(null, '201708024107', 'e15015a3b9df6b0da040e2e557a7150c', '陈博', '女', '1', 2);");
        db.execSQL("insert into user values(null, '201708024118', 'e15015a3b9df6b0da040e2e557a7150c', '傅泽浩', '女', '1', 2);");
        db.execSQL("insert into user values(null, '201708024117', 'e15015a3b9df6b0da040e2e557a7150c', '叶家宏', '女', '1', 2);");

        //院系表
        db.execSQL(DepTable.CREATE_TAB);
        db.execSQL("insert into dep values(null, '计算机学院');");
        db.execSQL("insert into dep values(null, '理学院');");
        db.execSQL("insert into dep values(null, '能源与环境学院');");
        db.execSQL("insert into dep values(null, '国际教育学院');");
        db.execSQL("insert into dep values(null, '新闻与传播学院');");
        db.execSQL("insert into dep values(null, '电子信息学院');");

        //比赛表
        db.execSQL(ContestTable.CREATE_TAB);
        db.execSQL("insert into contest values(null, '第十届校赛','第十届校赛报名开始啦.....','2020-6-21','无');");
        db.execSQL("insert into contest values(null, '第十一届校赛','第十一届校赛报名开始啦.....','2020-6-21','无');");
        db.execSQL("insert into contest values(null, '第十二届校赛','第十二届校赛报名开始啦.....','2020-6-21','无');");
        db.execSQL("insert into contest values(null, 'acm新生赛','acm新生赛报名开始啦.....','2020-6-21','无');");
        db.execSQL("insert into contest values(null, 'ICPC','ICPC校赛报名开始啦.....','2020-6-21','无');");
        //报名表
        db.execSQL(ContestRegistryTable.CREATE_TAB);
        db.execSQL("insert into contestregistry values(null, 1, 14, 1, '计科171', '女', '1738871130@qq.com', '陈博')");
        db.execSQL("insert into contestregistry values(null, 3, 14, 1, '计科171', '女', '1738871130@qq.com', '陈博')");
        db.execSQL("insert into contestregistry values(null, 4, 14, 1, '计科171', '女', '1738871130@qq.com', '陈博')");
        db.execSQL("insert into contestregistry values(null, 5, 10, 1, '计科171', '女', '1738871130@qq.com', '孙艺苹')");
        db.execSQL("insert into contestregistry values(null, 5, 9, 1, '计科171', '女', '1738871130@qq.com', '刘勇')");
        db.execSQL("insert into contestregistry values(null, 5, 11, 1, '计科171', '女', '1738871130@qq.com', '张萌')");
        db.execSQL("insert into contestregistry values(null, 5, 13, 1, '计科171', '女', '1738871130@qq.com', '王强强')");
        db.execSQL("insert into contestregistry values(null, 1, 5, 1, '计科171', '男', 'test@qq.com', '用户')");
        db.execSQL("insert into contestregistry values(null, 2, 5, 1, '计科171', '男', 'test@qq.com', '用户')");
        db.execSQL("insert into contestregistry values(null, 3, 5, 1, '计科171', '男', 'test@qq.com', '用户')");
        db.execSQL("insert into contestregistry values(null, 4, 6, 1, '计科171', '男', 'test@qq.com', '用户')");
        db.execSQL("insert into contestregistry values(null, 5, 5, 1, '计科171', '男', 'test@qq.com', '用户')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
