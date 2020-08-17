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

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.dao.ContestRegistryDao;
import com.example.myapplication.dao.MessageDao;
import com.example.myapplication.dao.NoticeDao;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.AwardsInfo;
import com.example.myapplication.domain.Message;
import com.example.myapplication.domain.Notice;
import com.example.myapplication.domain.User;
import com.example.myapplication.utils.BaseUrl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NoticeSendActivity extends AppCompatActivity {
    private static final String TAG = "NoticeSendActivity";
    private NoticeDao noticeDao;
    private MessageDao messageDao;
    private UserDao userDao;
    private ContestRegistryDao contestRegistryDao;
    private TextView title, content, receiver;
    private Notice notice = null;
    private Context context;
    private List<User> users = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notice_send);
        Intent intent = getIntent();
        context = NoticeSendActivity.this;
        String id = intent.getStringExtra("ID");
        Log.v("MyInfo", id + "");
        //userDao = new UserDao(context);
        //noticeDao = new NoticeDao(context);
        //messageDao = new MessageDao(context);
        //contestRegistryDao = new ContestRegistryDao(context);
        bindUI();
        try {
            assert id != null;
            findByNoticeId(Integer.parseInt(id));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
    //修改通知
    public void UpdateSubmit(View view) throws IOException, InterruptedException {
        String title1 = title.getText().toString().trim();
        String content1 = content.getText().toString().trim();
        String receiver1 = receiver.getText().toString().trim();
        if (title1.length() <= 0) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
        } else if (content1.length() <= 0) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
        } else {
            //noticeDao.update(new Notice(notice.get_id(), title1, content1, null, receiver1));
            // notice = noticeDao.findByNoticeId(notice.get_id());
            notice = new Notice(notice.get_id(), title1, content1, "", receiver1);
            update(notice);
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        }
    }
    //发送通知
    public void SentSubmit(View view) throws IOException, InterruptedException {
        findAllUsers();
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
    private void SendMsg(){
        String title1 = title.getText().toString().trim();
        String content1 = content.getText().toString().trim();
        String time = getTime();
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
    public void findByNoticeId(final int _id) throws IOException, InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "notice/findByNoticeId.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("_id", _id + "")
                        .build();
                //System.out.println(body);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "<<<<e=" + e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String d = response.body().string();
                            Log.d("d=", d);
                            notice = JSON.parseObject(d, Notice.class);
                        } else throw new IOException("Unexpected code " + response);
                    }
                });
            }
        });
        thread.start();
        thread.join();
        while(thread.isAlive()||notice==null)continue;
        if(!thread.isAlive()){
            showUI();
        }
    }
    public void update(final Notice notice) throws IOException, InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "notice/update.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("_id",notice.get_id()+"")
                        .add("title", notice.getTitle())
                        .add("content", notice.getContent())
                        .add("time", notice.getTime())
                        .add("receiver", notice.getReceiver())
                        .build();
                //System.out.println(body);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "<<<<e=" + e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        } else Log.d(TAG, "更新成功");
                    }
                });
            }
        });
        thread1.start();
        thread1.join();
        while(thread1.isAlive())continue;
    }
    public void findAllUsers() throws IOException, InterruptedException {
        String title1 = title.getText().toString().trim();
        String content1 = content.getText().toString().trim();
        String receiver1 = receiver.getText().toString().trim();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "user/findAllUsers.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .build();
                //System.out.println(body);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "<<<<e=" + e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String d = response.body().string();
                            users = JSON.parseArray(d,User.class);
                        } else throw new IOException("Unexpected code " + response);
                    }
                });
            }
        });
        thread2.start();
        thread2.join();
        while(thread2.isAlive()&&users==null){ continue; }
        if(!thread2.isAlive()&&users!=null){
            Log.d("users->",users.toString());
            if (title1.length() <= 0) {
                Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
            } else if (content1.length() <= 0) {
                Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            } else if (receiver1.length() != 0 && receiver1.length() != 12 && (receiver1.charAt(0)>='0'&&receiver1.charAt(0)<='9')) {
                Toast.makeText(this, "请输入正确的接收者学号", Toast.LENGTH_SHORT).show();
            } else if (receiver1.length() == 0){
                //List<User> users = userDao.findAll();
                String time = getTime();
                SendMsg();
                for(int i=0;i<users.size();++i){
                    String userId = users.get(i).getUserName();
                    //messageDao.add(new Message(messageDao.findEmptyMessageId(),userId,title1,content1,time));
                    addMessage(new Message(0,userId,title1,content1,time));
                }
                Toast.makeText(this, "通知已发送至所有用户", Toast.LENGTH_SHORT).show();
            } /*else if (receiver1.charAt(0)<'0'||receiver1.charAt(0)>'9'){
                List<String> list = contestRegistryDao.findAllUserIdByContestName(receiver1);
                for(int i=0;i<list.size();++i){
                    String userId = list.get(i);
                    SendMsg(userId);
                }
                Toast.makeText(this, "通知已发送至部分用户", Toast.LENGTH_SHORT).show();
            } */else {
                SendMsg();
                String time = getTime();
                //messageDao.add(new Message(0,receiver1,title1,content1,time));
                addMessage(new Message(0,receiver1,title1,content1,time));
                Toast.makeText(this, "通知已发送至指定用户", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void addMessage(final Message message) throws IOException, InterruptedException {
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "message/add.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("userId", message.getUserId())
                        .add("title", message.getTitle())
                        .add("content", message.getContent())
                        .add("time", message.getTime())
                        .build();
                //System.out.println(body);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "<<<<e=" + e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        } else Log.d(TAG, "添加成功");
                    }
                });
            }
        });
        thread3.start();
        thread3.join();
        while(thread3.isAlive())continue;
    }
}