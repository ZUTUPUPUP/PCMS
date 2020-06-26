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

import com.example.myapplication.R;
import com.example.myapplication.utils.News.PermisionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NewsPreview extends AppCompatActivity {


    private ImageView[] imageView;
    private Intent getIntent;
    private TextView[] textView;
    private String[] url;
    private String[] content;
    private TextView headline;
    private String hl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        setContentView(R.layout.activity_news_preview);
        BuildUI();
    }

    private void BuildUI() {
        getIntent = getIntent();
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