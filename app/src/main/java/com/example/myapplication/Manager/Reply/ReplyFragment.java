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
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContactDao;
import com.example.myapplication.domain.Contact;
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


    public ReplyFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reply, container, false);
       // System.out.println(new String("ypy踩踩").length());
        initUI();
        getUserList();//读取用户列表

        return view;
    }

    private void getUserList() {

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