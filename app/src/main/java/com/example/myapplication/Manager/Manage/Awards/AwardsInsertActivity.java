package com.example.myapplication.Manager.Manage.Awards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.AwardsDao;
import com.example.myapplication.domain.AwardsInfo;

public class AwardsInsertActivity extends AppCompatActivity implements View.OnClickListener{

    private AwardsDao awardsDao;
    private EditText ed_id, ed_name, ed_college, ed_type, ed_level;
    private AwardsInfo awardsInfo;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awards_insert);
        ed_id = findViewById(R.id.ed_id);
        ed_name = findViewById(R.id.ed_name);
        ed_college = findViewById(R.id.ed_college);
        ed_type = findViewById(R.id.ed_type);
        ed_level = findViewById(R.id.ed_level);
        add = findViewById(R.id.but_add);
        awardsDao = new AwardsDao(this);
        add.setOnClickListener(this);

    }

    private void SqlInsert() {
        AwardsInfo awardsInfo1 = new AwardsInfo();
        awardsInfo1.setUserId(ed_id.getText().toString());
        awardsInfo1.setUserName(ed_name.getText().toString());
        awardsInfo1.setCollege(ed_college.getText().toString());
        awardsInfo1.setCompetitionType(ed_type.getText().toString());
        awardsInfo1.setAwardLevel(ed_level.getText().toString());
        awardsDao.add(awardsInfo1);
        Toast.makeText(AwardsInsertActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_add:
                SqlInsert();
                break;
        }
    }
}