package com.example.myapplication.dao;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.utils.MD5Utils;
import com.example.myapplication.domain.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {
    Context context = null;

    public UserDaoTest() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
    @Test
    public void add() {
        UserDao dao = new UserDao(context);
        if(dao.findByUserName("201708024xx1") == null) {
            Log.v("MyInfo", "该用户名已经存在");
        } else {
            String passwd = MD5Utils.md5("222");
            dao.add(new User(-1, "201708024xx1", passwd + "", "aaaaa", null, 1, 1));
        }
    }

    @Test
    public void deleteByUserName() {
        UserDao dao = new UserDao(context);
        dao.deleteByUserName("201708024104");
    }

    @Test
    public void update() {
        UserDao dao = new UserDao(context);
        dao.update(new User(-1, "user", "123456", "user", "女", 2, 2));
    }

    @Test
    public void findAll() {
        UserDao dao = new UserDao(context);
        Log.v("MyInfo", dao.findAll().toString());
    }
    @Test
    public void findByUserName() {
        UserDao dao = new UserDao(context);
        User user = dao.findByUserName("user");
        Log.v("MyInfo", user + "");
    }
}