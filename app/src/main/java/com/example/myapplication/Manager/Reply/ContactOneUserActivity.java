package com.example.myapplication.Manager.Reply;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContactDao;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.Contact;
import com.example.myapplication.utils.Contact.MassageItemAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

//用户界面下，用户联系管理员
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        setContentView(R.layout.activity_manager_contact);
        init();//初始化部件和参数
        getHistoryContact();//读取历史消息
        setBtnSendMes();//设置发送信息
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
        //开延迟，使得可以先显示底部最新消息
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        contactList=contactDao.findById(userName);
        //筛选出admin和user的对话
        adapter = new MassageItemAdapter(this,R.layout.item_contactmessage, contactList);
        showListView.setAdapter(adapter);
        showListView.setSelection(adapter.getCount()-1);

    }
    private void setBtnSendMes() {
        btn_sendmes = (Button)findViewById(R.id.btn_sendmas);
        ed_input = (EditText)findViewById(R.id.ed_input);
        btn_sendmes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact= new Contact(-1,null,"admin",userName,ed_input.getText().toString());
                Calendar calendar= Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String nowDate=(sdf.format(calendar.getTime()));
                contact.setTimestamp(nowDate);
                contactDao.insertOneContact(contact);
                //插入一个聊天记录后要刷新ListVeiw
                contactList.add(contact);
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


