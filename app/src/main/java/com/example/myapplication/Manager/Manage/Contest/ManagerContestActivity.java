package com.example.myapplication.Manager.Manage.Contest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContestDao;
import com.example.myapplication.domain.Contest;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ManagerContestActivity extends AppCompatActivity {
    EditText editText;
    private List<Contest> contextList=new ArrayList<>();
    ContestDao contestDao=new ContestDao(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_contest);
        contextList=contestDao.queryAll();
        editText=findViewById(R.id.et_contestName);
        ContestAdapter contestAdapter=new ContestAdapter(this,R.layout.item_listview_contest,contextList);
        ListView listView=(ListView)findViewById(R.id.manager_contest_list_view);
        listView.setAdapter(contestAdapter);
    }
    protected void onResume() {
        super.onResume();
        contextList=contestDao.queryAll();
        ContestAdapter contestAdapter=new ContestAdapter(this,R.layout.item_listview_contest,contextList);
        ListView listView=(ListView)findViewById(R.id.manager_contest_list_view);
        listView.setAdapter(contestAdapter);
    }



    public void OpenAddContest(View view) {
        Intent intent=new Intent(this,AddContestActivity.class);
        startActivity(intent);
    }

    public void findAllContestMessageByPrint(View view) {
        contextList=contestDao.querybyName(editText.getText().toString());
        ContestAdapter contestAdapter=new ContestAdapter(this,R.layout.item_listview_contest,contextList);
        ListView listView=(ListView)findViewById(R.id.manager_contest_list_view);
        listView.setAdapter(contestAdapter);
    }

    public void click(View view) {
        editText.setText("");
    }
}