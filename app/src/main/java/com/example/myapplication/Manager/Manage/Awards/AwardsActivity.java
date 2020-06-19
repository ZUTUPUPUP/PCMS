package com.example.myapplication.Manager.Manage.Awards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.example.myapplication.R;
import com.example.myapplication.dao.AwardsDao;
import com.example.myapplication.domain.AwardsInfo;

import java.util.List;

public class AwardsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt1,bt2;
    private AwardsAdapter awardsAdapter;
    private List list;
    private AwardsDao awardsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awards);
        ListView listView=findViewById(R.id.rb_awards_list);
        bt1 = findViewById(R.id.but_insert);
        bt2 = findViewById(R.id.but_queryOrder);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        awardsDao=new AwardsDao(this);
        list=awardsDao.findAll();
        awardsAdapter = new AwardsAdapter(this,list);
        listView.setAdapter(awardsAdapter);
    }

    //Activity创建或者从被覆盖、后台重新回到前台时被调用
    @Override
    protected void onResume() {
        super.onResume();
        //重新获取list数据
        list = awardsDao.findAll();
        //给list赋值 更新
        awardsAdapter.setList(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_insert:
                startActivity(new Intent(this,AwardsInsertActivity.class));
                break;
            case R.id.but_queryOrder:
                startActivity(new Intent(this,AwardsQueryActivity.class));
                break;
        }
    }
}