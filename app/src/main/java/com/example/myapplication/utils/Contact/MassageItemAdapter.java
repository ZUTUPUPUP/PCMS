package com.example.myapplication.utils.Contact;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.Contact;
import com.example.myapplication.domain.User;

import java.util.List;

//消息ListView的适配器
public class MassageItemAdapter extends ArrayAdapter {

    private final int resourceId;
    public MassageItemAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Contact> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = (Contact) getItem(position); // 获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        TextView v1 = (TextView) view.findViewById(R.id.tv1);//获取该布局内的文本视图
        TextView v2 =(TextView) view.findViewById(R.id.tv2);
        TextView v3 =(TextView) view.findViewById(R.id.tv3);

     //   GradientDrawable drawable = (GradientDrawable) getContext().getResources().getDrawable(R.drawable.shape_round_textview);
        if(contact.getSenderId().equals("admin")){
            v1.setText("管理"); //发送人id
            //设置背景色工具
            GradientDrawable gradientDrawable = (GradientDrawable) v1.getBackground();
            gradientDrawable.setColor(Color.parseColor("#FF6666"));

        }
        else{
            //得到姓名的后两位
            UserDao userDao=new UserDao(view.getContext());
            String uName=contact.getSenderId();
            if(uName.equals("admin"))uName=contact.getReceiverId();
            User user=userDao.findByUserName(uName);
            String realName;
            if(user==null) realName="-1";
            else  realName=userDao.findByUserName(uName).getNickName();
            realName=realName.substring(realName.length()-2);
            v1.setText(realName);
            //设置背景色
            GradientDrawable gradientDrawable = (GradientDrawable) v1.getBackground();
            gradientDrawable.setColor(Color.parseColor("#FFCC33"));
        }

        v2.setText(contact.getMas());   //发送的信息
        v3.setText(contact.getTimestamp()); //时间
        return view;
    }

}
