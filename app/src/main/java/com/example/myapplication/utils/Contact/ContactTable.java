package com.example.myapplication.utils.Contact;

import static com.example.myapplication.utils.UserTable.USER_NAME;
import static com.example.myapplication.utils.UserTable.USER_NICKNAME;
import static com.example.myapplication.utils.UserTable.USER_PASSWD;

public class ContactTable {
    public static final String TAB_NAME = "contact";
    public static final String _id = "_id";
    public static final String date= "date";
    public static final String senderId = "senderId";
    public static final String receiverId = "receiverId";
    public static final String mas = "mas";
    public static final String CREATE_TAB = "create table "
            + TAB_NAME + "("
            + _id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + date + " varchar, "
            + senderId + " varchar, "
            + receiverId + " varchar, "
            + mas + " varchar"
            + ");";
}
