package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.domain.Contact;
import com.example.myapplication.domain.User;
import com.example.myapplication.utils.ContactDBHelper;
import com.example.myapplication.utils.ContactTable;
import com.example.myapplication.utils.UserDBHelper;
import com.example.myapplication.utils.UserTable;

import java.util.ArrayList;
import java.util.List;

public class ContactDao {
    private ContactDBHelper dbHelper;

    public ContactDao(Context context) {
        dbHelper = new ContactDBHelper(context);
    }

    public List<Contact> findAll() {
        List<Contact> list = new ArrayList<>();
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //执行query select * from user
        Cursor cursor = database.query(ContactTable.TAB_NAME,null,null,null,null,null,null);
        //从cursor取出所有数据,并且封装到List中
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String date  = cursor.getString(1);
            String senderId = cursor.getString(2);
            String receiver = cursor.getString(3);
            String mas = cursor.getString(4);
            list.add(new Contact(_id, date, senderId, receiver, mas));
        }
        //关闭连接
        cursor.close();
        database.close();
        return list;
    }


}
