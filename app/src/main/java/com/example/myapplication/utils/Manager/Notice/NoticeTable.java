package com.example.myapplication.utils.Manager.Notice;

public class NoticeTable {
    public static final String TAB_NAME = "notice";
    public static final String NOTICE_ID = "_id"; //编号
    public static final String NOTICE_TITLE = "title";//标题
    public static final String NOTICE_CONTENT = "content";//内容
    public static final String NOTICE_TIME = "time"; //时间
    public static final String NOTICE_RECEIVER = "receiver"; //接收者学号
    public static final String CREATE_TAB = "create table "
            + TAB_NAME + "("
            + NOTICE_ID + " integer primary key autoincrement, "
            + NOTICE_TITLE + " varchar, "
            + NOTICE_CONTENT + " varchar, "
            + NOTICE_TIME + " varchar, "
            + NOTICE_RECEIVER + " varchar "
            + ");";
}
