package com.example.myapplication.Manager.Reply;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContactDao;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.Contact;
import com.example.myapplication.domain.User;
import com.example.myapplication.utils.Contact.UserListItemAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ReplyFragment extends Fragment {



    private ListView sshowListView;
    private ContactDao contactDao;
    private View view;
    private List<Contact> contactList;
    private UserListItemAdapter adapter;
    private List<Contact> allContacts;
    private boolean longer;
    private ImageButton btn_reflash;
    private ImageButton btn_searchUser;
    private EditText input;


    public ReplyFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reply, container, false);
       // System.out.println(new String("ypy踩踩").length());
        initUI();
        getUserList();//读取用户列表
        btn_reflash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserList();
            }
        });
        return view;
    }

    public void getUserList() {

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
    }

    private void initUI() {
        btn_searchUser = (ImageButton)view.findViewById(R.id.btn_searchUser);
        btn_reflash = (ImageButton)view.findViewById(R.id.btn_reflash);

        input = (EditText)view.findViewById(R.id.ed_input);
        final UserDao userDao=new UserDao(getContext());
        btn_searchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=input.getText().toString();
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
                sshowListView.setAdapter(adapter);
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
}