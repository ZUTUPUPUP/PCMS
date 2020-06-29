package com.example.myapplication.utils.News;

public class NewsTable {
    public static final String TAB_NAME = "News";
    public static final String _id = "_id";
    public static final String contestId= "contestId";
    public static final String date= "date";
    public static final String head="head";
    public static final String brief="brief";
    public static final String t0="t0";public static final String t5="t5";public static final String t9="t9";
    public static final String t1="t1";public static final String t6="t6";public static final String t10="t10";
    public static final String t2="t2";public static final String t7="t7";public static final String t11="t11";
    public static final String t3="t3";public static final String t8="t8";public static final String t12="t12";
    public static final String t4="t4";

    public static final String CREATE_TAB = "create table "
            + TAB_NAME + "("
            + _id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + contestId + " varchar, "
            + date + " varchar, "+ head + " varchar, "+ brief + " varchar, "
            + t0 + " varchar, "+ t4 + " varchar, "+ t7 + " varchar, "+ t10 + " varchar, "
            + t1 + " varchar, "+ t5 + " varchar, "+ t8 + " varchar, "+ t11 + " varchar, "
            + t2 + " varchar, "+ t6 + " varchar, "+ t9 + " varchar, "+ t12 + " varchar, "
            + t3 + " varchar"
            + ");";
}
