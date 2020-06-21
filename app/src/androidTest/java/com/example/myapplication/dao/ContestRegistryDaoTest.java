package com.example.myapplication.dao;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.domain.ContestRegistry;
import com.example.myapplication.domain.ContestRegistryMessage;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class ContestRegistryDaoTest {

    Context context = null;

    public ContestRegistryDaoTest() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void add() {
        ContestRegistryDao contestRegistryDao = new ContestRegistryDao(context);
        contestRegistryDao.add(new ContestRegistry(null, 1, 1, 2, "计科172", "女", "1738871130@qq.com", "syp"));
    }
    @Test
    public void deleteByUserName() {
        ContestRegistryDao contestRegistryDao = new ContestRegistryDao(context);
        contestRegistryDao.deleteById(1);
    }

    @Test
    public void update() {
        ContestRegistryDao contestRegistryDao = new ContestRegistryDao(context);
        ContestRegistryMessage contestRegistryMessage = contestRegistryDao.findById(1);
        Log.v("MyInfo", contestRegistryMessage.toString());
        String relName = "zut_acm";
        String Class = "计科172";
        String email = "zut_acm@xxx.com";
        ContestRegistry contestRegistry = new ContestRegistry(contestRegistryMessage.get_id(), contestRegistryMessage.getContestId(), contestRegistryMessage.getSTNumberId(), contestRegistryMessage.getDepId(), Class, contestRegistryMessage.getGender(), email, relName);
        contestRegistryDao.update(contestRegistry);
    }

    @Test
    public void findById() {
        ContestRegistryDao contestRegistryDao = new ContestRegistryDao(context);
        ContestRegistryMessage contestRegistryMessage = contestRegistryDao.findById(1);
        Log.v("MyInfo", contestRegistryMessage.toString());
    }

    @Test
    public void findAll() {
        ContestRegistryDao contestRegistryDao = new ContestRegistryDao(context);
        Log.v("MyInfo", contestRegistryDao.findAll().toString());
    }
}