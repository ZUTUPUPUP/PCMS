package com.example.myapplication.Manager.Manage.Notice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.AwardsDao;
import com.example.myapplication.dao.NoticeDao;
import com.example.myapplication.domain.AwardsInfo;
import com.example.myapplication.domain.Notice;

import java.util.List;

public class NoticeInsertActivity extends AppCompatActivity {

    private NoticeDao noticeDao;
    private TextView title, content, receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notice_insert);
        noticeDao = new NoticeDao(this);
        bindUI();
    }

    public void InsertSubmit(View v) {
        String title1 = title.getText().toString().trim();
        String content1 = content.getText().toString().trim();
        String receiver1 = receiver.getText().toString().trim();
        if (title1.length() <= 0) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
        } else if (content1.length() <= 0) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
        } else {
            int id=noticeDao.findEmptyNoticeId();
            noticeDao.add(new Notice(id, title1, content1, null, receiver1));
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void bindUI() {
        title = findViewById(R.id.et_notice_insert_title);
        content = findViewById(R.id.et_notice_insert_content);
        receiver = findViewById(R.id.et_notice_insert_receiver);
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.et_notice_insert_title:
                title.setText("");
                break;
            case R.id.et_notice_insert_content:
                content.setText("");
                break;
            case R.id.et_notice_insert_receiver:
                receiver.setText("");
                break;
            default:
                break;
        }
    }
}