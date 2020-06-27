package com.example.myapplication.User.Message;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.Manager.Manage.Notice.NoticeAdapter;
import com.example.myapplication.Manager.Manage.Notice.NoticeSendActivity;
import com.example.myapplication.R;
import com.example.myapplication.dao.MessageDao;
import com.example.myapplication.dao.NoticeDao;
import com.example.myapplication.domain.Message;
import com.example.myapplication.domain.Notice;

import java.util.List;

public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<Message> list;
    private MessageDao messageDao;
    //适配器
    public MessageAdapter(Context context,List<Message> list){
        this.context = context;
        this.list = list;
    }
    //更新集合用
    public void setList(List<Message> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MessageAdapter.HUi hui;
        if(convertView == null){
            hui = new MessageAdapter.HUi();
            final int wo = position;
            messageDao = new MessageDao(context);
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_message, null);
            hui.title = convertView.findViewById(R.id.tv_message_title);
            hui.content = convertView.findViewById(R.id.tv_message_content);
            hui.time = convertView.findViewById(R.id.tv_message_time);
            convertView.setTag(hui);
        }else{
            hui = (MessageAdapter.HUi) convertView.getTag();
        }
        final Message message = list.get(position);
        hui.title.setText(message.getTitle());
        hui.content.setText(message.getContent());
        hui.time.setText(message.getTime());
        return convertView;
    }

    class HUi {
        TextView title,content,time;
    }
}
