package com.example.myapplication.Manager.Manage.Notice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContestRegistryDao;
import com.example.myapplication.dao.MessageDao;
import com.example.myapplication.dao.NoticeDao;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.ContestRegistry;
import com.example.myapplication.domain.Message;
import com.example.myapplication.domain.Notice;
import com.example.myapplication.domain.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoticeSendActivity extends AppCompatActivity {

    private NoticeDao noticeDao;
    private MessageDao messageDao;
    private UserDao userDao;
    private ContestRegistryDao contestRegistryDao;
    private TextView title, content, receiver;
    private Notice notice;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notice_send);
        Intent intent = getIntent();
        context = NoticeSendActivity.this;
        String id = intent.getStringExtra("ID");
        Log.v("MyInfo", id + "");
        userDao = new UserDao(NoticeSendActivity.this);
        noticeDao = new NoticeDao(NoticeSendActivity.this);
        messageDao = new MessageDao(NoticeSendActivity.this);
        contestRegistryDao = new ContestRegistryDao(NoticeSendActivity.this);
        notice = noticeDao.findByNoticeId(Integer.valueOf(id));
        bindUI();
        showUI();
    }
    //修改通知
    public void UpdateSubmit(View view) {
        String title1 = title.getText().toString().trim();
        String content1 = content.getText().toString().trim();
        String receiver1 = receiver.getText().toString().trim();
        if (title1.length() <= 0) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
        } else if (content1.length() <= 0) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
        } else {
            noticeDao.update(new Notice(notice.get_id(), title1, content1, null, receiver1));
            notice = noticeDao.findByNoticeId(notice.get_id());
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        }
    }
    //发送通知
    public void SentSubmit(View view) {
        String title1 = title.getText().toString().trim();
        String content1 = content.getText().toString().trim();
        String receiver1 = receiver.getText().toString().trim();
        if (title1.length() <= 0) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
        } else if (content1.length() <= 0) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
        } else if (receiver1.length() != 0 && receiver1.length() != 12 && (receiver1.charAt(0)>='0'&&receiver1.charAt(0)<='9')) {
            Toast.makeText(this, "请输入正确的接收者学号", Toast.LENGTH_SHORT).show();
        } else if (receiver1.length() == 0){
            List<User> users = userDao.findAll();
            for(int i=0;i<users.size();++i){
                String userId = users.get(i).getUserName();
                SendMsg(userId);
            }
            Toast.makeText(this, "通知已发送至所有用户", Toast.LENGTH_SHORT).show();
        } else if (receiver1.charAt(0)<'0'||receiver1.charAt(0)>'9'){
            List<String> list = contestRegistryDao.findAllUserIdByContestName(receiver1);
            for(int i=0;i<list.size();++i){
                String userId = list.get(i);
                Log.v("部分用户ID",userId);
                SendMsg(userId);
            }
            Toast.makeText(this, "通知已发送至部分用户", Toast.LENGTH_SHORT).show();
        } else {
            SendMsg(receiver1);
            Toast.makeText(this, "通知已发送至特定用户", Toast.LENGTH_SHORT).show();
        }
    }
    private void showUI() {
        title.setText(notice.getTitle());
        content.setText(notice.getContent());
        receiver.setText(notice.getReceiver());
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

    //发送通知
    private void SendMsg(String userId) {
        String title1 = title.getText().toString().trim();
        String content1 = content.getText().toString().trim();
        String time = getTime();
        messageDao.add(new Message(messageDao.findEmptyMessageId(),userId,title1,content1,time));
        //1 获取系统通知管理员
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //2 获取通知构造者
        Notification.Builder builder = new Notification.Builder(context);
        //3 添加自定义通知视图
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_listview_message);
        //4 设置主要参数 给自定义布局的控件赋值
        remoteViews.setTextViewText(R.id.tv_message_title,title1);
        remoteViews.setTextViewText(R.id.tv_message_content,content1);
        remoteViews.setTextViewText(R.id.tv_message_time,time);
        builder.setContent(remoteViews);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true); //点击取消通知
        //关闭通知
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,new Intent(),0);
        builder.setContentIntent(pendingIntent);

        //更新通知，发送通知
        notificationManager.notify(1,builder.build());
    }


    //获取时间
    private String getTime() {
        //获取系统当前时间
        long time = System.currentTimeMillis();
        //将格式化的日期转化成Date对象
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(time);
        //将date对象转换为String对象
        String time1 = format.format(date);
        return time1;
    }
}