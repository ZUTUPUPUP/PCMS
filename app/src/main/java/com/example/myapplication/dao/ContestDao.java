package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.domain.Contest;
import com.example.myapplication.utils.Manager.ContestDBHelper;
import com.example.myapplication.utils.Manager.ContestTable;

public class ContestDao {

    private ContestDBHelper dbHelper;

    public ContestDao(Context context){
        dbHelper = new ContestDBHelper(context);
    }
    public void add(Contest contest) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContestTable.CONTEST_NAME, contest.getContestName().trim());
        values.put(ContestTable.CONTEST_INTRODUCTION, contest.getContestIntroduction());
        values.put(ContestTable.CONTEST_TIME, contest.getContestTime());
        values.put(ContestTable.CONTEST_NOTE, contest.getContestNote().trim());
        long id = database.insert(ContestTable.TAB_NAME, null, values);
        Log.v("MyInfo", "id = " + id);
        database.close();
    }
}
