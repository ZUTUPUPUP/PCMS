package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.domain.Message;
import com.example.myapplication.utils.MessageDBHelper;
import com.example.myapplication.utils.MessageTable;

import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    private MessageDBHelper dbHelper;

    public MessageDao(Context context) {
        dbHelper = new MessageDBHelper(context);
    }

    /**
     * 添加一条消息
     * @param message 消息
     */
    public void add(Message message) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(MessageTable.MESSAGE_ID, message.get_id());
        values.put(MessageTable.MESSAGE_USER_ID, message.getUserId());
        values.put(MessageTable.MESSAGE_TITLE, message.getTitle());
        values.put(MessageTable.MESSAGE_CONTENT, message.getContent());
        values.put(MessageTable.MESSAGE_TIME, message.getTime());
        values.put(MessageTable.MESSAGE_VIS, message.getVis());
        long id = database.insert(MessageTable.TAB_NAME, null, values);
        Log.v("MyInfo", "id = " + id);
        database.close();
    }

    /**
     * 用户可根据_id删除一条消息
     * @param _id 消息编号
     */
    public void deleteById(int _id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        int deleteCount = database.delete(MessageTable.TAB_NAME, MessageTable.MESSAGE_ID + "=?", new String[]{_id+""});
        Log.v("MyInfo", "deleteCount = " + deleteCount);
        database.close();
    }

    /**
     * 根据id查询通知
     * @return
     */
    public Message findById(int id){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String sql="select * from message where _id = '"+id+"' ;";
        Cursor cursor = database.rawQuery(sql , null);
        Message message = null;
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String userId = cursor.getString(1);
            String title = cursor.getString(2);
            String content = cursor.getString(3);
            String time = cursor.getString(4);
            String vis = cursor.getString(5);
            message = new Message(_id, userId, title, content, time, vis);
        }
        cursor.close();
        database.close();
        return message;
    }

    /**
     * 根据用户id查询部分通知
     * @return
     */
    public List<Message> findByUserId(String userId) {
        List<Message> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String sql="select * from message where userId = '"+userId+"' order by _id desc;";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String _userId = cursor.getString(1);
            String title = cursor.getString(2);
            String content  = cursor.getString(3);
            String time = cursor.getString(4);
            String vis = cursor.getString(5);
            list.add(new Message(id, _userId, title, content, time, vis));
        }
        cursor.close();
        database.close();
        return list;
    }

}
