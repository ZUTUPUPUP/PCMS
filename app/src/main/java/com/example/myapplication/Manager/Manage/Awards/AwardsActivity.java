package com.example.myapplication.Manager.Manage.Awards;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.domain.AwardsInfo;
import com.example.myapplication.utils.BaseUrl;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AwardsActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "AwardsActivity";
    private OkHttpClient client = new OkHttpClient();
    private Button bt1,bt2;
    private EditText et_award_query_contestName, et_award_query_username, et_award_query_award;
    private AwardsAdapter awardsAdapter;
    private List<AwardsInfo> list = null;
    //private AwardsDao awardsDao= new AwardsDao(this);;
    ListView listView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    awardsAdapter = new AwardsAdapter(AwardsActivity.this, list);
                    listView.setAdapter(awardsAdapter);
                    break;
                case 2:
                    awardsAdapter.setList(list);
                    et_award_query_username.setText("");
                    et_award_query_contestName.setText("");
                    et_award_query_award.setText("");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_awards);
        listView = findViewById(R.id.lv_awards_list);
        et_award_query_contestName = findViewById(R.id.et_award_query_contestName);
        et_award_query_username = findViewById(R.id.et_award_query_username);
        et_award_query_award = findViewById(R.id.et_award_query_award);
        bt1 = findViewById(R.id.but_insert);
        bt2 = findViewById(R.id.but_queryOrder);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        findAll();
    }

    public void findAllAwardMessageByPrint(View v) {
        final String STNumber = et_award_query_username.getText().toString().trim();
        final String contestName = et_award_query_contestName.getText().toString().trim();
        final String awardLevel = et_award_query_award.getText().toString().trim();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "awards/findLikeByPrint.do";
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("STNumber", STNumber)
                        .add("contestName",contestName)
                        .add("awardLevel",awardLevel)
                        .build();
                System.out.println(body);
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
                            list = JSON.parseArray(d, AwardsInfo.class);
                            android.os.Message msg = android.os.Message.obtain();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();
    }
    /*public void findAllAwardMessageByPrint(View v) {
        String STNumber = et_award_query_username.getText().toString().trim();
        String contestName = et_award_query_contestName.getText().toString().trim();
        String award = et_award_query_award.getText().toString().trim();
        //Log.v("MyInfo1", STNumber + " " + contestName + " " + award);
        list = awardsDao.findLikeByPrint(STNumber, contestName, award);
        awardsAdapter.setList(list);
        et_award_query_username.setText("");
        et_award_query_contestName.setText("");
        et_award_query_award.setText("");
    }*/
    //Activity创建或者从被覆盖、后台重新回到前台时被调用
    @Override
    protected void onResume() {
        super.onResume();
        //重新获取list数据
        Log.v("Process", "->onResume");
        findAll();
        /*
        list = awardsDao.findAll();

        awardsAdapter.setList(list);
        */
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_insert:
                startActivity(new Intent(this,AwardsInsertActivity.class));
                break;
            case R.id.but_queryOrder:
                startActivity(new Intent(this, AwardsStatisticsActivity.class));
                break;
        }
    }

    public void findAll() {
        new Thread(new Runnable() {
            public void run() {
                String url = BaseUrl.BASE_URL + "awards/findAll.do";
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
                            list = JSON.parseArray(d, AwardsInfo.class);
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