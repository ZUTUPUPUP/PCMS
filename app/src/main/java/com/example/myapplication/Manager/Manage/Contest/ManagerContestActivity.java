package com.example.myapplication.Manager.Manage.Contest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContestDao;
import com.example.myapplication.domain.Contest;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ManagerContestActivity extends AppCompatActivity {
    EditText editText;
    private List<Contest> contextTemp=new ArrayList<>();
    private List<Contest> contextList=new ArrayList<>();
    ContestDao contestDao=new ContestDao(this);
    ContestAdapter contestAdapter;
    ListView listView;
    private LinearLayout ll_loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_contest);
        editText=findViewById(R.id.et_contestName);
        ll_loading = findViewById(R.id.ll_loading);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                contextTemp=contestDao.queryAll();
                contextList.clear();
                contextList.addAll(contextTemp);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(thread.isAlive()){
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        contestAdapter=new ContestAdapter(this,R.layout.item_listview_contest,contextList);
        listView=(ListView)findViewById(R.id.manager_contest_list_view);
        listView.setAdapter(contestAdapter);
    }
    protected void onResume() {
        super.onResume();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                contextTemp=contestDao.queryAll();
                contextList.clear();
                contextList.addAll(contextTemp);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(thread.isAlive()){
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ContestAdapter contestAdapter=new ContestAdapter(this,R.layout.item_listview_contest,contextList);
        ListView listView=(ListView)findViewById(R.id.manager_contest_list_view);
        listView.setAdapter(contestAdapter);
    }

    public void OpenAddContest(View view) {
        Intent intent=new Intent(this,AddContestActivity.class);
        startActivity(intent);
    }

    public void findAllContestMessageByPrint(View view) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                contextTemp=contestDao.queryByName(editText.getText().toString());
                contextList.clear();
                contextList.addAll(contextTemp);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(thread.isAlive()){
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ContestAdapter contestAdapter=new ContestAdapter(this,R.layout.item_listview_contest,contextList);
        ListView listView=(ListView)findViewById(R.id.manager_contest_list_view);
        listView.setAdapter(contestAdapter);
    }

    public void click(View view) {
        editText.setText("");
    }
}