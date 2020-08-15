package com.example.myapplication.Manager.News;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Manager.ManagerActivity;
import com.example.myapplication.Manager.Reply.ContactOneUserActivity;
import com.example.myapplication.R;
import com.example.myapplication.dao.NewsDao;
import com.example.myapplication.domain.Contact;
import com.example.myapplication.domain.News;
import com.example.myapplication.utils.BaseUrl;
import com.example.myapplication.utils.News.PermisionUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.Request;

public class NewsPreview extends AppCompatActivity {


    private ImageView[] imageView;
    private Intent getIntent;
    private TextView[] textView;
    private String[] url;
    private String[] content;
    private TextView headline;
    private String hl;
    private Button btn_setNews;
   // private NewsDao newsDao;
    private TextView head;
    private TextView brief;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        setContentView(R.layout.activity_news_preview);

        Build();
        ButtonBuild();
    }
    /**
     * post请求
     */
    public void postOKHttpUtils(News news) {
        String url = BaseUrl.BASE_URL + "news/insertOne.do";
        OkHttpUtils
                .get()
                .url(url)
                .id(1001)
                .addParams("contestId",news.getContestId())
                .addParams("head",news.getHead())
                .addParams("date",news.getDate())
                .addParams("brief",news.getBrief())
                .addParams("t0",news.getT0())
                .addParams("t1",news.getT1())
                .addParams("t2",news.getT2())
                .addParams("t3",news.getT3())
                .addParams("t4",news.getT4())
                .addParams("t5",news.getT5())
                .addParams("t6",news.getT6())
                .addParams("t7",news.getT7())
                .addParams("t8",news.getT8())
                .addParams("t9",news.getT9())
                .addParams("t10",news.getT10())
                .addParams("t11",news.getT11())
                .addParams("t12",news.getT12())
                .build()
                .execute(new PostStringCallback());
    }

    public class  PostStringCallback extends StringCallback {
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
            Log.e("ContactOneUserUserAct", "onResponse：complete");
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
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            //Log.e(TAG, "inProgress:" + progress);
        }
    }
    private void ButtonBuild() {
        btn_setNews = (Button)findViewById(R.id.setNews);
       // newsDao = new NewsDao(this);
        btn_setNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到当前时间
                Calendar calendar= Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String nowDate=(sdf.format(calendar.getTime()));

                News news=new News(-1,getIntent.getStringExtra("contestId"),head.getText().toString(),nowDate,brief.getText().toString(),getIntent.getStringExtra("0"),
                        getIntent.getStringExtra("1"),getIntent.getStringExtra("2"),getIntent.getStringExtra("3"),
                        getIntent.getStringExtra("4"),getIntent.getStringExtra("5"),getIntent.getStringExtra("6"),
                        getIntent.getStringExtra("7"),getIntent.getStringExtra("8"),getIntent.getStringExtra("9"),
                        getIntent.getStringExtra("10"),getIntent.getStringExtra("11"),getIntent.getStringExtra("12"));
                postOKHttpUtils(news);
                Toast.makeText(NewsPreview.this, "发布新闻成功", Toast.LENGTH_SHORT).show();
                Intent nowIntent=new Intent(NewsPreview.this, ManagerActivity.class);
                nowIntent.putExtra("cid",getIntent.getStringExtra("contestId"));
                nowIntent.putExtra("ii",1);
                nowIntent.putExtra("userName",getIntent.getStringExtra("userName"));
                startActivity(nowIntent);

            }
        });
    }

    private void Build() {
        getIntent = getIntent();

        head = (TextView)findViewById(R.id.head);
        head.setText(getIntent.getStringExtra("100"));
        brief =(TextView)findViewById(R.id.brief);
        brief.setText(getIntent.getStringExtra("101"));
        date = (TextView)findViewById(R.id.date);
        date.setText(getIntent.getStringExtra("0000-00-00 00:00"));
        headline = (TextView)findViewById(R.id.e0);
        hl = getIntent.getStringExtra("0");
        headline.setText(hl);
        imageView = new ImageView[4];
        imageView[0]=(ImageView)findViewById(R.id.e1);
        imageView[1]=(ImageView)findViewById(R.id.e4);
        imageView[2]=(ImageView)findViewById(R.id.e7);
        imageView[3]=(ImageView)findViewById(R.id.e10);
        url = new  String[4];
        url[0]=getIntent.getStringExtra("1");
        url[1]=getIntent.getStringExtra("4");
        url[2]=getIntent.getStringExtra("7");
        url[3]=getIntent.getStringExtra("8");
        for(int i=0;i<4;i++){
            String path =   url[i];
            File mFile=new File(path);
            //若该文件存在
            if (mFile.exists()) {
                Bitmap bitmap=BitmapFactory.decodeFile(path);
                imageView[i].setImageBitmap(bitmap);
            }
        }

        textView = new TextView[4];
        textView[0]=(TextView)findViewById(R.id.e2);
        textView[1]=(TextView)findViewById(R.id.e5);
        textView[2]=(TextView)findViewById(R.id.e8);
        textView[3]=(TextView)findViewById(R.id.e11);
        content = new String[4];
        content[0]=getIntent.getStringExtra("2");
        content[1]=getIntent.getStringExtra("5");
        content[2]=getIntent.getStringExtra("8");
        content[3]=getIntent.getStringExtra("11");
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
        bt[0]=getIntent.getStringExtra("3");
        bt[1]=getIntent.getStringExtra("6");
        bt[2]=getIntent.getStringExtra("9");
        bt[3]=getIntent.getStringExtra("12");
        for(int i=0;i<4;i++){
            if(bt[i].length()==0)continue;
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) button[i].getLayoutParams();
            lp.setMargins(10, 10, 10, 10);
            lp.height= 100;
            button[i].setText(bt[i]);
            button[i].setLayoutParams(lp);
            if(bt[i].equals("reg")){
                button[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NewsPreview.this,TemporaryRegister.class);
                        startActivity(intent);
                    }
                });

            }
            if(bt[i].equals("awa")){
                button[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NewsPreview.this,TemporaryAward.class);
                        startActivity(intent);
                    }
                });
            }
        }
    }


}