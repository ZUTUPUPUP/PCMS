package com.example.myapplication.Manager.Manage.Notice;

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
import com.example.myapplication.R;
import com.example.myapplication.dao.NoticeDao;
import com.example.myapplication.domain.Notice;

import java.util.List;

public class NoticeAdapter extends BaseAdapter {

    private Context context;
    private List<Notice> list;
    private NoticeDao noticeDao;
    //适配器
    public NoticeAdapter(Context context,List<Notice> list){
        this.context = context;
        this.list = list;
    }
    //更新集合用
    public void setList(List<Notice> list) {
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
        final NoticeAdapter.HUi hui;
        final Notice java = list.get(position);
        if(convertView == null){
            hui = new NoticeAdapter.HUi();
            final int wo = position;
            noticeDao = new NoticeDao(context);
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_notice, null);
            hui.title = convertView.findViewById(R.id.tv_notice_title);
            hui.content = convertView.findViewById(R.id.tv_notice_content);
            hui.time = convertView.findViewById(R.id.tv_notice_time);
            hui.send = convertView.findViewById(R.id.tv_notice_edit);
            hui.delete = convertView.findViewById(R.id.tv_notice_delete);
            convertView.setTag(hui);
        }else{
            hui = (NoticeAdapter.HUi) convertView.getTag();
        }
        final Notice notice = list.get(position);
        hui.title.setText(notice.getTitle());
        hui.content.setText(notice.getContent());
        hui.time.setText(notice.getTime());

        //构建删除对话框
        hui.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new  AlertDialog.Builder(context);
                builder.setMessage("确定删除吗？");
                builder.setPositiveButton("取消",null);
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        noticeDao.deleteById(notice.get_id());
                        list = noticeDao.findAll();
                        setList(list);
                        notifyDataSetChanged();
                        Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        hui.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoticeInsertActivity.class);
                intent.putExtra("ID", notice.get_id() + "");
                Log.v("MyInfo", notice.get_id() + "");
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class HUi {
        TextView title,content,time;
        Button send,delete;
    }
}
