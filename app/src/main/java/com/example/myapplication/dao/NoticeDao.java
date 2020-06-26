package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.domain.Notice;
import com.example.myapplication.utils.Manager.Notice.NoticeDBHelper;
import com.example.myapplication.utils.Manager.Notice.NoticeTable;

import java.util.ArrayList;
import java.util.List;

public class NoticeDao {
    private NoticeDBHelper dbHelper;

    public NoticeDao(Context context) {
        dbHelper = new NoticeDBHelper(context);
    }

    /**
     * 添加一条通知
     * @param notice 通知
     */
    public void add(Notice notice) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(NoticeTable.NOTICE_ID, notice.get_id());
        values.put(NoticeTable.NOTICE_TITLE, notice.getTitle());
        values.put(NoticeTable.NOTICE_CONTENT, notice.getContent());
        values.put(NoticeTable.NOTICE_TIME, notice.getTime());
        values.put(NoticeTable.NOTICE_RECEIVER, notice.getReceiver());
        long id = database.insert(NoticeTable.TAB_NAME, null, values);
        Log.v("MyInfo", "id = " + id);
        database.close();
    }

    /**
     * 根据_id删除一条通知
     * @param _id 信息编号
     */
    public void deleteById(int _id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        int deleteCount = database.delete(NoticeTable.TAB_NAME, NoticeTable.NOTICE_ID + "=?", new String[]{_id+""});
        Log.v("MyInfo", "deleteCount = " + deleteCount);
        database.close();
    }

    /**
     * 更新一条记录
     * @param notice 需要更新的通知
     */
    public void update(Notice notice) {
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(NoticeTable.NOTICE_ID, notice.get_id());
        values.put(NoticeTable.NOTICE_TITLE, notice.getTitle());
        values.put(NoticeTable.NOTICE_CONTENT, notice.getContent());
        values.put(NoticeTable.NOTICE_TIME, notice.getTime());
        values.put(NoticeTable.NOTICE_RECEIVER, notice.getReceiver());
        int updateCount = database.update(NoticeTable.TAB_NAME, values,  NoticeTable.NOTICE_ID + "=" + "'" + notice.get_id() + "'", null);
        Log.v("MyInfo", "updateCount = " + updateCount);
        //关闭连接
        database.close();
    }

    /**
     * 查询所有通知
     * @return
     */
    public List<Notice> findAll() {
        List<Notice> list = new ArrayList<>();
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //执行query select * from notice
        Cursor cursor = database.query(NoticeTable.TAB_NAME, null, null, null, null, null, NoticeTable.NOTICE_ID+" desc");
        //从cursor取出所有数据,并且封装到List中
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content  = cursor.getString(2);
            String time = cursor.getString(3);
            String receiver = cursor.getString(4);
            list.add(new Notice(_id, title, content, time, receiver));
        }
        //关闭连接
        cursor.close();
        database.close();
        return list;
    }

    /**
     * 根据编号查询通知
     * @return
     */
    public Notice findByNoticeId(int _id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(NoticeTable.TAB_NAME, null,  NoticeTable.NOTICE_ID + "=?", new String[]{_id + ""}, null, null, null);
        Notice notice = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content  = cursor.getString(2);
            String time = cursor.getString(3);
            String receiver = cursor.getString(4);
            notice = new Notice(id, title, content, time, receiver);
        }
        cursor.close();
        database.close();
        return notice;
    }
    /**
     * 查询空缺id
     */
    public int findEmptyNoticeId() {
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //执行query select * from notice
        Cursor cursor = database.query(NoticeTable.TAB_NAME, null, null, null, null, null, NoticeTable.NOTICE_ID);
        //从cursor取出所有数据,并且封装到List中
        int id=-1,sum=1;
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            if(_id!=sum){
                id=sum;break;
            }
            ++sum;
        }
        //关闭连接
        cursor.close();
        database.close();
        return id==-1?sum:id;
    }
    /**
     * 根据标题查询部分通知
     * @return
     */
    public List<Notice> findByTitle(String title) {
        List<Notice> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String sql="select * from notice where title like '%" + title + "%' order by _id desc;";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String _title = cursor.getString(1);
            String content  = cursor.getString(2);
            String time = cursor.getString(3);
            String receiver = cursor.getString(4);
            list.add(new Notice(_id, title, content, time, receiver));
        }
        cursor.close();
        database.close();
        return list;
    }
}
