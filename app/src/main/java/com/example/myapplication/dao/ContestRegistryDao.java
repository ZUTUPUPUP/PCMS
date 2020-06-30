package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.domain.ContestRegistry;
import com.example.myapplication.domain.ContestRegistryMessage;
import com.example.myapplication.utils.ContestRegistryTable;
import com.example.myapplication.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ContestRegistryDao {
    private DBHelper dbHelper;

    public ContestRegistryDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    /**
     * 添加一条记录
     * @param contestRegistry 添加信息
     */
    public void add(ContestRegistry contestRegistry) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContestRegistryTable.CONTESTREG_ID, contestRegistry.get_id());
        values.put(ContestRegistryTable.CONTESTREG_CONTESTID, contestRegistry.getContestId());
        values.put(ContestRegistryTable.CONTESTREG_STNUMBERID, contestRegistry.getSTNumberId());
        values.put(ContestRegistryTable.CONTESTREG_DEPID, contestRegistry.getDepId());
        values.put(ContestRegistryTable.CONTESTREG_CLASS, contestRegistry.getClassAndGrade().trim());
        values.put(ContestRegistryTable.CONTESTREG_EMAIL, contestRegistry.getEmail().trim());
        values.put(ContestRegistryTable.CONTESTREG_GENDER, contestRegistry.getGender().trim());
        values.put(ContestRegistryTable.CONTESTREG_RELNAME, contestRegistry.getRelName().trim());
        long id = database.insert(ContestRegistryTable.TAB_NAME, null, values);
        Log.v("MyInfo", "id = " + id);
        database.close();
    }

    /**
     * 根据id删除一条报名信息
     * @param id id
     */
    public void deleteById(int id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        int deleteCount = database.delete(ContestRegistryTable.TAB_NAME, ContestRegistryTable.CONTESTREG_ID + "=" + id, null);
        Log.v("MyInfo", "deleteCount = " + deleteCount);
        database.close();
    }

    /**
     * 更新一条记录
     * @param contestRegistry 要更新报名信息的实体类
     */
    public void update(ContestRegistry contestRegistry) {
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //执行update update user set user where icon_username=icon_username
        ContentValues values = new ContentValues();
        Log.v("MyInfo", contestRegistry.toString());
        values.put(ContestRegistryTable.CONTESTREG_ID, contestRegistry.get_id());
        values.put(ContestRegistryTable.CONTESTREG_CONTESTID, contestRegistry.getContestId());
        values.put(ContestRegistryTable.CONTESTREG_STNUMBERID, contestRegistry.getSTNumberId());
        values.put(ContestRegistryTable.CONTESTREG_DEPID, contestRegistry.getDepId());
        values.put(ContestRegistryTable.CONTESTREG_CLASS, contestRegistry.getClassAndGrade().trim());
        values.put(ContestRegistryTable.CONTESTREG_EMAIL, contestRegistry.getEmail().trim());
        values.put(ContestRegistryTable.CONTESTREG_GENDER, contestRegistry.getGender().trim());
        values.put(ContestRegistryTable.CONTESTREG_RELNAME, contestRegistry.getRelName().trim());
        int updateCount = database.update(ContestRegistryTable.TAB_NAME, values,  ContestRegistryTable.CONTESTREG_CONTESTID + "=? and " + ContestRegistryTable.CONTESTREG_STNUMBERID + "=?", new String[]{contestRegistry.getContestId() + "", contestRegistry.getSTNumberId() + ""});
        Log.v("MyInfo", "updateCount = " + updateCount);
        //关闭连接
        database.close();
    }

    /**
     * 根据报名id
     * @param id 报名id
     * @return 用户
     */
    public ContestRegistryMessage findById(int id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //select * from user where userName = 'admin';
        String sql = "select contestregistry.*, contest.contestName, user.userName, dep.name from contestregistry, contest, user, dep where contestregistry._id = " + id + " and contest.contestId and contestregistry.STNumberId = user._id and contestregistry.depId = dep._id order by _id desc;";
        Cursor cursor = database.rawQuery(sql, null);
        ContestRegistryMessage contestRegistryMessage = null;
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            int contestId = cursor.getInt(cursor.getColumnIndex("contestId"));
            int STNumberId = cursor.getInt(cursor.getColumnIndex("STNumberId"));
            int depId = cursor.getInt(cursor.getColumnIndex("depId"));
            String ClassAndGrade = cursor.getString(cursor.getColumnIndex("ClassAndGrade"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String contestName = cursor.getString(cursor.getColumnIndex("contestName"));
            String userName = cursor.getString(cursor.getColumnIndex("userName"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String relName = cursor.getString(cursor.getColumnIndex("relName"));
            contestRegistryMessage = new ContestRegistryMessage(_id, contestId, STNumberId, depId, ClassAndGrade, gender, email, contestName, userName, name, relName);
        }
        cursor.close();
        database.close();
        return contestRegistryMessage;
    }


    /**
     * 根据竞赛id查询报名信息
     * @param id 竞赛id
     * @return
     */
    public List<ContestRegistryMessage> findByContestId(int id) {
        List<ContestRegistryMessage> list = new ArrayList<>();
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //执行query select * from user
        String sql = "select contestregistry.*, contest.contestName, user.userName, dep.name from contestregistry, contest, user, dep where contestregistry.contestId = " + id + " and contestregistry.contestId = contest.contestId and contestregistry.STNumberId = user._id and contestregistry.depId = dep._id order by _id desc";
        Cursor cursor = database.rawQuery(sql, null);
        //从cursor取出所有数据,并且封装到List中
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            int contestId = cursor.getInt(cursor.getColumnIndex("contestId"));
            int STNumberId = cursor.getInt(cursor.getColumnIndex("STNumberId"));
            int depId = cursor.getInt(cursor.getColumnIndex("depId"));
            String ClassAndGrade = cursor.getString(cursor.getColumnIndex("ClassAndGrade"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String contestName = cursor.getString(cursor.getColumnIndex("contestName"));
            String userName = cursor.getString(cursor.getColumnIndex("userName"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String relName = cursor.getString(cursor.getColumnIndex("relName"));
            list.add(new ContestRegistryMessage(_id, contestId, STNumberId, depId, ClassAndGrade, gender, email, contestName, userName, name, relName));
        }
        //关闭连接
        cursor.close();
        database.close();
        return list;
    }

    /**
     * 查询所有用户
     * @return
     */
    public List<ContestRegistryMessage> findAll() {
        List<ContestRegistryMessage> list = new ArrayList<>();
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //执行query select * from user
        String sql = "select contestregistry.*, contest.contestName, user.userName, dep.name from contestregistry, contest, user, dep where contestregistry.contestId = contest.contestId and contestregistry.STNumberId = user._id and contestregistry.depId = dep._id order by _id desc";
        Cursor cursor = database.rawQuery(sql, null);
        //从cursor取出所有数据,并且封装到List中
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            int contestId = cursor.getInt(cursor.getColumnIndex("contestId"));
            int STNumberId = cursor.getInt(cursor.getColumnIndex("STNumberId"));
            int depId = cursor.getInt(cursor.getColumnIndex("depId"));
            String ClassAndGrade = cursor.getString(cursor.getColumnIndex("ClassAndGrade"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String contestName = cursor.getString(cursor.getColumnIndex("contestName"));
            String userName = cursor.getString(cursor.getColumnIndex("userName"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String relName = cursor.getString(cursor.getColumnIndex("relName"));
            list.add(new ContestRegistryMessage(_id, contestId, STNumberId, depId, ClassAndGrade, gender, email, contestName, userName, name, relName));
        }
        //关闭连接
        cursor.close();
        database.close();
        return list;
    }


    /**
     * 根据输入模糊查询所有用户
     * @return
     */
    public List<ContestRegistryMessage> findLikeAllByPrint(String contestName1, String userName1, String depName1, String ClassAndGrade1) {
        List<ContestRegistryMessage> list = new ArrayList<>();
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //执行query select * from user
        String sql = "select contestregistry.*, contest.contestName, user.userName, dep.name from contestregistry, contest, user, dep where contestregistry.contestId = contest.contestId and contestregistry.STNumberId = user._id and contestregistry.depId = dep._id and contest.contestName like '%" + contestName1 + "%' and user.userName like '%" + userName1 + "%' and  dep.name like '%" + depName1 + "%' and contestregistry.ClassAndGrade like '%" + ClassAndGrade1 + "%'  order by _id desc;";
        Cursor cursor = database.rawQuery(sql, null);
        //从cursor取出所有数据,并且封装到List中
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            int contestId = cursor.getInt(cursor.getColumnIndex("contestId"));
            int STNumberId = cursor.getInt(cursor.getColumnIndex("STNumberId"));
            int depId = cursor.getInt(cursor.getColumnIndex("depId"));
            String ClassAndGrade = cursor.getString(cursor.getColumnIndex("ClassAndGrade"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String contestName = cursor.getString(cursor.getColumnIndex("contestName"));
            String userName = cursor.getString(cursor.getColumnIndex("userName"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String relName = cursor.getString(cursor.getColumnIndex("relName"));
            list.add(new ContestRegistryMessage(_id, contestId, STNumberId, depId, ClassAndGrade, gender, email, contestName, userName, name, relName));
        }
        //关闭连接
        cursor.close();
        database.close();
        return list;
    }

    /**
     * 根据输入的比赛名称查询所有报名者的学号
     * @return
     */
    public List<String> findAllUserIdByContestName(String ContestName){
        List<String> list = new ArrayList<>();
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        /*
         select userName
         from contestregistry,user,Contest
         where contestregistry.STNumber=user._id and contestregistry.contestId=contest.contestId and contest.contestName=contestName;
        */
        String sql="select userName from user, contestregistry, contest where contestregistry.STNumberId = user._id and contestregistry.contestId = contest.contestId and contest.contestName like '%" + ContestName + "%'";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String id=cursor.getString(cursor.getColumnIndex("userName"));
            list.add(id);
        }
        //关闭连接
        cursor.close();
        database.close();
        return list;
    }
    //根据user查询注册信息
    public List<ContestRegistryMessage> findByUsernameId(String Username) {
        List<ContestRegistryMessage> list = new ArrayList<>();
        //得到连接
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //执行query select * from user
        String sql = "select contestregistry.*, contest.contestName, user.userName, dep.name from contestregistry, contest, user, dep where user.userName = '" + Username + "' and contestregistry.contestId = contest.contestId and contestregistry.STNumberId = user._id and contestregistry.depId = dep._id order by _id desc";
        Cursor cursor = database.rawQuery(sql, null);
        //从cursor取出所有数据,并且封装到List中
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            int contestId = cursor.getInt(cursor.getColumnIndex("contestId"));
            int STNumberId = cursor.getInt(cursor.getColumnIndex("STNumberId"));
            int depId = cursor.getInt(cursor.getColumnIndex("depId"));
            String ClassAndGrade = cursor.getString(cursor.getColumnIndex("ClassAndGrade"));
            String gender = cursor.getString(cursor.getColumnIndex("gender"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String contestName = cursor.getString(cursor.getColumnIndex("contestName"));
            String userName = cursor.getString(cursor.getColumnIndex("userName"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String relName = cursor.getString(cursor.getColumnIndex("relName"));
            list.add(new ContestRegistryMessage(_id, contestId, STNumberId, depId, ClassAndGrade, gender, email, contestName, userName, name, relName));
        }
        //关闭连接
        cursor.close();
        database.close();
        return list;
    }
}
