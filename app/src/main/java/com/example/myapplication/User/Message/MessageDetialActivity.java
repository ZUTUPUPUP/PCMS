package com.example.myapplication.User.Message;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.dao.MessageDao;
import com.example.myapplication.domain.Message;
import com.example.myapplication.domain.Notice;
import com.example.myapplication.utils.BaseUrl;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageDetialActivity extends AppCompatActivity {
    private static final String TAG = "MessageDetialActivity";
    private Message message = null;
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
       // messageDao = new MessageDao(context);
       //  message = messageDao.findById(userId);
        bindUI();
        try {
            findById(userId);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void DeleteSubmit(View v) throws IOException, InterruptedException {
        //messageDao.deleteById(message.get_id());
        delete(message);
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
    public void delete(final Message message) throws IOException, InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "message/delete.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("_id", message.get_id() + "")
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
                        } else {
                            Log.d(TAG, message.getTitle() + "已删");
                        }
                    }
                });
            }
        });
        thread.start();
        thread.join();
        while(thread.isAlive())continue;
        if(!thread.isAlive()){
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    public void findById(final int id) throws IOException, InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "message/findById.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("_id", id + "")
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
                            Log.d("message =", d);
                            message = JSON.parseObject(d, Message.class);
                        } else throw new IOException("Unexpected code " + response);
                    }
                });
            }
        });
        thread1.start();
        thread1.join();
        while(thread1.isAlive()||message==null)continue;
        if(!thread1.isAlive()){
            showUI();
        }
    }
}