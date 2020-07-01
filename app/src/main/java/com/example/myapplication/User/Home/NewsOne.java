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

import com.example.myapplication.Manager.News.TemporaryAward;
import com.example.myapplication.Manager.News.TemporaryRegister;
import com.example.myapplication.R;
import com.example.myapplication.dao.NewsDao;
import com.example.myapplication.domain.News;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;

public class NewsOne extends AppCompatActivity {
    private ImageView[] imageView;
    private Intent getIntent;
    private TextView[] textView;
    private String[] url;
    private String[] content;
    private TextView headline;
    private TextView date;
    private NewsDao newsDao;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        setContentView(R.layout.activity_news_one);
        BuildUI();
    }
    private void BuildUI() {
        getIntent = getIntent();
        int id=getIntent.getIntExtra("id",1);
        newsDao = new NewsDao(this);
        news = newsDao.findById(id);
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
            String path =   url[i];
            File mFile=new File(path);
            //若该文件存在
            if (mFile.exists()) {
                Bitmap bitmap= BitmapFactory.decodeFile(path);
                imageView[i].setImageBitmap(bitmap);
            }
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