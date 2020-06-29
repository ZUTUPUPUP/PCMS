package com.example.myapplication.Manager.News;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;
//跳转的注册页面
public class TemporaryRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporary_register);
        Intent intent=getIntent();
        TextView textView= findViewById(R.id.tv);
        String t=intent.getStringExtra("cid");
        textView.setText(t);
    }
}