package com.example.myapplication.User.Message;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.MessageDao;
import com.example.myapplication.domain.Message;
import com.example.myapplication.domain.Notice;

public class MessageDetialActivity extends AppCompatActivity {

    private Message message;
    private TextView title, content, time;
    private MessageDao messageDao;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_message_detial);
        Intent intent = getIntent();
        String id = intent.getStringExtra("userId");
        int userId = Integer.parseInt(id);
        context = MessageDetialActivity.this;
        messageDao = new MessageDao(context);
        message = messageDao.findById(userId);
        bindUI();
        showUI();
    }

    public void DeleteSubmit(View v) {
        messageDao.deleteById(message.get_id());
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showUI() {
        title.setText(message.getTitle());
        title.setEnabled(false); //去掉点击时编辑框下面横线
        title.setFocusable(false); //不可编辑
        title.setFocusableInTouchMode(false);//不可编辑
        title.setTextIsSelectable(true); //可复制
        content.setText(message.getContent());
        content.setEnabled(false);
        content.setFocusable(false);
        content.setFocusableInTouchMode(false);
        content.setTextIsSelectable(true);
        time.setText(message.getTime());
        time.setEnabled(false);
        time.setFocusable(false);
        time.setFocusableInTouchMode(false);
        time.setTextIsSelectable(false);
    }

    private void bindUI() {
        title = findViewById(R.id.et_message_delete_title);
        content = findViewById(R.id.et_message_delete_content);
        time = findViewById(R.id.et_message_delete_time);
    }
}