package com.example.myapplication.utils;

import com.example.myapplication.utils.Manager.Contest.ContestTable;

public class ContestRegistryTable {
    //create table contestregistry(_id integer primary key autoincrement, contestId int, STNumberId int, depId int, ClassAndGradevarchar not null, gender varchar not null, email varchar not null,  constraint reg_contest_fk foreign key(contestId) references contest(contestId) ON UPDATE CASCADE ON DELETE CASCADE,  constraint reg_dep_fk foreign key(depId) references dep(_id) ON UPDATE CASCADE ON DELETE CASCADE, constraint reg_user_fk foreign key(STNumberId) references user(_id) ON UPDATE CASCADE ON DELETE CASCADE);
    public static final String TAB_NAME = "contestregistry";
    public static final String CONTESTREG_ID = "_id";
    public static final String CONTESTREG_CONTESTID = "contestId";
    public static final String CONTESTREG_STNUMBERID = "STNumberId";
    public static final String CONTESTREG_DEPID = "depId";
    public static final String CONTESTREG_CLASS = "ClassAndGrade";
    public static final String CONTESTREG_GENDER = "gender";
    public static final String CONTESTREG_EMAIL = "email";
    public static final String CONTESTREG_RELNAME = "relName";
    public static final String CREATE_TAB = "create table "
            + TAB_NAME + "("
            + CONTESTREG_ID + " integer primary key autoincrement, "
            + CONTESTREG_CONTESTID + " int, "
            + CONTESTREG_STNUMBERID + " int, "
            + CONTESTREG_DEPID + " int, "
            + CONTESTREG_CLASS + " varchar not null, "
            + CONTESTREG_GENDER + " varchar not null, "
            + CONTESTREG_EMAIL + " varchar not null, "
            + CONTESTREG_RELNAME + " varchar not null, "
            + " constraint reg_contest_fk foreign key" + "(" + CONTESTREG_CONTESTID + ") references "
            + ContestTable.TAB_NAME + "(" + ContestTable.CONTEST_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
            + " constraint reg_dep_fk foreign key" + "(" + CONTESTREG_DEPID + ") references "
            + DepTable.TAB_NAME + "(" + DepTable.DEP_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
            + "constraint reg_user_fk foreign key" + "(" + CONTESTREG_STNUMBERID + ") references "
            + UserTable.TAB_NAME + "(" + UserTable.USER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE);";
}
