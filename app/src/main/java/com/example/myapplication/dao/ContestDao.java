package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.domain.Contest;
import com.example.myapplication.utils.BaseUrl;
import com.example.myapplication.utils.DBHelper;
import com.example.myapplication.utils.Manager.Contest.ContestTable;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContestDao {

    private DBHelper dbHelper;
    String url = BaseUrl.BASE_URL;

    public ContestDao(Context context){
        dbHelper = new DBHelper(context);
    }
    public void add(Contest contest)  {
        url= BaseUrl.BASE_URL + "contest/add.do";
        try {
            Log.d("add:",OkHttpUtils.post()
                    .url(url)
                    .id(100)
                    .addParams("contestName", contest.getContestName())
                    .addParams("contestIntroduction", contest.getContestIntroduction())
                    .addParams("contestTime", contest.getContestTime())
                    .addParams("contestNote", contest.getContestNote())
                    .build().execute().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public List<Contest> queryByName(String contestName){
        url= BaseUrl.BASE_URL + "contest/queryByName.do";
        List<Contest> testList = null;
        try {
            testList=JSON.parseArray(OkHttpUtils.get()
                    .url(url)
                    .id(100)
                    .addParams("contestName", contestName)
                    .build()
                    .execute()
                    .body()
                    .string()
                    ,Contest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Contest> contestList=new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery("select * from "+ContestTable.TAB_NAME+" where "+ContestTable.CONTEST_NAME+" like ?",new String[]{"%"+contestName+"%"});
        while(cursor.moveToNext()){
            int id=cursor.getInt(0);
            String name=cursor.getString(1);
            String introduction=cursor.getString(2);
            String time=cursor.getString(3);
            String note=cursor.getString(4);
            contestList.add(new Contest(id,name,introduction,time,note));
        }
        return testList;
    }

    public List<Contest> queryAll(){
        url= BaseUrl.BASE_URL + "contest/queryAll.do";
        List<Contest> testList = null;
        try {
            testList = JSON.parseArray(OkHttpUtils.get()
                    .url(url)
                    .id(100)
                    .build()
                    .execute()
                    .body()
                    .string()
                    ,Contest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        return testList;
    }

    public void modifyContest(Contest contest){
        url= BaseUrl.BASE_URL + "contest/modifyContest.do";
        try {
            OkHttpUtils.get()
                    .url(url)
                    .id(1001)
                    .addParams("contestId", Integer.toString(contest.getContestId()))
                    .addParams("contestName", contest.getContestName())
                    .addParams("contestIntroduction", contest.getContestIntroduction())
                    .addParams("contestTime", contest.getContestTime())
                    .addParams("contestNote", contest.getContestNote())
                    .build().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        url= BaseUrl.BASE_URL + "contest/deleteContest.do";
        try {
            OkHttpUtils.get()
                    .url(url)
                    .id(1001)
                    .addParams("contestId", Integer.toString(contest.getContestId()))
                    .addParams("contestName", contest.getContestName())
                    .addParams("contestIntroduction", contest.getContestIntroduction())
                    .addParams("contestTime", contest.getContestTime())
                    .addParams("contestNote", contest.getContestNote())
                    .build().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.delete(ContestTable.TAB_NAME,ContestTable.CONTEST_ID+"=?",new String[]{String.valueOf(contest.getContestId())});
    }


    public Contest findById(int id) {
        url= BaseUrl.BASE_URL + "contest/findById.do";
        Contest test = null;
        try {
            test = JSON.parseObject(OkHttpUtils.get()
                    .url(url)
                    .id(1001)
                    .addParams("id", Integer.toString(id))
                    .build().execute().body().string(),Contest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        return test;
    }


}
