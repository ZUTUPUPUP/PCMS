package com.example.myapplication.Manager.Manage.Contest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContestDao;
import com.example.myapplication.domain.Contest;

import java.util.ArrayList;
import java.util.List;

public class ManagerContestActivity extends AppCompatActivity {

    private List<Contest> contextList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_contest);

        ContestDao contestDao=new ContestDao(this);
        contextList=contestDao.queryAll();

        ContestAdapter contestAdapter=new ContestAdapter(this,R.layout.item_listview_contest,contextList);

        ListView listView=(ListView)findViewById(R.id.manager_contest_list_view);
        listView.setAdapter(contestAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contest contest=contextList.get(position);
                Toast.makeText(ManagerContestActivity.this,contest.getContestName(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void OpenAddContest(View view) {
        Intent intent=new Intent(this,AddContestActivity.class);
        startActivity(intent);
    }
}