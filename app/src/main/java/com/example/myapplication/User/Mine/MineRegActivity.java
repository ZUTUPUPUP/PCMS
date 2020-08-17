package com.example.myapplication.User.Mine;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.dao.ContestDao;
import com.example.myapplication.dao.ContestRegistryDao;
import com.example.myapplication.domain.Contest;
import com.example.myapplication.domain.ContestRegistry;
import com.example.myapplication.domain.User;
import com.example.myapplication.utils.BaseUrl;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MineRegActivity extends AppCompatActivity {
    private  ContestDao contestDao;
    private ContestRegistryDao contestRegistryDao;
    //private List<ContestRegistryMessage> myContestRegistry;
    private String userName;
    private User user;
    private ListView listView;
    boolean flag = false;

    private static final String TAG = "MineRegActivity";
    OkHttpClient client = new OkHttpClient();
    private static final int GET = 1;
    private static final int POST = 2;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET:
                    Toast.makeText(MineRegActivity.this, "get数据请求成功", Toast.LENGTH_SHORT).show();
                    break;
                case POST:
                    MyRegAdapter myRegAdapter = new MyRegAdapter();
                    listView.setAdapter(myRegAdapter);
                    Toast.makeText(MineRegActivity.this, "post数据请求成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mine_reg);
        //contestRegistryDao = new ContestRegistryDao(this);
        //contestDao = new ContestDao(this);
        //myContestRegistry = contestRegistryDao.findByUsernameId(userName);

        userName = getIntent().getStringExtra("userName");
        listView = (ListView)findViewById(R.id.lv_mine_reg);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "user/findByUserName.do";
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("userName", userName)
                        .build();
                System.out.println(body);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG,"<<<<e="+e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()) {
                            String d = response.body().string();
                            Log.d(TAG,"<<<<d=" + d);
                            user = JSON.parseObject(d, User.class);
                            Message msg = Message.obtain();
                            msg.what = POST;
                            handler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();

    }
    class MyRegAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return user.getContestRegistries().size();
        }

        @Override
        public Object getItem(int position) {
            return user.getContestRegistries().get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(MineRegActivity.this, R.layout.item_listview_mine_reg, null);
                holder.contestImage = convertView.findViewById(R.id.ContestImage);
                holder.contestName = (TextView) convertView.findViewById(R.id.tv_item_mine_reg_contest_name);
                holder.contestTime = (TextView) convertView.findViewById(R.id.tv_item_mine_reg_contest_time);
                holder.contestNote = (TextView) convertView.findViewById(R.id.tv_item_mine_reg_contest_node);

            }
            ContestRegistry contestRegistry = user.getContestRegistries().get(position);
            Contest contest = contestRegistry.getContest();
            holder.contestImage.setImageResource(R.drawable.contest);
            holder.contestName.setText(contest.getContestName() + "(id:"+contest.getContestId()+")");
            holder.contestTime.setText(contest.getContestTime());
            holder.contestNote.setText(contest.getContestNote());
            return convertView;
        }
        class ViewHolder {
            ImageView contestImage;
            TextView contestName,contestTime,contestNote;
        }
    }

}