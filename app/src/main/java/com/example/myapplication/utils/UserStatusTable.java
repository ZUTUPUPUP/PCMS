package com.example.myapplication.utils;

public class UserStatusTable {
    //create table status(id integer primary key autoincrement, name varchar not null unique);
    public static final String TAB_NAME = "status";
    public static final String STATUS_ID = "_id";
    public static final String STATUS_NAME = "name";
    public static final String CREATE_TAB = "create table "
            + TAB_NAME + "("
            + STATUS_ID + " integer primary key autoincrement, "
            + STATUS_NAME + " varchar not null unique);";
}
