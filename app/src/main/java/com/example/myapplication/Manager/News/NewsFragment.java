package com.example.myapplication.Manager.News;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Manager.News.NewsAdd;
import com.example.myapplication.R;
import com.example.myapplication.User.Mine.Contact.UserContactActivity;


public class NewsFragment extends Fragment {


    private View view;
    private Button addNews;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        BuildUI();
        return view;
    }

    private void BuildUI() {
        addNews = (Button) view.findViewById(R.id.add);
        addNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), NewsAdd.class);
                startActivity(intent);
            }
        });
    }


}
