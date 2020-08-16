package com.example.myapplication.Manager.Manage.Awards;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.AwardsDao;
import com.example.myapplication.domain.AwardsInfo;
import com.example.myapplication.utils.BaseUrl;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AwardsInsertActivity extends AppCompatActivity {
    private String dd = null;
    private boolean flag = false;
    private boolean flag1 = false;
    private static final String TAG = "AwardsInsertActivity";
   // private AwardsDao awardsDao;
    private TextView STNumber, relName, depName, className, contestName, awardLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_awards_insert);
       // awardsDao = new AwardsDao(this);
        bindUI();
    }

    public void updateInsertSubmit(View v) throws IOException, InterruptedException {
        String STNumber1 = STNumber.getText().toString().trim();
        String relName1 = relName.getText().toString().trim();
        String depName1 = depName.getText().toString().trim();
        String className1 = className.getText().toString().trim();
        String contestName1 = contestName.getText().toString().trim();
        String awardLevel1 = awardLevel.getText().toString().trim();
        if (STNumber1.length() != 12) {
            Toast.makeText(this, "请输入正确的学号", Toast.LENGTH_SHORT).show();
        } else if (contestName1.length() <= 0) {
            Toast.makeText(this, "请输入比赛名称", Toast.LENGTH_SHORT).show();
        } else if (awardLevel1.length() <= 0) {
            Toast.makeText(this, "请输入奖项", Toast.LENGTH_SHORT).show();
        } else if (findBySTNumberAndContestAndAward(STNumber1, contestName1, awardLevel1)!=null) {
            Toast.makeText(this, "当前奖项已经添加,请添加未添加的奖项", Toast.LENGTH_SHORT).show();
        } else {
            AwardsInfo awardsInfo=new AwardsInfo(null, STNumber1, relName1, className1, contestName1, awardLevel1, depName1);
            add(awardsInfo);
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            finish();
        }
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
    public void add(final AwardsInfo awardsInfo) throws IOException, InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "awards/add.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
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
                            flag = true;
                            Log.d(TAG, "添加成功");
                        }
                    }
                });
            }
        });
        thread.start();
        thread.join();
        while(thread.isAlive())continue;
    }
    public String findBySTNumberAndContestAndAward(final String STNumber1, final String contestName1, final String awardLevel1) throws IOException, InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
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
                        } else {
                            throw new IOException("Unexpected code " + response);
                        }
                    }
                });
            }
        });
        thread1.start();
        thread1.join();
        while(thread1.isAlive())continue;
        return dd;
    }
}