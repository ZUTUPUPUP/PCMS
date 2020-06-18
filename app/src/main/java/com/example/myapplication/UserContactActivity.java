package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.dao.ContactDao;
import com.example.myapplication.domain.Contact;
import com.example.myapplication.utils.ContactDBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//用户界面下，用户联系管理员
public class UserContactActivity extends AppCompatActivity {

    private String userName;
    private TextView viewShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contact);
        Intent getIntent = getIntent();
        userName = getIntent.getStringExtra("userName");
        viewShow = (TextView) findViewById(R.id.show);
        System.out.println(userName);
        viewShow.setText(userName);
        getHistoryContact();//读取历史消息
    }

    private void getHistoryContact() {
        ContactDao contactDao=new ContactDao(this);
        List<Contact> list=contactDao.findAll();
        for(Contact contact:list){
            viewShow.append('\n'+contact.toString());
            System.out.println(contact);
        }


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

