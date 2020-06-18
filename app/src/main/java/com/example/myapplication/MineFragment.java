package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MineFragment extends Fragment {

    private View view;
    private Button btn_contact;
    private String userName;

    public MineFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        txt_content.setText("个人信息管理\n竞赛信息管理\n留言互动管理\n获奖信息查询");
        initContact();
        return view;
    }

    private void initContact() {
        Intent MainIntent=getActivity().getIntent();//得到main里传进来的intent
        userName = MainIntent.getStringExtra("userName");
        System.out.print(userName);
        btn_contact = (Button)view.findViewById(R.id.contact);
        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), UserContactActivity.class);
                intent.putExtra("userName", userName);//传到联系管理员的界面
                startActivity(intent);
            }
        });

    }
}