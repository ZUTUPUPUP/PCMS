package com.example.myapplication.utils.Contact;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContactDao;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.Contact;
import com.example.myapplication.domain.User;

import java.util.List;
//消息ListView的适配器
public class UserListItemAdapter extends ArrayAdapter {

    private final int resourceId;
    public UserListItemAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Contact> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = (Contact) getItem(position); // 获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        TextView name = (TextView) view.findViewById(R.id.name);//获取该布局内的文本视图
        TextView L1 =(TextView) view.findViewById(R.id.L1);
        TextView R1 =(TextView) view.findViewById(R.id.R1);
        TextView B =(TextView) view.findViewById(R.id.B);
        //设置头像上的名字
        UserDao userDao=new UserDao(view.getContext());
        String uName=contact.getSenderId();
        if(uName.equals("admin"))uName=contact.getReceiverId();
        User user=userDao.findByUserName(uName);
        System.out.println("aaaaaa"+user.getNickName());
        String realName;
        if(user==null) realName="-1";
        else  realName=userDao.findByUserName(uName).getNickName();
        realName=realName.substring(realName.length()-2);
        name.setText(realName);
        //配色表
        String color[]={"#FFCCCC","#FF6666","#FF9966","#CCCCFF","#66CCFF","#66CCCC","#FF99CC"};
        //设置颜色
        GradientDrawable drawable = (GradientDrawable) getContext().getResources().getDrawable(R.drawable.shape_round_textview);
        GradientDrawable gradientDrawable = (GradientDrawable) name.getBackground();
        gradientDrawable.setColor(Color.parseColor(color[position%7]));
        //设置学号
        L1.setText(uName);

        //设置时间
        String date=contact.getTimestamp();
        if(date=="")return  view;
        String qian=date.substring(0,date.length()-6);
        String hou=date.substring(date.length()-5);
        R1.setText(qian+'\n'+hou);

        //设置消息
        B.setText(contact.getMas());

        return view;
    }
    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int mlength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }
}
