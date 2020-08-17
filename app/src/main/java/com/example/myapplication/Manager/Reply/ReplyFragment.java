package com.example.myapplication.Manager.Reply;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.Login_Register.RegisterActivity;
import com.example.myapplication.Login_Register.RegisterAdapter;
import com.example.myapplication.R;
import com.example.myapplication.dao.ContactDao;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.Contact;
import com.example.myapplication.domain.Dep;
import com.example.myapplication.domain.User;
import com.example.myapplication.utils.BaseUrl;
import com.example.myapplication.utils.Contact.UserListItemAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Request;


public class ReplyFragment extends Fragment {



    private ListView sshowListView;
    // private ContactDao contactDao;
    private View view;
    private List<Contact> contactList;
    private UserListItemAdapter adapter;
  //  private List<Contact> allContacts;
    private boolean longer;
    private ImageButton btn_reflash;
    private ImageButton btn_searchUser;
    private EditText input;
    private List<Contact> data;
    private List<User> list;//所有用户信息

    public ReplyFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reply, container, false);
        // System.out.println(new String("ypy踩踩").length());
        FselectListGetDataGetByOKHttpUtils();//加载userlist
        initUI();
        // getUserList();//读取用户列表
        //加载有聊天记录的user
        UserListGetDataGetByOKHttpUtils();
        //刷新按钮的监听
        btn_reflash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserListGetDataGetByOKHttpUtils();
            }
        });
        return view;
    }
    public void FselectListGetDataGetByOKHttpUtils() {
        String url = BaseUrl.BASE_URL + "user/findAll.do";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new FselectMyStringCallback());
    }

    public class FselectMyStringCallback extends StringCallback {
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
            // Log.v("ReplyFragment", "onError:" + e.getMessage());
        }
        @Override
        public void onResponse(String response, int id) {
            // Log.e("ReplyFragment", "onResponse：complete");
//            tv_result.setText();
//            Toast.makeText(RegisterActivity.this, "onResponse:" + response, Toast.LENGTH_SHORT).show();
            //   Log.v("ReplyFragment", "onResponse:" + response);

            if(response != null) {
                //解析数据
                FSelectUserListParseData(response);
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            //Log.e("ReplyFragment", "inProgress:" + progress);
        }
    }

    private void FSelectUserListParseData(String json) { //得到所有聊天记录，加载user列表
        list = JSON.parseArray(json, User.class);
    }

   /* public void getUserList() {

        contactDao = new ContactDao(getContext());
        allContacts = contactDao.findAll();
        Collections.reverse(allContacts);
        contactList = new ArrayList<>();
        Set hs = new HashSet();
        for(Contact contact: allContacts){
            String uuuu=contact.getSenderId();
            if(uuuu.equals("admin")) uuuu=contact.getReceiverId();
            if(hs.contains(uuuu))continue;
            hs.add(uuuu);
            //超出部分删去
            String mes=contact.getMas();
            longer = false;
            mes=substringForWidth(mes,mes.length(),new TextPaint());
            if(longer) mes=mes+"...";
            contact.setMas(mes);
            contactList.add(contact);
        }
        adapter = new UserListItemAdapter(view.getContext(),R.layout.item_contactuserlist, contactList);
        sshowListView.setAdapter(adapter);
        //设置点击单个Item事件
        sshowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contactList.get(position);
                //打开私聊页面
                String userName = contact.getSenderId();
                if(userName.equals("admin")) userName=contact.getReceiverId();
                Intent intent=new Intent(view.getContext(), ContactOneUserActivity.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });
    }*/

    /**
     * get
     */
    public void selectListGetDataGetByOKHttpUtils() {
        String url = BaseUrl.BASE_URL + "user/findAll.do";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new selectMyStringCallback());
    }

    public class selectMyStringCallback extends StringCallback {
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
            // Log.v("ReplyFragment", "onError:" + e.getMessage());
        }
        @Override
        public void onResponse(String response, int id) {
            // Log.e("ReplyFragment", "onResponse：complete");
//            tv_result.setText();
//            Toast.makeText(RegisterActivity.this, "onResponse:" + response, Toast.LENGTH_SHORT).show();
            //   Log.v("ReplyFragment", "onResponse:" + response);

            if(response != null) {//拿到数据
                //解析数据
                SelectUserListParseData(response);
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            //Log.e("ReplyFragment", "inProgress:" + progress);
        }
    }

    private void SelectUserListParseData(String json) { //得到所有聊天记录，加载user列表
        list = JSON.parseArray(json, User.class);
        Log.v("MyInfo", data.toString());
        //输入的id，匹配用
        String id=input.getText().toString();
        Set<String> st = new HashSet();
        for(User user: list){
            if(user.getStatus_id()==1)continue;
            if(user.getUserName().contains(id)==false)continue;
            st.add(user.getUserName());
        }
        contactList.clear();
        //筛选出每个用户的最后一条消息，并按时间顺序排序
        Set hs = new HashSet();
        for(Contact contact: data){
            String uuuu=contact.getSenderId();
            if(uuuu.equals("admin")) uuuu=contact.getReceiverId();
            if(hs.contains(uuuu))continue;
            if(st.contains(uuuu)==false)continue;
            st.remove(uuuu);
            hs.add(uuuu);
            //超出部分删去
            String mes=contact.getMas();
            longer = false;
            mes=substringForWidth(mes,mes.length(),new TextPaint());
            if(longer) mes=mes+"...";
            contact.setMas(mes);
            contactList.add(contact);
        }
        for(String user:st) {
            String nickName="";
            for(User user1:list)if(user.equals(user1.getUserName())) nickName=user1.getNickName();
            contactList.add(new Contact(-1,"",user,nickName,""));
        }
        adapter = new UserListItemAdapter(view.getContext(),R.layout.item_contactuserlist, contactList);
        sshowListView.setAdapter(adapter);
    }
    private void initUI() {
        //找到搜索的按钮
        btn_searchUser = (ImageButton)view.findViewById(R.id.btn_searchUser);
        //刷新按钮
        btn_reflash = (ImageButton)view.findViewById(R.id.btn_reflash);
        //搜索输入框
        input = (EditText)view.findViewById(R.id.ed_input);
        // final UserDao userDao=new UserDao(getContext());
        btn_searchUser.setOnClickListener(new View.OnClickListener() {//查找用户的监听
            @Override
            public void onClick(View v) {
                selectListGetDataGetByOKHttpUtils();
              /*  String id=input.getText().toString();
                List<User>list=userDao.findByLikeUserName(id);
                Set<String> st = new HashSet();
                for(User user:list){
                    if(user.getStatus_id()==1)continue;
                    st.add(user.getUserName());
                }
                contactList.clear();
                Set hs = new HashSet();
                for(Contact contact: allContacts){
                    String uuuu=contact.getSenderId();
                    if(uuuu.equals("admin")) uuuu=contact.getReceiverId();
                    if(hs.contains(uuuu))continue;
                    if(st.contains(uuuu)==false)continue;
                    st.remove(uuuu);
                    hs.add(uuuu);
                    //超出部分删去
                    String mes=contact.getMas();
                    longer = false;
                    mes=substringForWidth(mes,mes.length(),new TextPaint());
                    if(longer) mes=mes+"...";
                    contact.setMas(mes);
                    contactList.add(contact);
                }
                for(String user:st) {
                    contactList.add(new Contact(-1,"",user,"admin",""));
                }
                adapter = new UserListItemAdapter(view.getContext(),R.layout.item_contactuserlist, contactList);
                sshowListView.setAdapter(adapter);*/
            }
        });
        sshowListView = (ListView) view.findViewById(R.id.sshow_listView);
    }
    //截取Text的固定长度
    public String substringForWidth(String str, int length, Paint textPaint){
        Rect rect = new Rect();
        str = str.substring(0, length);
        textPaint.getTextBounds(str, 0, str.length(), rect);
        if(rect.width() >= 180){
            longer=true;
            return substringForWidth(str,str.length()-1,textPaint);
        }
        return str;
    }
    /**
     * get
     */
    public void UserListGetDataGetByOKHttpUtils() {
        String url = BaseUrl.BASE_URL + "contact/findAll.do";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
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
            // Log.v("ReplyFragment", "onError:" + e.getMessage());
        }
        @Override
        public void onResponse(String response, int id) {
            // Log.e("ReplyFragment", "onResponse：complete");
//            tv_result.setText();
//            Toast.makeText(RegisterActivity.this, "onResponse:" + response, Toast.LENGTH_SHORT).show();
            //   Log.v("ReplyFragment", "onResponse:" + response);

            if(response != null) {
                //解析数据
                UserListParseData(response);
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            //Log.e("ReplyFragment", "inProgress:" + progress);
        }
    }

    private void UserListParseData(String json) { //得到所有聊天记录，加载user列表
        data = JSON.parseArray(json, Contact.class);
        if(list==null) return ;
        Log.v("MyInfo", data.toString());
        Collections.reverse(data);
        contactList = new ArrayList<>();
        Set hs = new HashSet();
        for(Contact contact: data){
            String uuuu=contact.getSenderId();
            if(uuuu.equals("admin")) uuuu=contact.getReceiverId();
            if(hs.contains(uuuu))continue;
            hs.add(uuuu);
            //超出部分删去
            String mes=contact.getMas();
            longer = false;
            mes=substringForWidth(mes,mes.length(),new TextPaint());
            if(longer) mes=mes+"...";
            contact.setMas(mes);
            String nickName="";
            for(User user1:list)if(uuuu.equals(user1.getUserName())) nickName=user1.getNickName();
            contact.setSenderId(uuuu);
            contact.setReceiverId(nickName);
            contactList.add(contact);
        }
        adapter = new UserListItemAdapter(view.getContext(),R.layout.item_contactuserlist, contactList);
        sshowListView.setAdapter(adapter);
        //设置点击单个Item事件
        sshowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contactList.get(position);
                //打开私聊页面
                String userName = contact.getSenderId();
                if(userName.equals("admin")) userName=contact.getReceiverId();
                Intent intent=new Intent(view.getContext(), ContactOneUserActivity.class);
                intent.putExtra("userName",userName);
                String nickname="";
                if(list!=null){
                    for(User user:list){
                        if(user.getUserName().equals(userName)) nickname=user.getNickName();
                    }
                }
                intent.putExtra("nickName",nickname);
                startActivity(intent);
            }
        });
    }

}