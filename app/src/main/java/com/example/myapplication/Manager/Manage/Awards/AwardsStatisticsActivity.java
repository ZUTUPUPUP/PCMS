package com.example.myapplication.Manager.Manage.Awards;

import android.os.Bundle;
import android.view.Window;

import com.example.myapplication.R;

import androidx.appcompat.app.AppCompatActivity;

public class AwardsStatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_awards_query);
    }
}