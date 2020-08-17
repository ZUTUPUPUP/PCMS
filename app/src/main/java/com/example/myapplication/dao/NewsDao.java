package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.domain.Contact;
import com.example.myapplication.domain.Dep;
import com.example.myapplication.domain.News;
import com.example.myapplication.utils.Contact.ContactDBHelper;
import com.example.myapplication.utils.Contact.ContactTable;
import com.example.myapplication.utils.DepTable;
import com.example.myapplication.utils.News.NewsDBHelper;
import com.example.myapplication.utils.News.NewsTable;

import java.util.ArrayList;
import java.util.List;

public class NewsDao {
    private NewsDBHelper dbHelper;
    public NewsDao(Context context) {
        dbHelper = new NewsDBHelper(context);
    }
   /* public List<News> findAll() {
        List<News> list = new ArrayList<>();
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //执行query select * from user
        Cursor cursor = database.query(NewsTable.TAB_NAME,null,null,null,null,null,null);
        //从cursor取出所有数据,并且封装到List中
        while (cursor.moveToNext()) {
            list.add(new News(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),
                    cursor.getString(8), cursor.getString(9), cursor.getString(10),
                    cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14)
                    , cursor.getString(15), cursor.getString(16),cursor.getString(17)));
        }
        //关闭连接
        cursor.close();
        database.close();
        return list;
    }
    public void insertOne(News news){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("contestId",news.getContestId());
        values.put("date",news.getDate());
        values.put("head",news.getHead());
        values.put("brief",news.getBrief());
        values.put("t1",news.getT1());values.put("t4",news.getT4());values.put("t7",news.getT7());values.put("t10",news.getT10());
        values.put("t2",news.getT2());values.put("t5",news.getT5());values.put("t8",news.getT8());values.put("t11",news.getT11());
        values.put("t3",news.getT3());values.put("t6",news.getT6());values.put("t9",news.getT9());values.put("t12",news.getT12());
        values.put("t0",news.getT0());
        long id = database.insert(NewsTable.TAB_NAME, null, values);
        Log.v("INSERTNEWS", "id = " + id);
        database.close();
    }*/
   /*
    public  News findById(int id){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(NewsTable.TAB_NAME, null,  NewsTable._id + "=" + id, null, null, null, null);
        News news = null;
        while (cursor.moveToNext()) {
            news = new News(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),
                    cursor.getString(8), cursor.getString(9), cursor.getString(10),
                    cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14)
                    , cursor.getString(15), cursor.getString(16),cursor.getString(17));

        }
        cursor.close();
        database.close();
        return news;
    }*/
}
