package com.example.myapplication.User.Home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.myapplication.Manager.News.TemporaryAward;
import com.example.myapplication.Manager.News.TemporaryRegister;
import com.example.myapplication.R;
import com.example.myapplication.dao.NewsDao;
import com.example.myapplication.domain.News;
import com.example.myapplication.utils.BaseUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Request;

public class NewsOne extends AppCompatActivity {
    private ImageView[] imageView;
    private Intent getIntent;
    private TextView[] textView;
    private String[] url;
    private String[] content;
    private TextView headline;
    private TextView date;
    //private NewsDao newsDao;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        setContentView(R.layout.activity_news_one);
        getDataGetByOKHttpUtils();
    }
    /**
     * get请求
     */
    public void getDataGetByOKHttpUtils() {
        String url = BaseUrl.BASE_URL + "news/findAll.do";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MMyStringCallback());
    }

    public class MMyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            //setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            ///setTitle("Sample-okHttp");
        }

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {
            e.printStackTrace();
//            tv_result.setText();
//            Toast.makeText(RegisterActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            // Log.v(TAG, "onError:" + e.getMessage());
        }
        @Override
        public void onResponse(String response, int id) {
            //  Log.e(TAG, "onResponse：complete");
//            tv_result.setText();
//            Toast.makeText(RegisterActivity.this, "onResponse:" + response, Toast.LENGTH_SHORT).show();
            // Log.v(TAG, "onResponse:" + response);
            switch (id) {
                case 100:
                    //  Toast.makeText(RegisterActivity.this, "http:数据请求成功",
                    //          Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    // Toast.makeText(RegisterActivity.this, "https:数据请求成功",
                    //          Toast.LENGTH_SHORT).show();
                    break;
            }
            if(response != null) {
                //解析数据
                BuildUI(response);
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            //Log.e(TAG, "inProgress:" + progress);
        }
    }
    private void BuildUI(String response) {
        List<News> allNews = JSON.parseArray(response, News.class);
        getIntent = getIntent();
        String d=getIntent.getStringExtra("date");
        for(News nn:allNews){
            if(nn.getDate().equals(d)) news=nn;
        }
        headline = (TextView)findViewById(R.id.e0);
        headline.setText(news.getT0());
        date=(TextView)findViewById(R.id.date);
        date.setText(news.getDate());
        imageView = new ImageView[4];
        imageView[0]=(ImageView)findViewById(R.id.e1);
        imageView[1]=(ImageView)findViewById(R.id.e4);
        imageView[2]=(ImageView)findViewById(R.id.e7);
        imageView[3]=(ImageView)findViewById(R.id.e10);
        url = new  String[4];
        url[0]=news.getT1();
        url[1]=news.getT4();
        url[2]=news.getT7();
        url[3]=news.getT10();
        for(int i=0;i<4;i++){
            //通过网络加载图片
            Glide.with(this).load(url[i]).into(imageView[i]);

            // String path =   url[i];
           /* File mFile=new File(path);
            //若该文件存在
            if (mFile.exists()) {
                Bitmap bitmap= BitmapFactory.decodeFile(path);
                imageView[i].setImageBitmap(bitmap);
            }*/
            // imageView[i].setImageBitmap(returnBitMap(path));
        }
        textView = new TextView[4];
        textView[0]=(TextView)findViewById(R.id.e2);
        textView[1]=(TextView)findViewById(R.id.e5);
        textView[2]=(TextView)findViewById(R.id.e8);
        textView[3]=(TextView)findViewById(R.id.e11);
        content = new String[4];
        content[0]=news.getT2();
        content[1]=news.getT5();
        content[2]=news.getT8();
        content[3]=news.getT11();
        for(int i=0;i<4;i++){
            if(content[i].length()>0){
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) textView[i].getLayoutParams();
                lp.setMargins(10, 10, 10, 10);
                lp.height= ViewGroup.LayoutParams.WRAP_CONTENT;
                textView[i].setLayoutParams(lp);
                textView[i].setText(content[i]+'\n');
            }

        }

        Button button[]=new Button[4];
        button[0]=(Button)findViewById(R.id.e3);
        button[1]=(Button)findViewById(R.id.e6);
        button[2]=(Button)findViewById(R.id.e9);
        button[3]=(Button)findViewById(R.id.e12);
        String bt[]=new String[4];
        bt[0]=news.getT3();
        bt[1]=news.getT6();
        bt[2]=news.getT9();
        bt[3]=news.getT12();
        for(int i=0;i<4;i++){
            if(bt[i].length()==0)continue;
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) button[i].getLayoutParams();
            lp.setMargins(10, 10, 10, 10);
            lp.height= 100;

            button[i].setLayoutParams(lp);
            if(bt[i].equals("reg")){
                button[i].setText("报名");
                button[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NewsOne.this,TemporaryRegister.class);
                        intent.putExtra("userName",getIntent.getStringExtra("userName"));
                        intent.putExtra("cid",news.getContestId());
                        startActivity(intent);
                    }
                });

            }
            if(bt[i].equals("awa")){
                button[i].setText("获奖");
                button[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NewsOne.this,TemporaryAward.class);
                        intent.putExtra("userName",getIntent.getStringExtra("userName"));
                        intent.putExtra("cid",news.getContestId());
                        startActivity(intent);
                    }
                });
            }
        }
    }
}