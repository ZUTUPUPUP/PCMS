package com.example.myapplication.User.Mine;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.Manager.Manage.UserManage.QueryAllUsersActivity;
import com.example.myapplication.R;
import com.example.myapplication.dao.AwardsDao;
import com.example.myapplication.domain.AwardsInfo;
import com.example.myapplication.utils.BaseUrl;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MineAwardActivity extends AppCompatActivity {

    //private AwardsDao awardsDao;
    private List<AwardsInfo> data;
    private String userName;
    private MyAdapter adapter;
    private ListView lv_mine_award;
    private static final String TAG = "MineAwardActivity";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    adapter = new MyAdapter();
                    lv_mine_award.setAdapter(adapter);
                    Toast.makeText(MineAwardActivity.this, "查询成功,共"+data.size()+"条获奖信息", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mine_award);
        lv_mine_award = findViewById(R.id.lv_mine_award);
        userName = getIntent().getStringExtra("userName");
        findAwardsBySTNumber(userName);
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

    public void findAwardsBySTNumber(final String userName) {
        new Thread(new Runnable() {
            public void run() {
                String url = BaseUrl.BASE_URL + "awards/findBySTNumber.do";
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("STNumber", userName)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "<<<<e=" + e);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String d = response.body().string();
                            Log.d(TAG, "<<<<d=" + d);
                            data = JSON.parseArray(d, AwardsInfo.class);
                            android.os.Message msg = android.os.Message.obtain();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();
    }
}