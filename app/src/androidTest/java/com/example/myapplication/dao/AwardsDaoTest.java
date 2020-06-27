package com.example.myapplication.dao;

import android.content.Context;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class AwardsDaoTest {

    Context context = null;

    public AwardsDaoTest() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
    @Test
    public void add() {
        AwardsDao awardsDao = new AwardsDao(context);
    }

    @Test
    public void findAll() {
        AwardsDao awardsDao = new AwardsDao(context);
        Log.v("MyInfo", awardsDao.findAll().toString());
    }

    @Test
    public void findBySTNumber() {
        AwardsDao awardsDao = new AwardsDao(context);
        Log.v("MyInfo1", awardsDao.findBySTNumber("user").toString());
    }
}