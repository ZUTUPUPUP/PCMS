package com.example.myapplication.Manager.Manage.Awards;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.dao.AwardsDao;
import com.example.myapplication.domain.AwardsInfo;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class AwardsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt1,bt2;
    private EditText et_award_query_contestName, et_award_query_username, et_award_query_award;
    private AwardsAdapter awardsAdapter;
    private List<AwardsInfo> list;
    private AwardsDao awardsDao;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_awards);
        listView = findViewById(R.id.lv_awards_list);
        et_award_query_contestName = findViewById(R.id.et_award_query_contestName);
        et_award_query_username = findViewById(R.id.et_award_query_username);
        et_award_query_award = findViewById(R.id.et_award_query_award);
        bt1 = findViewById(R.id.but_insert);
        bt2 = findViewById(R.id.but_queryOrder);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        awardsDao = new AwardsDao(this);
        list = awardsDao.findAll();
        awardsAdapter = new AwardsAdapter(this, list);
        listView.setAdapter(awardsAdapter);
    }

    public void findAllAwardMessageByPrint(View v) {
        String STNumber = et_award_query_username.getText().toString().trim();
        String contestName = et_award_query_contestName.getText().toString().trim();
        String award = et_award_query_award.getText().toString().trim();
        list = awardsDao.findLikeByPrint(STNumber, contestName, award);
        awardsAdapter.setList(list);
    }
    public void click(View v) {
        if (v.getId() == R.id.iv_award_delContestName) {
            et_award_query_contestName.setText("");
        } else if (v.getId() == R.id.iv_award_delUserName) {
            et_award_query_username.setText("");
        } else if (v.getId() == R.id.iv_award_delAward) {
            et_award_query_award.setText("");
        }
    }
    //Activity创建或者从被覆盖、后台重新回到前台时被调用
    @Override
    protected void onResume() {
        super.onResume();
        //重新获取list数据
        list = awardsDao.findAll();
        Log.v("MyInfo", list.toString());
        awardsAdapter.setList(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_insert:
                startActivity(new Intent(this,AwardsInsertActivity.class));
                break;
            case R.id.but_queryOrder:
                startActivity(new Intent(this, AwardsStatisticsActivity.class));
                break;
        }
    }
}