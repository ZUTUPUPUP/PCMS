package com.example.myapplication.utils.News;

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
import com.example.myapplication.domain.News;
import com.example.myapplication.domain.User;

import java.util.List;

public class NewsListItemAdapter extends ArrayAdapter {
    private final int resourceId;
    public NewsListItemAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<News> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = (News) getItem(position); // 获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        TextView t1=(TextView)view.findViewById(R.id.date);
        TextView t2=(TextView)view.findViewById(R.id.head);
        TextView t3=(TextView)view.findViewById(R.id.text);
        String color[]={"#FFCCCC","#FF6666","#FF9966","#CCCCFF","#66CCFF","#66CCCC","#FF99CC"};
        t2.setBackgroundColor(Color.parseColor(color[position%7]));
        t1.setText(news.getDate());
        t2.setText(news.getHead());
        t3.setText(news.getBrief());

        return view;
    }
}
