package com.example.myapplication.utils.Manager.Awards;

public class AwardsTable {

    public static final String TAB_NAME = "awards";
    public static final String AWARDS_ID = "_id"; //id
    public static final String AWARDS_STNUMBER = "STNumber";//学号
    public static final String AWARDS_RELNAME = "relName";//姓名
    public static final String AWARDS_CLASSNAME = "className"; //班级
    public static final String AWARDS_CONTESTNAME = "contestName";//比赛
    public static final String AWARDS_LEVEL = "awardLevel";//奖项
    public static final String AWARDS_DEPNAME = "depName";//学院
    public static final String CREATE_TAB = "create table "
            + TAB_NAME + "("
            + AWARDS_ID + " integer primary key autoincrement, "
            + AWARDS_STNUMBER + " varchar, "
            + AWARDS_RELNAME + " varchar, "
            + AWARDS_CLASSNAME + " varchar, "
            + AWARDS_CONTESTNAME + " varchar, "
            + AWARDS_LEVEL + " varchar, "
            + AWARDS_DEPNAME + " varchar"
            + ");";
}
