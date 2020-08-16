package com.example.myapplication.Manager.Manage.Notice;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notice);
        listView = findViewById(R.id.lv_notice_list);
        iv = findViewById(R.id.imageRightView);
        iv.setOnClickListener(this);
        getDataGetByOKHttpUtils();
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
        try {
            findAll();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
       /* list = noticeDao.findAll();
        Log.v("MyInfo", list.toString());
        noticeAdapter.setList(list);*/
    }
    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, NoticeInsertActivity.class));
    }

    /**
     * post请求
     */
    public void getDataGetByOKHttpUtils() {
        String url = BaseUrl.BASE_URL + "notice/findAll.do";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new NoticeActivity.MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {
            e.printStackTrace();
            Log.v(TAG, "onError:" + e.getMessage());
        }
        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
            Log.v(TAG, "onResponse:" + response);
            switch (id) {
                case 100:
                    Toast.makeText(NoticeActivity.this, "http:数据请求成功",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(NoticeActivity.this, "https:数据请求成功",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
            if(response != null) {
                //解析数据
                parseData(response);
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
        }
    }

    private void parseData(String json) {
        list = JSON.parseArray(json, Notice.class);
        Log.v("MyInfo", list.toString());
        noticeAdapter = new NoticeAdapter(this, list);
        listView.setAdapter(noticeAdapter);
    }

    public void findAll() throws IOException, InterruptedException {
        Thread thread = new Thread(new Runnable() {
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
                        }
                    }
                });
            }
        });
        thread.start();
        thread.join();
        while(thread.isAlive()&&list==null)continue;
        if(!thread.isAlive()){
            noticeAdapter=new NoticeAdapter(this,list);
        }
    }
}