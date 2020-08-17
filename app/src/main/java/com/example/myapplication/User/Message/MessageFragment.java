package com.example.myapplication.User.Message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.Manager.Manage.UserManage.QueryAllUsersActivity;
import com.example.myapplication.R;
import com.example.myapplication.dao.MessageDao;
import com.example.myapplication.domain.Message;
import com.example.myapplication.domain.User;
import com.example.myapplication.utils.BaseUrl;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MessageFragment extends Fragment {

    private static String TAG = "MessageFragment";
    private View view;
    private ListView listView;
   // private MessageDao messageDao;
    private List<Message> list = null;
    private MessageAdapter adapter;
    private String userName;
    public MessageFragment() {
        // Required empty public constructor
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    adapter = new MessageAdapter(view.getContext(),list);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Message message = list.get(position);
                            //打开私聊页面
                            int _id = message.get_id();
                            Intent intent=new Intent(view.getContext(), MessageDetialActivity.class);
                            intent.putExtra("userId",_id+"");
                            startActivity(intent);
                        }
                    });
                    break;
                case 2:
                    adapter.setList(list);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message, container, false);
       // messageDao = new MessageDao(getContext());
        initUI();
        getMessage();
        return view;
    }

    private void getMessage() {
        Intent MainIntent=getActivity().getIntent();//得到main里传进来的intent
        String json= MainIntent.getStringExtra("user");
        userName = JSON.parseObject(json, User.class).getUserName();
        //list = messageDao.findByUserId(userName);
        findByUserId(userName);
        Log.v("用户名：",userName);
    }

    private void initUI(){
        listView = view.findViewById(R.id.lv_message);
    }

    //Activity创建或者从被覆盖、后台重新回到前台时被调用
    @Override
    public void onResume() {
        super.onResume();
        //重新获取list数据
        findAll(userName);
        /*list = messageDao.findByUserId(userName);
        adapter.setList(list);*/

    }

    public void findByUserId(final String userId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "message/findByUserId.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("userId", userId)
                        .build();
                //System.out.println(body);
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
                            Log.d("d=", d);
                            list = JSON.parseArray(d, Message.class);
                            android.os.Message msg = android.os.Message.obtain();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        } else throw new IOException("Unexpected code " + response);
                    }
                });
            }
        }).start();
    }
    public void findAll(final String userId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "message/findByUserId.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("userId", userId)
                        .build();
                //System.out.println(body);
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
                            Log.d("d=", d);
                            list = JSON.parseArray(d, Message.class);
                            android.os.Message msg = android.os.Message.obtain();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        } else throw new IOException("Unexpected code " + response);
                    }
                });
            }
        }).start();
    }
}