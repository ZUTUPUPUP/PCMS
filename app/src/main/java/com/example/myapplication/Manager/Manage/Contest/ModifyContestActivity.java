package com.example.myapplication.Manager.Manage.Contest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContestDao;
import com.example.myapplication.domain.Contest;

public class ModifyContestActivity extends AppCompatActivity {

    EditText et_contest_name,et_contest_introduction,et_contest_time,et_contest_note;
    private void bindUI() {
        //从activity_add_contest.xml 页面中获取对应的UI控件
        et_contest_name = findViewById(R.id.et_modify_contest_name);
        et_contest_name.setText(getIntent().getStringExtra("name"));
        et_contest_introduction = findViewById(R.id.et_modify_contest_introduction);
        et_contest_introduction.setText(getIntent().getStringExtra("introduction"));
        et_contest_time = findViewById(R.id.et_modify_contest_time);
        et_contest_time.setText(getIntent().getStringExtra("time"));
        et_contest_note = findViewById(R.id.et_modify_contest_note);
        et_contest_note.setText(getIntent().getStringExtra("note"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_contest);
        bindUI();
    }

    public void modifyContest(View view) {
        ContestDao contestDao=new ContestDao(this);
        String idd=getIntent().getStringExtra("id");
        int id=Integer.parseInt(idd);
        Contest contest=new Contest(id,et_contest_name.getText().toString(),et_contest_introduction.getText().toString(),et_contest_time.getText().toString(),et_contest_note.getText().toString());
        contestDao.modifyContest(contest);
        Toast.makeText(this,"修改成功！",Toast.LENGTH_LONG);
        finish();
    }

    public void deleteContest(View view) {
        ContestDao contestDao=new ContestDao(this);
        Contest contest=new Contest(Integer.parseInt(getIntent().getStringExtra("id")),et_contest_name.getText().toString(),et_contest_introduction.getText().toString(),et_contest_time.getText().toString(),et_contest_note.getText().toString());
        contestDao.deleteContest(contest);
        Toast.makeText(this,"删除成功！",Toast.LENGTH_LONG);
        finish();
    }
}