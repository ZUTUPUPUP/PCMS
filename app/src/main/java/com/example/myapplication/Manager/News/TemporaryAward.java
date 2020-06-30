package com.example.myapplication.Manager.News;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.dao.AwardsDao;
import com.example.myapplication.dao.ContestDao;
import com.example.myapplication.domain.AwardsInfo;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
//跳转的获奖页面
public class TemporaryAward extends AppCompatActivity {

    private ListView lv_mine_query_award_listview;
    private EditText et_award_query_STNumber;
    private ContestDao contestDao;
    private AwardsDao awardsDao;
    private List<AwardsInfo> data;
    private MyAdapter adapter;
    private String contestName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_temporary_award);
        lv_mine_query_award_listview = findViewById(R.id.lv_mine_query_award_listview);
        et_award_query_STNumber = findViewById(R.id.et_award_query_STNumber);
        Intent intent = getIntent();
        TextView textView = findViewById(R.id.tv);
        String cid = intent.getStringExtra("cid");
        //Log.v("MyInfo", cid + "");
        contestDao = new ContestDao(this);
        awardsDao = new AwardsDao(this);
        //Log.v("MyInfo", contestRegistryDao.findByContestId(Integer.valueOf(cid)).toString());
        contestName = contestDao.findById(Integer.valueOf(cid)).getContestName();
        data = awardsDao.findByContestName(contestName);
        textView.setText(contestName + "获奖名单");
        adapter = new MyAdapter();
        lv_mine_query_award_listview.setAdapter(adapter);
    }

    public void AwardBySTNumber(View view) {
        String STNumber = et_award_query_STNumber.getText().toString().trim();
        data = awardsDao.findLikeByPrint(STNumber, contestName, "");
        adapter.notifyDataSetChanged();
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
                convertView = View.inflate(TemporaryAward.this, R.layout.item_listview_contest_awards, null);
                holder.relName = convertView.findViewById(R.id.tv_item_query_award_relName);
                holder.award = convertView.findViewById(R.id.tv_item_query_award_award);
                holder.STNumber = convertView.findViewById(R.id.tv_item_query_award_STNumber);
                holder.depName = convertView.findViewById(R.id.tv_item_query_award_depName);
                holder.className = convertView.findViewById(R.id.tv_item_query_award_calssName);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final AwardsInfo awardsInfo = data.get(position);
            holder.relName.setText(awardsInfo.getRelName());
            holder.award.setText(awardsInfo.getAwardLevel());
            holder.STNumber.setText(awardsInfo.getSTNumber());
            holder.depName.setText(awardsInfo.getDepName());
            holder.className.setText(awardsInfo.getClassName());
            return convertView;
        }
    }
    class ViewHolder { //视图的容器
        public TextView relName;
        private TextView award;
        private TextView STNumber;
        private TextView depName;
        private TextView className;
    }
}