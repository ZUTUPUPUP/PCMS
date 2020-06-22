package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.domain.Contact;
import com.example.myapplication.utils.Contact.ContactDBHelper;
import com.example.myapplication.utils.Contact.ContactTable;
import com.example.myapplication.utils.UserTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ContactDao {
    private ContactDBHelper dbHelper;

    public ContactDao(Context context) {
        dbHelper = new ContactDBHelper(context);
    }
    public void insertOneContact(Contact contact){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("date",contact.getTimestamp());
        values.put("senderId",contact.getSenderId());
        values.put("receiverId",contact.getReceiverId());
        values.put("mas",contact.getMas());
        long id = database.insert(ContactTable.TAB_NAME, null, values);
        Log.v("INSERTCONTACT", "id = " + id);
        database.close();
    }
    public List<Contact> findById(String userName){
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
        //筛选user和admin 的聊天记录
        List<Contact> contactList = new ArrayList<>();
          for(Contact c:list){
            if(c.getSenderId().equals("admin")&&c.getReceiverId().equals(userName))
                contactList.add(c);

            if(c.getSenderId().equals(userName)&&c.getReceiverId().equals("admin"))
                contactList.add(c);
        }
        return contactList;

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
