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
import com.example.myapplication.domain.Contact;

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

        GradientDrawable drawable = (GradientDrawable) getContext().getResources().getDrawable(R.drawable.shape_round_textview);
        if(contact.getSenderId().equals("admin")){
            v1.setText("管"); //发送人id
            GradientDrawable gradientDrawable = (GradientDrawable) v1.getBackground();
            gradientDrawable.setColor(Color.parseColor("#FF0033"));

        }
        else{
            v1.setText("我"); //发送人id
            GradientDrawable gradientDrawable = (GradientDrawable) v1.getBackground();
            gradientDrawable.setColor(Color.parseColor("#FFCC33"));
        }

        v2.setText(contact.getMas());   //发送的信息
        v3.setText(contact.getTimestamp()); //时间
        return view;
    }

}
