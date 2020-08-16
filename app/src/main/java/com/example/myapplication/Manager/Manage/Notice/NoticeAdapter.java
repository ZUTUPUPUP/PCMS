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

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.dao.NoticeDao;
import com.example.myapplication.domain.AwardsInfo;
import com.example.myapplication.domain.Notice;
import com.example.myapplication.utils.BaseUrl;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NoticeAdapter extends BaseAdapter {
    private static final String TAG = "NoticeAdapter";
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
            hui.receiver = convertView.findViewById(R.id.tv_notice_receiver);
            hui.send = convertView.findViewById(R.id.tv_notice_edit);
            hui.delete = convertView.findViewById(R.id.tv_notice_delete);
            convertView.setTag(hui);
        }else{
            hui = (NoticeAdapter.HUi) convertView.getTag();
        }
        final Notice notice = list.get(position);
        hui.title.setText(notice.getTitle());
        hui.content.setText(notice.getContent());
        hui.receiver.setText(notice.getReceiver());

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
                        /*noticeDao.deleteById(notice.get_id());
                        list = noticeDao.findAll();
                        setList(list);*/
                        try {
                            delete(notice);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        hui.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoticeSendActivity.class);
                intent.putExtra("ID", notice.get_id() + "");
                Log.v("MyInfo", notice.get_id() + "");
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class HUi {
        TextView title,content,receiver;
        Button send,delete;
    }

    public void delete(final Notice notice) throws IOException, InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "notice/delete.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("id", notice.get_id() + "")
                        .build();
                //System.out.println(body);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "<<<<e=" + e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        } else {
                            Log.d(TAG, notice.getTitle() + "已删");
                        }
                    }
                });
            }
        });
        thread.start();
        thread.join();
        while(thread.isAlive())continue;
        if(!thread.isAlive())findAll();
    }
    public void findAll() throws IOException, InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                String url = BaseUrl.BASE_URL + "notice/findAll.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .build();
                //System.out.println(body);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "<<<<e=" + e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String d = response.body().string();
                            Log.d(TAG, "<<<<d=" + d);
                            list = JSON.parseArray(d, Notice.class);
                        }
                    }
                });
            }
        });
        thread1.start();
        thread1.join();
        while(thread1.isAlive()&&list==null)continue;
        if(!thread1.isAlive())setList(list);
    }
}
