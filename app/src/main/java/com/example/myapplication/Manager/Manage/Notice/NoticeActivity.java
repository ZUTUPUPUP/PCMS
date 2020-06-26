package com.example.myapplication.Manager.Manage.Notice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.dao.NoticeDao;
import com.example.myapplication.domain.Notice;

import java.util.List;

public class NoticeActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv;
    private NoticeAdapter noticeAdapter;
    private List<Notice> list;
    private NoticeDao noticeDao;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notice);
        listView = findViewById(R.id.lv_notice_list);
        iv = findViewById(R.id.imageRightView);
        iv.setOnClickListener(this);
        noticeDao = new NoticeDao(this);
        list = noticeDao.findAll();
        noticeAdapter = new NoticeAdapter(this, list);
        listView.setAdapter(noticeAdapter);
    }

    //Activity创建或者从被覆盖、后台重新回到前台时被调用
    @Override
    protected void onResume() {
        super.onResume();
        //重新获取list数据
        list = noticeDao.findAll();
        Log.v("MyInfo", list.toString());
        noticeAdapter.setList(list);
    }
    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, NoticeInsertActivity.class));
    }

}