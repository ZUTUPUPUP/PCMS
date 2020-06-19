package com.example.myapplication.utils.Manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContestDBHelper extends SQLiteOpenHelper {
    public ContestDBHelper(Context context) {
        super(context, "ManagerContest.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContestTable.CREATE_TAB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
