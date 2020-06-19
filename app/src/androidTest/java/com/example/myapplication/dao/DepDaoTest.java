package com.example.myapplication.dao;

import android.content.Context;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class DepDaoTest {

    Context context = null;

    public DepDaoTest() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
    @Test
    public void findAll() {
        DepDao dao = new DepDao(context);
        Log.v("MyInfo", dao.findAll().toString());
    }

}