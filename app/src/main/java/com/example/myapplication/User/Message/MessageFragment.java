package com.example.myapplication.User.Message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;


public class MessageFragment extends Fragment {


   public MessageFragment(){
        // Required empty public constructor
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_content, container, false);
        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        txt_content.setText("服务器比赛前给参赛者发通知\n恭喜你荣获优胜奖，请去辅导班领奖");
        return view;
    }
}