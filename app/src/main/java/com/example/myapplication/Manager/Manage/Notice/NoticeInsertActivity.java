package com.example.myapplication.Manager.Manage.Notice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.NoticeDao;
import com.example.myapplication.domain.AwardsInfo;
import com.example.myapplication.domain.Notice;
import com.example.myapplication.utils.BaseUrl;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NoticeInsertActivity extends AppCompatActivity {
    private static final String TAG = "NoticeInsertActivity";
    private NoticeDao noticeDao;
    private TextView title, content, receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notice_insert);
       // noticeDao = new NoticeDao(this);
        bindUI();
    }

    public void InsertSubmit(View v) throws IOException, InterruptedException {
        String title1 = title.getText().toString().trim();
        String content1 = content.getText().toString().trim();
        String receiver1 = receiver.getText().toString().trim();
        if (title1.length() <= 0) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
        } else if (content1.length() <= 0) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
        } else {
            //noticeDao.add(new Notice(id, title1, content1, null, receiver1));
            add(new Notice(1,title1,content1,null,receiver1));
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
    public void add(final Notice notice) throws IOException, InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "notice/add.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
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
                        } else Log.d(TAG, "添加成功");
                    }
                });
            }
        });
        thread.start();
        thread.join();
        while(thread.isAlive())continue;
        if(!thread.isAlive()){
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}