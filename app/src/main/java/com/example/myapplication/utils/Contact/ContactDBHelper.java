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
        db.execSQL("insert into contact values(1, 'THIS IS START','admin', '12','admin say hello to 12')");
        db.execSQL("insert into contact values(2, '11:11','admin', '12','admin say hello to 12')");
        // for(int i=21;i<=30;i++)
        db.execSQL("insert into contact values(3, '11','12', 'admin','admin 12 hello to admin')");
        db.execSQL("insert into contact values(4, '11:11','admin', '12','admin say hello to 12')");
        db.execSQL("insert into contact values(5, '11:11','admin', '12','admin say hello to 12')");
        db.execSQL("insert into contact values(6, '11:11','admin', '12','admin say hello to 12')");
        db.execSQL("insert into contact values(7, '11:11','admin', '12','admin say hello to 12')");
        db.execSQL("insert into contact values(8, '11:11','admin', '12','admin say hello to 12')");
        db.execSQL("insert into contact values(9, '11:11','admin', '12','admin say hello to 12')");
        db.execSQL("insert into contact values(10, 'THIS IS END','admin', '12','admin say hello to 12')");
        db.execSQL("insert into contact values(11, 'THIS IS END','admin', '12','admin say hello to 12')");
        db.execSQL("insert into contact values(12, 'THIS IS END','admin', '12','admin say hello to 12')");
        db.execSQL("insert into contact values(13, 'THIS IS END','admin', '12','admin say hello to 12')");
        db.execSQL("insert into contact values(14, 'THIS IS END','admin', '12','admin say hello to 12')");
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
