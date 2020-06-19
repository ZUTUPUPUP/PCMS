package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.domain.AwardsInfo;
import com.example.myapplication.utils.Manager.Awards.AwardsDBHelper;
import com.example.myapplication.utils.Manager.Awards.AwardsTable;

import java.util.ArrayList;
import java.util.List;

public class AwardsDao {
    private AwardsDBHelper dbHelper;

    public AwardsDao(Context context) {
        dbHelper = new AwardsDBHelper(context);
    }

    /**
     * 添加一条获奖记录
     * @param awardsInfo 获奖信息
     */
    public void add(AwardsInfo awardsInfo) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(AwardsTable.USER_ID, awardsInfo.getUserId().trim());
        values.put(AwardsTable.USER_NAME, awardsInfo.getUserName());
        values.put(AwardsTable.COLLEGE,awardsInfo.getCollege());
        values.put(AwardsTable.COMPETITION_TYPE,awardsInfo.getCompetitionType());
        values.put(AwardsTable.AWARD_LEVEL,awardsInfo.getAwardLevel());
        long id = database.insert(AwardsTable.TAB_NAME, null, values);
        Log.v("MyInfo", "id = " + id);
        database.close();
    }

    /**
     * 根据用户学号删除一条获奖信息
     * @param userId 用户学号
     */
    public void deleteByUserId(String userId) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        int deleteCount = database.delete(AwardsTable.TAB_NAME, AwardsTable.USER_ID + "=?", new String[]{userId});
        Log.v("MyInfo", "deleteCount = " + deleteCount);
        database.close();
    }

    /**
     * 更新一条记录
     * @param awardsInfo 需要更新信息的获奖信息
     */
    public void update(AwardsInfo awardsInfo) {
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(AwardsTable.USER_ID, awardsInfo.getUserId().trim());
        values.put(AwardsTable.USER_NAME, awardsInfo.getUserName());
        values.put(AwardsTable.COLLEGE,awardsInfo.getCollege());
        values.put(AwardsTable.COMPETITION_TYPE,awardsInfo.getCompetitionType());
        values.put(AwardsTable.AWARD_LEVEL,awardsInfo.getAwardLevel());
        int updateCount = database.update(AwardsTable.TAB_NAME, values,  AwardsTable.USER_ID + "=" + "'" + awardsInfo.getUserId() + "'", null);
        Log.v("MyInfo", "updateCount = " + updateCount);
        //关闭连接
        database.close();
    }

    /**
     * 查询所有用户
     * @return
     */
    public List<AwardsInfo> findAll() {
        List<AwardsInfo> list = new ArrayList<>();
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //执行query select * from user
        Cursor cursor = database.query(AwardsTable.TAB_NAME, null, null, null, null, null, null);
        //从cursor取出所有数据,并且封装到List中
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name  = cursor.getString(1);
            String college = cursor.getString(2);
            String competitionType = cursor.getString(3);
            String awardLevel = cursor.getString(4);
            list.add(new AwardsInfo(id, name, college, competitionType, awardLevel));
        }
        //关闭连接
        cursor.close();
        database.close();
        return list;
    }

    public AwardsInfo findByUserId(String userId) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(AwardsTable.TAB_NAME, null,  AwardsTable.USER_ID + "=?", new String[]{userId}, null, null, null);
        AwardsInfo awardsInfo = null;
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name  = cursor.getString(1);
            String college = cursor.getString(2);
            String type = cursor.getString(3);
            String level = cursor.getString(4);
            awardsInfo = new AwardsInfo(id,name,college,type,level);
        }
        cursor.close();
        database.close();
        return awardsInfo;
    }
}
