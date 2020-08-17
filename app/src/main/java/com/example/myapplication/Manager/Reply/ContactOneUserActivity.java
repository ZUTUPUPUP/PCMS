package com.example.myapplication.Manager.Reply;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.Login_Register.RegisterActivity;
import com.example.myapplication.Login_Register.RegisterAdapter;
import com.example.myapplication.R;
import com.example.myapplication.dao.ContactDao;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.Contact;
import com.example.myapplication.domain.Dep;
import com.example.myapplication.utils.BaseUrl;
import com.example.myapplication.utils.Contact.MassageItemAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Request;

//用户界面下，管理员和一个用户聊天
public class ContactOneUserActivity extends AppCompatActivity {

    private String userName;
    private ListView showListView;
    private ScrollView scrollView;
    private Button btn_sendmes;
    private EditText ed_input;
    private ContactDao contactDao;
    private MassageItemAdapter adapter;
    private List<Contact> contactList;
    private TextView userNameView;
    private List<Contact> adContactList; //适配器里的list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        setContentView(R.layout.activity_manager_contact);
        init();//初始化部件和参数
        getHistoryContact();//读取历史消息
        setBtnSendMes();//设置发送信息
    }
    /**
     * get请求
     */
    public void getDataGetByOKHttpUtils() {
        String url = BaseUrl.BASE_URL + "contact/findAll.do";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MMyStringCallback());
    }

    public class MMyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            //setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            ///setTitle("Sample-okHttp");
        }

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {
            e.printStackTrace();
//            tv_result.setText();
//            Toast.makeText(RegisterActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
           // Log.v(TAG, "onError:" + e.getMessage());
        }
        @Override
        public void onResponse(String response, int id) {
            Log.e("ContactOneUserUserAct", "onResponse：complete");
//            tv_result.setText();
//            Toast.makeText(RegisterActivity.this, "onResponse:" + response, Toast.LENGTH_SHORT).show();
           // Log.v(TAG, "onResponse:" + response);
            switch (id) {
                case 100:
                  //  Toast.makeText(RegisterActivity.this, "http:数据请求成功",
                  //          Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                   // Toast.makeText(RegisterActivity.this, "https:数据请求成功",
                  //          Toast.LENGTH_SHORT).show();
                    break;
            }
            if(response != null) {
                //解析数据
                parseData(response);
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            //Log.e(TAG, "inProgress:" + progress);
        }
    }

    private void parseData(String json) {
        contactList = JSON.parseArray(json, Contact.class);
        adContactList = new ArrayList<>();
        //开延迟，使得可以先显示底部最新消息
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        for(Contact contact:contactList){
            if(contact.getSenderId().equals(userName)||contact.getReceiverId().equals(userName)){
                adContactList.add(contact);
            }
        }
        //筛选出admin和user的对话
        adapter = new MassageItemAdapter(this,R.layout.item_contactmessage, adContactList);
        showListView.setAdapter(adapter);
        showListView.setSelection(adapter.getCount()-1);

    }

    /**
     * post请求
     */
    public void postOKHttpUtils(Contact contact) {
        String url = BaseUrl.BASE_URL + "contact/insertOne.do";
        OkHttpUtils
                .get()
                .url(url)
                .id(1001)
                .addParams("timestamp",contact.getTimestamp())
                .addParams("senderId","admin")
                .addParams("receiverId",contact.getReceiverId())
                .addParams("mas",contact.getMas())
                .build()
                .execute(new PostStringCallback());
    }

    public class  PostStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            //setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            ///setTitle("Sample-okHttp");
        }

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {
            e.printStackTrace();
//            tv_result.setText();
//            Toast.makeText(RegisterActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            // Log.v(TAG, "onError:" + e.getMessage());
        }
        @Override
        public void onResponse(String response, int id) {
            Log.e("ContactOneUserUserAct", "onResponse：complete");
//            tv_result.setText();
//            Toast.makeText(RegisterActivity.this, "onResponse:" + response, Toast.LENGTH_SHORT).show();
            // Log.v(TAG, "onResponse:" + response);
            switch (id) {
                case 100:
                    //  Toast.makeText(RegisterActivity.this, "http:数据请求成功",
                    //          Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    // Toast.makeText(RegisterActivity.this, "https:数据请求成功",
                    //          Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            //Log.e(TAG, "inProgress:" + progress);
        }
    }
    public  void init(){
        Intent getIntent = getIntent();
        userName = getIntent.getStringExtra("userName");  //System.out.println(userName);
        userNameView = (TextView)findViewById(R.id.tv);
        UserDao userDao =new UserDao(this);
        String realName=userDao.findByUserName(userName).getNickName();
        userNameView.setText(realName+'\n'+userName);
        contactDao = new ContactDao(this);
        scrollView = (ScrollView)findViewById(R.id.sv);
        showListView = (ListView) findViewById(R.id.show_listView);
    }
    private void getHistoryContact() {
        getDataGetByOKHttpUtils();
    }

    private void setBtnSendMes() {
        btn_sendmes = (Button)findViewById(R.id.btn_sendmas);
        ed_input = (EditText)findViewById(R.id.ed_input);
        btn_sendmes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact= new Contact(-1,null,"admin",userName,ed_input.getText().toString());
                //得到当前日期
                Calendar calendar= Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String nowDate=(sdf.format(calendar.getTime()));
                contact.setTimestamp(nowDate);
                postOKHttpUtils(contact);
                //插入一个聊天记录后要刷新ListVeiw
                adContactList.add(contact);
                adapter.notifyDataSetChanged();//关键
                //强制到底
                scrollView.post(new Runnable() {
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
                ed_input.setText("");//清空
                //ed_input.requestFocus();
            }
        });
    }
}


