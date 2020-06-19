package com.example.myapplication.User.Mine.Contact;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContactDao;
import com.example.myapplication.domain.Contact;
import com.example.myapplication.utils.Contact.MassageItemAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//用户界面下，用户联系管理员
public class UserContactActivity extends AppCompatActivity {

    private String userName;
    private ListView showListView;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contact);
        Intent getIntent = getIntent();
        userName = getIntent.getStringExtra("userName");  //System.out.println(userName);
        getHistoryContact();//读取历史消息
    }

    private void getHistoryContact() {
        scrollView = (ScrollView)findViewById(R.id.sv);
        //开延迟，使得可以先显示底部最新消息
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        showListView = (ListView) findViewById(R.id.show_listView);
        ContactDao contactDao=new ContactDao(this);
        List<Contact> list=contactDao.findAll();
        MassageItemAdapter adapter =new MassageItemAdapter(this,R.layout.masseage_item,list);
        showListView.setAdapter(adapter);
        showListView.setSelection(adapter.getCount()-1);
    }



    String UTCtoUTF8(String dateStr) { //utc转到utf8
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sf.parse(dateStr);
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date);
            cal1.add(Calendar.HOUR,8);
            Date date2=cal1.getTime();
            return date2.toString();

        } catch (Exception e) {
            return null;
        }
    }

}

