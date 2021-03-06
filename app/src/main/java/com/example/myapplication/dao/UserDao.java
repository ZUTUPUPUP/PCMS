package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.domain.User;
import com.example.myapplication.utils.DBHelper;
import com.example.myapplication.utils.MD5Utils;
import com.example.myapplication.utils.UserTable;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户增删改查
 */
public class UserDao {
    private DBHelper dbHelper;

    public UserDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    /**
     * 添加一条记录(用户注册,默认为普通用户)
     * @param user 添加的用户
     */
    public void add(User user) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserTable.USER_ID, user.get_id());
        values.put(UserTable.USER_NAME, user.getUserName().trim());
        values.put(UserTable.USER_PASSWD, MD5Utils.md5(user.getPasswd().trim()));
        String nickName = user.getNickName().trim();
        values.put(UserTable.USER_NICKNAME, nickName.isEmpty() ? user.getUserName() : user.getNickName());
        values.put(UserTable.USER_GENDER, user.getGender());
        values.put(UserTable.USER_DEP_ID, user.getDepartment_id());
        values.put(UserTable.USER_STATUS_ID, 2);
        long id = database.insert(UserTable.TAB_NAME, null, values);
        Log.v("MyInfo", "id = " + id);
        database.close();
    }

    /**
     * 根据用户名删除一条用户信息
     * @param userName 用户名
     */
    public void deleteByUserName(String userName) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        int deleteCount = database.delete(UserTable.TAB_NAME, UserTable.USER_NAME + "=?", new String[]{userName});
        Log.v("MyInfo", "deleteCount = " + deleteCount);
        database.close();
    }

    /**
     * 更新一条记录
     * @param user 需要更新信息的用户
     */
    public void update(User user) {
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //执行update update user set user where icon_username=icon_username
        ContentValues values = new ContentValues();
        values.put(UserTable.USER_ID, user.get_id());
        values.put(UserTable.USER_NAME, user.getUserName().trim());
        values.put(UserTable.USER_PASSWD, MD5Utils.md5(user.getPasswd().trim()));
        values.put(UserTable.USER_NICKNAME, user.getNickName().trim().isEmpty() ? user.getUserName():user.getNickName());
        values.put(UserTable.USER_GENDER, user.getGender());
        values.put(UserTable.USER_DEP_ID, user.getDepartment_id());
        values.put(UserTable.USER_STATUS_ID, user.getStatus_id());
        int updateCount = database.update(UserTable.TAB_NAME, values,  UserTable.USER_NAME + "=" + "'" + user.getUserName() + "'", null);
        Log.v("MyInfo", "updateCount = " + updateCount);
        //关闭连接
        database.close();
    }

    /**
     * 查询所有用户
     * @return
     */
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //执行query select * from user
        Cursor cursor = database.query(UserTable.TAB_NAME, null, null, null, null, null, UserTable.USER_ID + " desc");
        //从cursor取出所有数据,并且封装到List中
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String name = cursor.getString(1);
            String passwd  = cursor.getString(2);
            String nickName = cursor.getString(3);
            String gender = cursor.getString(4);
            int department_id = cursor.getInt(5);
            int status_id = cursor.getInt(6);
            list.add(new User(_id, name, passwd, nickName, gender, department_id, status_id));
        }
        //关闭连接
        cursor.close();
        database.close();
        return list;
    }

    /**
     *
     * @param userName 用户名
     * @return 用户
     */
    public User findByUserName(String userName) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //select * from user where userName = 'admin';
        Cursor cursor = database.query(UserTable.TAB_NAME, null,  UserTable.USER_NAME + "=?", new String[]{userName}, null, null, UserTable.USER_ID + " desc");
        User user = null;
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String name = cursor.getString(1);
            String passwd  = cursor.getString(2);
            String nickName = cursor.getString(3);
            String gender = cursor.getString(4);
            int department_id = cursor.getInt(5);
            int status_id = cursor.getInt(6);
            user = new User(_id, name, passwd, nickName, gender, department_id, status_id);
        }
        cursor.close();
        database.close();
        return user;
    }


    /**
     *
     * @param userName 用户名
     * @return 用户
     */
    public List<User> findByLikeUserName(String userName) {
        List<User> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //select * from user where userName = 'admin';
        String sql = "select * from user where userName like '%" + userName + "%' order by _id desc";
        Cursor cursor = database.rawQuery(sql, null);
        User user = null;
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String name = cursor.getString(1);
            String passwd  = cursor.getString(2);
            String nickName = cursor.getString(3);
            String gender = cursor.getString(4);
            int department_id = cursor.getInt(5);
            int status_id = cursor.getInt(6);
            user = new User(_id, name, passwd, nickName, gender, department_id, status_id);
            list.add(user);
        }
        cursor.close();
        database.close();
        return list;
    }
}
