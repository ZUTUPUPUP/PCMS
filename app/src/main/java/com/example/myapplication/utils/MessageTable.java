package com.example.myapplication.utils;

public class MessageTable {
    public static final String TAB_NAME = "message";
    public static final String MESSAGE_ID = "_id"; //编号
    public static final String MESSAGE_USER_ID = "userId"; //用户id
    public static final String MESSAGE_TITLE = "title";//标题
    public static final String MESSAGE_CONTENT = "content";//内容
    public static final String MESSAGE_TIME = "time"; //时间
    public static final String MESSAGE_VIS = "vis"; //是否发送过通知
    public static final String CREATE_TAB = "create table "
            + TAB_NAME + "("
            + MESSAGE_ID + " integer primary key autoincrement, "
            + MESSAGE_USER_ID + " varchar, "
            + MESSAGE_TITLE + " varchar, "
            + MESSAGE_CONTENT + " varchar, "
            + MESSAGE_TIME + " varchar, "
            + MESSAGE_VIS + " int "
            + ");";
}
