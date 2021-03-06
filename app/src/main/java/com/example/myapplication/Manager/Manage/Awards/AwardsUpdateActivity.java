package com.example.myapplication.Manager.Manage.Awards;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.dao.AwardsDao;
import com.example.myapplication.domain.AwardsInfo;
import com.example.myapplication.domain.User;
import com.example.myapplication.utils.BaseUrl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AwardsUpdateActivity extends AppCompatActivity {
    private String dd = null;
    private static final String TAG = "AwardsUpdateActivity";
    private AwardsDao awardsDao;
    private AwardsInfo awardsInfo = null;
    private AwardsInfo repeatAward = null;
    private TextView STNumber, relName, depName, className, contestName, awardLevel;
    private String STNumber1, relName1, depName1, className1, contestName1, awardLevel1;
    private String id;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(AwardsUpdateActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case 2:
                    showUI();
                    break;
                case 3:
                    if(repeatAward != null){
                        Toast.makeText(AwardsUpdateActivity.this, "当前奖项已经存在,请修改成不存在的奖项", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        update(new AwardsInfo(Integer.parseInt(id), STNumber1, relName1, className1, contestName1, awardLevel1, depName1));
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_awards_update);
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        Log.v("MyInfo", id+"");
        //awardsDao = new AwardsDao(AwardsUpdateActivity.this);
        bindUI();
        findByUserId(Integer.parseInt(id));

        //awardsInfo = JSON.parseObject(dp, AwardsInfo.class);
        //Log.v("MyInfo", awardsInfo.toString());
        //showUI();
    }

    public void updateAwardSubmit(View view) throws IOException, InterruptedException {
        STNumber1 = STNumber.getText().toString().trim();
        relName1 = relName.getText().toString().trim();
        depName1 = depName.getText().toString().trim();
        className1 = className.getText().toString().trim();
        contestName1 = contestName.getText().toString().trim();
        awardLevel1 = awardLevel.getText().toString().trim();
        if (STNumber1.length() != 12) {
            Toast.makeText(this, "请输入正确的学号", Toast.LENGTH_SHORT).show();
        } else if (contestName1.length() <= 0) {
            Toast.makeText(this, "请输入比赛名称", Toast.LENGTH_SHORT).show();
        } else if (awardLevel1.length() <= 0) {
            Toast.makeText(this, "请输入奖项", Toast.LENGTH_SHORT).show();
        } else {
            findRepeatAwards(STNumber1, contestName1, awardLevel1);
        }
    }

    private void showUI() {
        //Log.d("awardsInfo->>>>>",awardsInfo.toString());
        STNumber.setText(awardsInfo.getSTNumber());
        relName.setText(awardsInfo.getRelName());
        depName.setText(awardsInfo.getDepName());
        className.setText(awardsInfo.getClassName());
        contestName.setText(awardsInfo.getContestName());
        awardLevel.setText(awardsInfo.getAwardLevel());
    }

    private void bindUI() {
        STNumber = findViewById(R.id.et_update_award_STNumber);
        relName = findViewById(R.id.et_update_award_relName);
        depName = findViewById(R.id.et_update_award_depName);
        className = findViewById(R.id.et_update_award_className);
        contestName = findViewById(R.id.et_update_award_contestName);
        awardLevel = findViewById(R.id.et_update_award_awardLevel);
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.iv_update_award_delSTNumber:
                STNumber.setText("");
                break;
            case R.id.iv_update_award_delRelName:
                relName.setText("");
                break;
            case R.id.iv_update_award_delDepName:
                depName.setText("");
                break;
            case R.id.iv_update_award_delClassName:
                className.setText("");
                break;
            case R.id.iv_update_award_delContestName:
                contestName.setText("");
                break;
            case R.id.iv_update_award_delAwardLevel:
                awardLevel.setText("");
                break;
            default:
                break;
        }
    }
    public void findByUserId(final int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "awards/findByUserId.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("id", id + "")
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
                            Log.d("d==", d);
                            awardsInfo = JSON.parseObject(d, AwardsInfo.class);
                            Message msg = Message.obtain();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        } else throw new IOException("Unexpected code " + response);
                    }
                });
            }
        }).start();
    }
    public void update(final AwardsInfo awardsInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "awards/update.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("id", awardsInfo.get_id() + "")
                        .add("STNumber", awardsInfo.getSTNumber())
                        .add("relName", awardsInfo.getRelName())
                        .add("className", awardsInfo.getClassName())
                        .add("contestName", awardsInfo.getContestName())
                        .add("awardLevel", awardsInfo.getAwardLevel())
                        .add("depName", awardsInfo.getDepName())
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
                            Message msg = Message.obtain();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();
    }
    public void findRepeatAwards(final String STNumber1, final String contestName1, final String awardLevel1) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "awards/findBySTNumberAndContestAndAward.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("STNumber", STNumber1)
                        .add("contestName", contestName1)
                        .add("awardLevel", awardLevel1)
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
                            dd = response.body().string();
                            Message msg = Message.obtain();
                            msg.what = 3;
                            handler.sendMessage(msg);
                        } else {
                            dd = null;
                            throw new IOException("Unexpected code " + response);
                        }
                    }
                });
            }
        }).start();
    }
}