package com.example.myapplication.utils.Contact;

import android.content.Context;
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
        TextView vid = (TextView) view.findViewById(R.id.id);//获取该布局内的文本视图
        TextView vmas =(TextView) view.findViewById(R.id.mas);
        TextView vdate =(TextView) view.findViewById(R.id.date);
        vid.setText(contact.getSenderId()); //发送人id
        vmas.setText(contact.getMas());   //发送的信息
        vdate.setText(contact.getTimestamp()); //时间
        return view;
    }

}
