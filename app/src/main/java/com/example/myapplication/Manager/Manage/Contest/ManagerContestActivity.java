package com.example.myapplication.Manager.Manage.Contest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContestDao;
import com.example.myapplication.domain.Contest;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ManagerContestActivity extends AppCompatActivity {

    private List<Contest> contextList=new ArrayList<>();
    ContestDao contestDao=new ContestDao(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_contest);


        contextList=contestDao.queryAll();

        ContestAdapter contestAdapter=new ContestAdapter(this,R.layout.item_listview_contest,contextList);

        ListView listView=(ListView)findViewById(R.id.manager_contest_list_view);
        listView.setAdapter(contestAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contest contest=contextList.get(position);
                Intent intent=new Intent(ManagerContestActivity.this,ModifyContestActivity.class);
                intent.putExtra("id",String.valueOf(contest.getContestId()));
                intent.putExtra("name",contest.getContestName());
                intent.putExtra("introduction",contest.getContestIntroduction());
                intent.putExtra("time",contest.getContestTime());
                intent.putExtra("note",contest.getContestNote());
                startActivity(intent);
            }
        });
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
}