package com.example.myapplication.User.Mine;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContestDao;
import com.example.myapplication.dao.ContestRegistryDao;
import com.example.myapplication.domain.Contest;
import com.example.myapplication.domain.ContestRegistryMessage;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MineRegActivity extends AppCompatActivity {
    private  ContestDao contestDao;
    private ContestRegistryDao contestRegistryDao;
    private List<ContestRegistryMessage> myContestRegistry;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mine_reg);
        contestRegistryDao=new ContestRegistryDao(this);
        contestDao=new ContestDao(this);
        userName = getIntent().getStringExtra("userName");
        myContestRegistry=contestRegistryDao.findByUsernameId(userName);
        MyRegAdapter myRegAdapter=new MyRegAdapter(this,R.layout.item_listview_mine_reg,myContestRegistry);
        ListView listView=(ListView)findViewById(R.id.lv_mine_reg);
        listView.setAdapter(myRegAdapter);
    }
    class MyRegAdapter extends ArrayAdapter<ContestRegistryMessage> {
        private int resourceId;
        public MyRegAdapter(@NonNull Context context, int resource, @NonNull List<ContestRegistryMessage> objects) {
            super(context, resource, objects);
            resourceId=resource;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final RegHui hui;
            final ContestRegistryMessage contestRegistryMessage=getItem(position);
            Contest contest=contestDao.findById(contestRegistryMessage.getContestId());
                hui=new RegHui();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview_mine_reg,null);
                hui.contestImage = convertView.findViewById(R.id.ContestImage);
                hui.contestName = (TextView) convertView.findViewById(R.id.tv_item_mine_reg_contest_name);
                hui.contestTime = (TextView) convertView.findViewById(R.id.tv_item_mine_reg_contest_time);
                hui.contestNote = (TextView) convertView.findViewById(R.id.tv_item_mine_reg_contest_node);
            hui.contestImage.setImageResource(R.drawable.contest);
            hui.contestName.setText(contest.getContestName()+"(id:"+contest.getContestId()+")");
            hui.contestTime.setText(contest.getContestTime());
            hui.contestNote.setText(contest.getContestNote());
            return convertView;
        }
        class RegHui{
            ImageView contestImage;
            TextView contestName,contestTime,contestNote;
        }
    }

}