package com.example.myapplication.utils.Manager.Awards;

public class AwardsTable {
    private String userId; //学号
    private String userName; //姓名
    private String college; //专业
    private String competitionType; //比赛类型
    private String awardLevel;//获奖等级
    public static final String TAB_NAME = "awards";
    public static final String USER_ID = "userId";
    public static final String USER_NAME= "userName";
    public static final String COLLEGE = "college";
    public static final String COMPETITION_TYPE = "competitionType";
    public static final String AWARD_LEVEL = "awardLevel";
    public static final String CREATE_TAB = "create table "
            + TAB_NAME + "("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME + " varchar, "
            + COLLEGE + " varchar, "
            + COMPETITION_TYPE + " varchar, "
            + AWARD_LEVEL + " varchar"
            + ");";
}
