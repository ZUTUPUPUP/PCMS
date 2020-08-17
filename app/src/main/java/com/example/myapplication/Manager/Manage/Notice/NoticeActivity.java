package com.example.myapplication.Manager.Manage.Notice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.Manager.Manage.Awards.AwardsActivity;
import com.example.myapplication.Manager.Manage.Awards.AwardsAdapter;
import com.example.myapplication.R;
import com.example.myapplication.User.Message.MessageDetialActivity;
import com.example.myapplication.dao.NoticeDao;
import com.example.myapplication.domain.AwardsInfo;
import com.example.myapplication.domain.Notice;
import com.example.myapplication.utils.BaseUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NoticeActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "NoticeActivity";
    private ImageView iv;
    private NoticeAdapter noticeAdapter;
    private List<Notice> list = null;
    //private NoticeDao noticeDao;
    ListView listView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    noticeAdapter = new NoticeAdapter(NoticeActivity.this, list);
                    listView.setAdapter(noticeAdapter);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notice);
        listView = findViewById(R.id.lv_notice_list);
        iv = findViewById(R.id.imageRightView);
        iv.setOnClickListener(this);
        findAll();
        //noticeDao = new NoticeDao(this);
        /*list = noticeDao.findAll();
        noticeAdapter = new NoticeAdapter(this, list);
        listView.setAdapter(noticeAdapter);*/
    }

    //Activity创建或者从被覆盖、后台重新回到前台时被调用
    @Override
    protected void onResume() {
        super.onResume();
        //重新获取list数据
        Log.v("OnResume", "++");
        findAll();
       /* list = noticeDao.findAll();

        noticeAdapter.setList(list);*/
    }
    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, NoticeInsertActivity.class));
    }

    public void findAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "notice/findAll.do";
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
                            Log.d(TAG, "<<<<d=" + d);
                            list = JSON.parseArray(d, Notice.class);
                            android.os.Message msg = android.os.Message.obtain();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();
    }
}