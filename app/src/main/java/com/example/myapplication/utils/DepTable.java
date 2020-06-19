package com.example.myapplication.utils;


public class DepTable {
    public static final String TAB_NAME = "dep";
    public static final String DEP_ID = "_id";
    public static final String DEP_NAME = "name";
    public static final String CREATE_TAB = "create table "
            + TAB_NAME + "("
            + DEP_ID + " integer primary key autoincrement, "
            + DEP_NAME + " varchar not null unique);";
}
