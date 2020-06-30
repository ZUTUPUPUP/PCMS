package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.domain.Contest;
import com.example.myapplication.utils.DBHelper;
import com.example.myapplication.utils.Manager.Contest.ContestTable;

import java.util.ArrayList;
import java.util.List;

public class ContestDao {

    private DBHelper dbHelper;

    public ContestDao(Context context){
        dbHelper = new DBHelper(context);
    }
    public void add(Contest contest) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContestTable.CONTEST_NAME, contest.getContestName().trim());
        values.put(ContestTable.CONTEST_INTRODUCTION, contest.getContestIntroduction());
        values.put(ContestTable.CONTEST_TIME, contest.getContestTime());
        values.put(ContestTable.CONTEST_NOTE, contest.getContestNote().trim());
        long id = database.insert(ContestTable.TAB_NAME, null, values);
        Log.v("Contest", "id = " + id);
        database.close();
    }

    public List<Contest> querybyName(String Name){
        List<Contest> contestList=new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery("select * from "+ContestTable.TAB_NAME+" where "+ContestTable.CONTEST_NAME+" like ?",new String[]{"%"+Name+"%"});
        while(cursor.moveToNext()){
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            String introduction=cursor.getString(2);
            String time=cursor.getString(3);
            String note=cursor.getString(4);
            contestList.add(new Contest(id,name,introduction,time,note));
        }
        return contestList;
    }

    public List<Contest> queryAll(){
        List<Contest> contestList=new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor=database.query(ContestTable.TAB_NAME, null,null,null,null,null,null);
        while(cursor.moveToNext()){
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            String introduction=cursor.getString(2);
            String time=cursor.getString(3);
            String note=cursor.getString(4);
            contestList.add(new Contest(id,name,introduction,time,note));
        }
        return contestList;
    }

    public void modifyContest(Contest contest){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContestTable.CONTEST_NAME, contest.getContestName().trim());
        values.put(ContestTable.CONTEST_INTRODUCTION, contest.getContestIntroduction());
        values.put(ContestTable.CONTEST_TIME, contest.getContestTime());
        values.put(ContestTable.CONTEST_NOTE, contest.getContestNote().trim());
        database.update(ContestTable.TAB_NAME,values,ContestTable.CONTEST_ID+"=?", new String[]{String.valueOf(contest.getContestId())});
        database.close();
    }

    public void deleteContest(Contest contest){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.delete(ContestTable.TAB_NAME,ContestTable.CONTEST_ID+"=?",new String[]{String.valueOf(contest.getContestId())});
    }


    public Contest findById(int id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(ContestTable.TAB_NAME, null,  ContestTable.CONTEST_ID + "=?", new String[]{id + ""}, null, null, ContestTable.CONTEST_ID + " desc");
        Contest contest = null;
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String name=cursor.getString(1);
            String introduction=cursor.getString(2);
            String time=cursor.getString(3);
            String note=cursor.getString(4);
            contest = new Contest(_id, name, introduction, time, note);
        }
        cursor.close();
        database.close();
        return contest;
    }


}
