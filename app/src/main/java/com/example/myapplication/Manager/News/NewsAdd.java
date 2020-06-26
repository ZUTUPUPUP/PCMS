package com.example.myapplication.Manager.News;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.utils.News.PermisionUtils;

public class NewsAdd extends AppCompatActivity {

    private EditText editText[];
    private Button btn_preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        setContentView(R.layout.activity_news_add);
       BuildUI();

    }
    private void BuildUI() {
        editText = new EditText[13];
        editText[0]=findViewById(R.id.e0);
        editText[1]=findViewById(R.id.e1);
        editText[2]=findViewById(R.id.e2);
        editText[3]=findViewById(R.id.e3);
        editText[4]=findViewById(R.id.e4);
        editText[5]=findViewById(R.id.e5);
        editText[6]=findViewById(R.id.e6);
        editText[7]=findViewById(R.id.e7);
        editText[8]=findViewById(R.id.e8);
        editText[9]=findViewById(R.id.e9);
        editText[10]=findViewById(R.id.e10);
        editText[11]=findViewById(R.id.e11);
        editText[12]=findViewById(R.id.e12);
        btn_preview = findViewById(R.id.perShow);
        btn_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewsAdd.this,NewsPreview.class);
                for(int i=0;i<=12;i++){
                    intent.putExtra(Integer.toString(i),editText[i].getText().toString());
                }
                startActivity(intent);
            }
        });
    }
}
