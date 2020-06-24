package com.example.myapplication.Manager.Manage.Awards;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.AwardsDao;
import com.example.myapplication.domain.AwardsInfo;

import androidx.appcompat.app.AppCompatActivity;

public class AwardsUpdateActivity extends AppCompatActivity {

    private AwardsDao awardsDao;
    private TextView STNumber, relName, depName, className, contestName, awardLevel;
    private AwardsInfo awardsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_awards_update);
        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        Log.v("MyInfo", id + "");
        awardsDao = new AwardsDao(AwardsUpdateActivity.this);
        awardsInfo = awardsDao.findByUserId(Integer.valueOf(id));
        bindUI();
        showUI();
    }

    public void updateAwardSubmit(View view) {
        String STNumber1 = STNumber.getText().toString().trim();
        String relName1 = relName.getText().toString().trim();
        String depName1 = depName.getText().toString().trim();
        String className1 = className.getText().toString().trim();
        String contestName1 = contestName.getText().toString().trim();
        String awardLevel1 = awardLevel.getText().toString().trim();
        if (STNumber1.length() != 12) {
            Toast.makeText(this, "请输入正确的学号", Toast.LENGTH_SHORT).show();
        } else if (contestName1.length() <= 0) {
            Toast.makeText(this, "请输入比赛名称", Toast.LENGTH_SHORT).show();
        } else if (awardLevel1.length() <= 0) {
            Toast.makeText(this, "请输入奖项", Toast.LENGTH_SHORT).show();
        } else if (awardsDao.findBySTNumberAndContestAndAward(STNumber1, contestName1, awardLevel1) != null) {
            Toast.makeText(this, "当前奖项已经存在,请修改成不存在的奖项", Toast.LENGTH_SHORT).show();
        } else {
            awardsDao.update(new AwardsInfo(awardsInfo.get_id(), STNumber1, relName1, className1, contestName1, awardLevel1, depName1));
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void showUI() {
        STNumber.setText(awardsInfo.getSTNumber());
        relName.setText(awardsInfo.getRelName());
        depName.setText(awardsInfo.getDepName());
        className.setText(awardsInfo.getClassName());
        contestName.setText(awardsInfo.getContestName());
        awardLevel.setText(awardsInfo.getAwardLevel());
    }

    private void bindUI() {
        STNumber = findViewById(R.id.et_update_award_STNumber);
        relName = findViewById(R.id.et_update_award_relName);
        depName = findViewById(R.id.et_update_award_depName);
        className = findViewById(R.id.et_update_award_className);
        contestName = findViewById(R.id.et_update_award_contestName);
        awardLevel = findViewById(R.id.et_update_award_awardLevel);
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.iv_update_award_delSTNumber:
                STNumber.setText("");
                break;
            case R.id.iv_update_award_delRelName:
                relName.setText("");
                break;
            case R.id.iv_update_award_delDepName:
                depName.setText("");
                break;
            case R.id.iv_update_award_delClassName:
                className.setText("");
                break;
            case R.id.iv_update_award_delContestName:
                contestName.setText("");
                break;
            case R.id.iv_update_award_delAwardLevel:
                awardLevel.setText("");
                break;
            default:
                break;
        }
    }


}