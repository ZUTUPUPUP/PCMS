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
        values.put(AwardsTable.AWARDS_ID, awardsInfo.get_id());
        values.put(AwardsTable.AWARDS_STNUMBER, awardsInfo.getSTNumber().trim());
        values.put(AwardsTable.AWARDS_RELNAME, awardsInfo.getRelName());
        values.put(AwardsTable.AWARDS_CLASSNAME, awardsInfo.getClassName());
        values.put(AwardsTable.AWARDS_CONTESTNAME, awardsInfo.getContestName());
        values.put(AwardsTable.AWARDS_LEVEL, awardsInfo.getAwardLevel());
        values.put(AwardsTable.AWARDS_DEPNAME, awardsInfo.getDepName());
        long id = database.insert(AwardsTable.TAB_NAME, null, values);
        Log.v("MyInfo", "id = " + id);
        database.close();
    }

    /**
     * 根据学生id删除一条获奖信息
     * @param id 用户学号
     */
    public void deleteByUserId(int id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        int deleteCount = database.delete(AwardsTable.TAB_NAME, AwardsTable.AWARDS_ID + "=?", new String[]{id + ""});
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
        values.put(AwardsTable.AWARDS_ID, awardsInfo.get_id());
        values.put(AwardsTable.AWARDS_STNUMBER, awardsInfo.getSTNumber().trim());
        values.put(AwardsTable.AWARDS_RELNAME, awardsInfo.getRelName());
        values.put(AwardsTable.AWARDS_CLASSNAME,awardsInfo.getClassName());
        values.put(AwardsTable.AWARDS_CONTESTNAME,awardsInfo.getContestName());
        values.put(AwardsTable.AWARDS_LEVEL,awardsInfo.getAwardLevel());
        values.put(AwardsTable.AWARDS_DEPNAME,awardsInfo.getDepName());
        int updateCount = database.update(AwardsTable.TAB_NAME, values,  AwardsTable.AWARDS_ID + "=" + "'" + awardsInfo.get_id() + "'", null);
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
        Cursor cursor = database.query(AwardsTable.TAB_NAME, null, null, null, null, null, AwardsTable.AWARDS_ID + " desc");
        //从cursor取出所有数据,并且封装到List中
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String id = cursor.getString(1);
            String name  = cursor.getString(2);
            String college = cursor.getString(3);
            String competitionType = cursor.getString(4);
            String awardLevel = cursor.getString(5);
            String depName = cursor.getString(6);
            list.add(new AwardsInfo(_id, id, name, college, competitionType, awardLevel, depName));
        }
        //关闭连接
        cursor.close();
        database.close();
        return list;
    }

    public AwardsInfo findByUserId(int id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(AwardsTable.TAB_NAME, null,  AwardsTable.AWARDS_ID + "=?", new String[]{id + ""}, null, null, AwardsTable.AWARDS_ID + " desc");
        AwardsInfo awardsInfo = null;
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String id1 = cursor.getString(1);
            String name  = cursor.getString(2);
            String college = cursor.getString(3);
            String competitionType = cursor.getString(4);
            String awardLevel = cursor.getString(5);
            String depName = cursor.getString(6);
            awardsInfo = new AwardsInfo(_id, id1, name, college, competitionType, awardLevel, depName);
        }
        cursor.close();
        database.close();
        return awardsInfo;
    }


    public AwardsInfo findBySTNumberAndContestAndAward(String STNumber, String ContestName, String AwardLevel) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(AwardsTable.TAB_NAME, null,  AwardsTable.AWARDS_STNUMBER + "='" + STNumber + "' and " + AwardsTable.AWARDS_CONTESTNAME + "='" + ContestName + "' and " + AwardsTable.AWARDS_LEVEL + "='" + AwardLevel + "'", null, null, null, null);
        AwardsInfo awardsInfo = null;
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String id1 = cursor.getString(1);
            String name  = cursor.getString(2);
            String college = cursor.getString(3);
            String competitionType = cursor.getString(4);
            String awardLevel = cursor.getString(5);
            String depName = cursor.getString(6);
            awardsInfo = new AwardsInfo(_id, id1, name, college, competitionType, awardLevel, depName);
        }
        cursor.close();
        database.close();
        return awardsInfo;
    }

    public List<AwardsInfo> findLikeByPrint(String STNumber, String ContestName, String AwardLevel) {
        List<AwardsInfo> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String sql = "select * from awards where STNumber like '%" + STNumber + "%' and contestName like '%" + ContestName + "%' and awardLevel like '%" + AwardLevel + "%' order by _id desc;";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String id1 = cursor.getString(1);
            String name  = cursor.getString(2);
            String college = cursor.getString(3);
            String competitionType = cursor.getString(4);
            String awardLevel = cursor.getString(5);
            String depName = cursor.getString(6);
            list.add(new AwardsInfo(_id, id1, name, college, competitionType, awardLevel, depName));
        }
        cursor.close();
        database.close();
        return list;
    }


    /**
     * 根据学号查询某一个人的获奖信息
     * @param STNumber 学号
     * @return 获奖信息
     */
    public List<AwardsInfo> findBySTNumber(String STNumber) {
        List<AwardsInfo> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(AwardsTable.TAB_NAME, null,  AwardsTable.AWARDS_STNUMBER + "=?", new String[]{STNumber}, null, null, AwardsTable.AWARDS_ID + " desc");
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String id1 = cursor.getString(1);
            String name  = cursor.getString(2);
            String college = cursor.getString(3);
            String competitionType = cursor.getString(4);
            String awardLevel = cursor.getString(5);
            String depName = cursor.getString(6);
            list.add(new AwardsInfo(_id, id1, name, college, competitionType, awardLevel, depName));
        }
        cursor.close();
        database.close();
        return list;
    }


    /**
     * 根据比赛查询获奖信息
     * @param contestName 比赛名称
     * @return 获奖信息
     */
    public List<AwardsInfo> findByContestName(String contestName) {
        List<AwardsInfo> list = new ArrayList<>();
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(AwardsTable.TAB_NAME, null, AwardsTable.AWARDS_CONTESTNAME + "='" + contestName + "'", null, null, null, AwardsTable.AWARDS_ID + " desc");
        //从cursor取出所有数据,并且封装到List中
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String id = cursor.getString(1);
            String name  = cursor.getString(2);
            String college = cursor.getString(3);
            String competitionType = cursor.getString(4);
            String awardLevel = cursor.getString(5);
            String depName = cursor.getString(6);
            list.add(new AwardsInfo(_id, id, name, college, competitionType, awardLevel, depName));
        }
        //关闭连接
        cursor.close();
        database.close();
        return list;
    }
    /**
     * 根据比赛和奖项查询获奖信息
     * @param ContestName 比赛名称
     * @param Award 奖项
     * @return 获奖信息
     */
    public List<AwardsInfo> findByContestAndAward(String ContestName, String Award) {
        List<AwardsInfo> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(AwardsTable.TAB_NAME, null,   AwardsTable.AWARDS_CONTESTNAME + " =?  and " + AwardsTable.AWARDS_LEVEL + " = ? ", new String[]{ContestName,Award}, null, null, AwardsTable.AWARDS_CLASSNAME );
        AwardsInfo awardsInfo = null;
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String id1 = cursor.getString(1);
            String name  = cursor.getString(2);
            String college = cursor.getString(3);
            String competitionType = cursor.getString(4);
            String awardLevel = cursor.getString(5);
            String depName = cursor.getString(6);
            list.add(new AwardsInfo(_id, id1, name, college, competitionType, awardLevel, depName));
        }
        cursor.close();
        database.close();
        return list;
    }

    public List<AwardsInfo> findByContestAndAward() {
        List<AwardsInfo> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(AwardsTable.TAB_NAME, null, null, null, null, null,  AwardsTable.AWARDS_CLASSNAME);
        AwardsInfo awardsInfo = null;
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String id1 = cursor.getString(1);
            String name  = cursor.getString(2);
            String college = cursor.getString(3);
            String competitionType = cursor.getString(4);
            String awardLevel = cursor.getString(5);
            String depName = cursor.getString(6);
            list.add(new AwardsInfo(_id, id1, name, college, competitionType, awardLevel, depName));
        }
        cursor.close();
        database.close();
        return list;
    }

    public List<AwardsInfo> findByAward(String award) {
        List<AwardsInfo> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(AwardsTable.TAB_NAME, null, AwardsTable.AWARDS_CONTESTNAME + "='" + award + "'", null, null, null, AwardsTable.AWARDS_ID + " desc");
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String id = cursor.getString(1);
            String name  = cursor.getString(2);
            String college = cursor.getString(3);
            String competitionType = cursor.getString(4);
            String awardLevel = cursor.getString(5);
            String depName = cursor.getString(6);
            list.add(new AwardsInfo(_id, id, name, college, competitionType, awardLevel, depName));
        }
        cursor.close();
        database.close();
        return list;
    }

}
