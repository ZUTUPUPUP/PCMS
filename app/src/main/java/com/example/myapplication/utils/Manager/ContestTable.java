package com.example.myapplication.utils.Manager;

public class ContestTable {

    public static final String TAB_NAME = "contest";
    public static final String CONTEST_ID="contestId";
    public static final String CONTEST_NAME="contestName";
    public static final String CONTEST_INTRODUCTION="contestIntroduction";
    public static final String CONTEST_TIME="contestTime";
    public static final String CONTEST_NOTE="contestNote";

    public static final String CREATE_TAB = "create table "
            + TAB_NAME + "("
            + CONTEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CONTEST_NAME + " varchar not null, "
            + CONTEST_INTRODUCTION + " varchar, "
            + CONTEST_TIME + " varchar not null, "
            + CONTEST_NOTE + " varchar  "
            + ");";
}