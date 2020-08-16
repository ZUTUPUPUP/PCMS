package com.example.myapplication.Manager.Manage.Contest;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContestDao;
import com.example.myapplication.domain.Contest;

import androidx.appcompat.app.AppCompatActivity;

public class AddContestActivity extends AppCompatActivity {
    ContestDao contestDao=new ContestDao(this);
    Contest contest;
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
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contest);
        bindUI();
    }
    public void AddContest(View view) {
        contest=new Contest(et_contest_name.getText().toString(),et_contest_introduction.getText().toString(),et_contest_time.getText().toString(),et_contest_note.getText().toString());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                contestDao.add(contest);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,"添加成功 ！",Toast.LENGTH_LONG).show();
        finish();
    }
}