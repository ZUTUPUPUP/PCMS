package com.example.myapplication.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.domain.Dep;
import com.example.myapplication.utils.DepTable;
import com.example.myapplication.utils.UserDBHelper;

import java.util.ArrayList;
import java.util.List;

public class DepDao {
    private UserDBHelper dbHelper;

    public DepDao(Context context) {
        dbHelper = new UserDBHelper(context);
    }
    /**
     * 查询所有学院
     * @return
     */
    public List<Dep> findAll() {
        List<Dep> list = new ArrayList<>();
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //执行query select * from user
        Cursor cursor = database.query(DepTable.TAB_NAME, null, null, null, null, null, null);
        //从cursor取出所有数据,并且封装到List中
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            list.add(new Dep(id, name));
        }
        //关闭连接
        cursor.close();
        database.close();
        return list;
    }


    /**
     *
     * @param depName 用户名
     * @return 用户
     */
    public Dep findByDepName(String depName) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //select * from user where userName = 'admin';
        Cursor cursor = database.query(DepTable.TAB_NAME, null,  DepTable.DEP_NAME + "=?", new String[]{depName}, null, null, null);
        Dep dep = null;
        while (cursor.moveToNext()) {
            int department_id = cursor.getInt(0);
            String name = cursor.getString(1);
            dep = new Dep(department_id, name);
        }
        cursor.close();
        database.close();
        return dep;
    }

    /**
     *
     * @param id 用户名
     * @return 用户
     */
    public Dep findById(int id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //select * from user where userName = 'admin';
        Cursor cursor = database.query(DepTable.TAB_NAME, null,  DepTable.DEP_ID + "=" + id, null, null, null, null);
        Dep dep = null;
        while (cursor.moveToNext()) {
            int department_id = cursor.getInt(0);
            String name = cursor.getString(1);
            dep = new Dep(department_id, name);
        }
        cursor.close();
        database.close();
        return dep;
    }
}
