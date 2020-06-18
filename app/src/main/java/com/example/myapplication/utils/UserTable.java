package com.example.myapplication.utils;

public class UserTable {
    //create table user(name varchar primary key, passwd varchar not null, nickName varchar, gender varchar, department varchar, status_id int, constraint user_status_fk foreign key (status_id) references status(_id) ON UPDATE CASCADE ON DELETE CASCADE);
    public static final String TAB_NAME = "user";
    public static final String USER_NAME = "userName";
    public static final String USER_PASSWD = "passwd";
    public static final String USER_NICKNAME = "nickName";
    public static final String USER_GENDER = "gender";
    public static final String USER_DEP = "department";
    public static final String USER_STATUS_ID = "status_id";
    public static final String CREATE_TAB = "create table "
            + TAB_NAME + "("
            + USER_NAME + " varchar primary key, "
            + USER_PASSWD + " varchar not null, "
            + USER_NICKNAME + " varchar, "
            + USER_GENDER + " varchar, "
            + USER_DEP + " varchar, "
            + USER_STATUS_ID + " int, constraint user_status_fk foreign key " + "(" +USER_STATUS_ID + ") references "
            + UserStatusTable.TAB_NAME + "(" + UserStatusTable.STATUS_ID + ") ON UPDATE CASCADE ON DELETE CASCADE);";
}
