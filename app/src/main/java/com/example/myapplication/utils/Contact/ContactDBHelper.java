package com.example.myapplication.utils.Contact;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//和管理员聊天，存放所有人和管理员聊天记录的database
public class ContactDBHelper extends SQLiteOpenHelper {


    public ContactDBHelper(Context context) {
        super(context, "contact.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactTable.CREATE_TAB);
       // for(int i=1;i<=10;i++)
        db.execSQL("insert into contact values(0, '2020-6-20 21:40','user', 'admin','yoyoyyoyoyoyyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoyoo')");
        db.execSQL("insert into contact values(1, '2020-6-20 21:41','201708024119', 'admin','2hello admin')");
        db.execSQL("insert into contact values(2, '2020-6-20 21:42','user', 'admin','1hello admin')");
        db.execSQL("insert into contact values(3, '2020-6-20 21:50','user', 'admin','1hello admin')");
        db.execSQL("insert into contact values(4, '2020-6-20 21:50','201708024103', 'admin','3hello admin')");
        db.execSQL("insert into contact values(5, '2020-6-20 21:50','201708024104', 'admin','1hello admin')");
        db.execSQL("insert into contact values(6, '2020-6-20 23:50','user', 'admin','1hello admin')");
        db.execSQL("insert into contact values(7, '2020-6-20 21:50','user', 'admin','1hello admin')");
        db.execSQL("insert into contact values(8, '2020-6-20 21:50','user', 'admin','1hello admin')");
        db.execSQL("insert into contact values(9, '2020-6-20 21:51','201708024105', 'admin','4hello admin')");
        db.execSQL("insert into contact values(10, '2020-6-20 21:52','201708024106', 'admin','hello admin')");
        db.execSQL("insert into contact values(11, '2020-6-20 21:52','201708024107', 'admin','hello admin')");
        db.execSQL("insert into contact values(12, '2020-6-20 23:54','user2', 'admin','hello admin')");
        db.execSQL("insert into contact values(13, '2020-6-20 21:54','user1', 'admin','hello admin')");
        db.execSQL("insert into contact values(14, '2020-6-20 21:55','user3', 'admin','hello admin')");
        db.execSQL("insert into contact values(15, '2020-6-20 21:55','user', 'admin','hello admin')");
        db.execSQL("insert into contact values(16, '2020-6-20 21:55','user', 'admin','hello admin')");
        db.execSQL("insert into contact values(17, '2020-6-20 21:55','user', 'admin','1hello admin')");
        db.execSQL("insert into contact values(18, '2020-6-20 23:50','user', 'admin','1hello admin')");
        db.execSQL("insert into contact values(19, '2020-6-20 23:54','admin', '201708024119','whis up?')");
        db.execSQL("insert into contact values(20, '2020-6-20 23:55','201708024119', 'admin','yoyoyo')");
        db.execSQL("insert into contact values(21, '2020-6-20 23:58','201708024103', 'admin','oyoyoyoyoyyoyooyyoyoyoyyoyoyoyoyoyyyoyoyoyoyoyyoyoyoyoyoyoyoyyoyoyoyooyy')");
        db.execSQL("insert into contact values(22, '2020-6-21 00:14','admin', '201708024119','???')");
        db.execSQL("insert into contact values(null, '2020-6-21 00:50','201708024119', 'admin','合合合合合合合合合合合合合合合合合合合合合合合合合合合合合合合合合合合合')");
       // for(int i=11;i<=20;i++)
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
