package com.example.myapplication.Manager.Manager.Contest;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContestDao;
import com.example.myapplication.domain.Contest;

public class AddContestActivity extends AppCompatActivity {

    EditText et_contest_name,et_contest_introduction,et_contest_time,et_contest_note;
    private void bindUI() {
        //从activity_add_contest.xml 页面中获取对应的UI控件
        et_contest_name = findViewById(R.id.et_contest_name);
        et_contest_introduction = findViewById(R.id.et_contest_introduction);
        et_contest_time = findViewById(R.id.et_contest_time);
        et_contest_note = findViewById(R.id.et_contest_note);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contest);
        bindUI();
    }
    public void AddContest(View view) {
        Contest contest=new Contest(et_contest_name.getText().toString(),et_contest_introduction.getText().toString(),et_contest_time.getText().toString(),et_contest_note.getText().toString());
        ContestDao contestDao=new ContestDao(this);
        contestDao.add(contest);
        Toast.makeText(this,"添加成功 ！",Toast.LENGTH_LONG).show();
        finish();
    }
}