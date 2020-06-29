package com.example.myapplication.User.Mine;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.dao.AwardsDao;
import com.example.myapplication.domain.AwardsInfo;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MineAwardActivity extends AppCompatActivity {

    private AwardsDao awardsDao;
    private List<AwardsInfo> data;
    private String userName;
    private MyAdapter adapter;
    private ListView lv_mine_award;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mine_award);
        lv_mine_award = findViewById(R.id.lv_mine_award);
        userName = getIntent().getStringExtra("userName");
        //Log.v("MyInfo1", userName);
        awardsDao = new AwardsDao(this);
        data = awardsDao.findBySTNumber(userName);
//        Log.v("MyInfo1", data.toString());
        adapter = new MyAdapter();
        lv_mine_award.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(MineAwardActivity.this, R.layout.item_listview_mine_award, null);
                holder.contestName = convertView.findViewById(R.id.tv_mine_award_constName);
                holder.awardLevel = convertView.findViewById(R.id.tv_mine_award_awardLevel);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            AwardsInfo awardsInfo = data.get(position);
            holder.contestName.setText(awardsInfo.getContestName());
            holder.awardLevel.setText(awardsInfo.getAwardLevel());
            return convertView;
        }
    }
    class ViewHolder { //视图的容器
        public TextView contestName, awardLevel;
    }
}