package com.example.myapplication.Manager.Manage.Awards;

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

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.dao.AwardsDao;
import com.example.myapplication.domain.AwardsInfo;
import com.example.myapplication.utils.BaseUrl;

import java.io.IOException;
import java.util.List;

import androidx.appcompat.app.AlertDialog;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AwardsAdapter extends BaseAdapter {
    private Context context;
    private List<AwardsInfo> list;
    private static final String TAG = "AwardsAdapter";
    //private AwardsDao awardsDao;
    //适配器
    public AwardsAdapter(Context context,List<AwardsInfo> list){
        this.context = context;
        this.list = list;
    }
    //更新集合用
    public void setList(List<AwardsInfo> list) {
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
        final HUi hui;
        final AwardsInfo java = list.get(position);
        if(convertView == null){
            hui = new HUi();
            final int wo = position;
            //awardsDao = new AwardsDao(context);
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_awards, null);
            hui.relName = convertView.findViewById(R.id.tv_item_query_award_relName);
            hui.award = convertView.findViewById(R.id.tv_item_query_award_award);
            hui.STNumber = convertView.findViewById(R.id.tv_item_query_award_STNumber);
            hui.contestName = convertView.findViewById(R.id.tv_item_query_award_contestName);
            hui.className = convertView.findViewById(R.id.tv_item_query_award_calssName);
            hui.depName = convertView.findViewById(R.id.tv_item_query_award_depName);
            hui.update = convertView.findViewById(R.id.tv_item_query_award_update_message);
            hui.delete = convertView.findViewById(R.id.tv_item_query_award_delete_message);
            convertView.setTag(hui);
        }else{
            hui = (HUi) convertView.getTag();
        }
        final AwardsInfo awardsInfo = list.get(position);
        hui.relName.setText(awardsInfo.getRelName());
        hui.award.setText(awardsInfo.getAwardLevel());
        hui.STNumber.setText(awardsInfo.getSTNumber());
        hui.contestName.setText(awardsInfo.getContestName());
        hui.contestName.setText(awardsInfo.getContestName());
        hui.className.setText(awardsInfo.getClassName());
        hui.depName.setText(awardsInfo.getDepName());
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
                        //awardsDao.deleteByUserId(awardsInfo.get_id());
                        try {
                            delete(awardsInfo);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        //list = awardsDao.findAll();
                        //setList(list);
                        Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        hui.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AwardsUpdateActivity.class);
                intent.putExtra("ID", awardsInfo.get_id() + "");
                Log.v("MyInfo", awardsInfo.get_id() + "");
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class HUi {
        TextView relName, award, STNumber, contestName, className, depName;
        Button update, delete;
    }

    public void delete(final AwardsInfo awardsInfo) throws IOException, InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "awards/delete.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("id", awardsInfo.get_id() + "")
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
                            Log.d(TAG, awardsInfo.getRelName() + "已删");
                        }
                    }
                });
            }
        });
        thread.start();
        thread.join();
        while(thread.isAlive()) continue;
        if(!thread.isAlive()){
            findAll();
        }
    }
    public void findAll() throws IOException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "awards/findAll.do";
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
                            list = JSON.parseArray(d, AwardsInfo.class);
                        }
                    }
                });
            }
        });
        thread1.start();
        while(thread1.isAlive()) continue;
        if(!thread1.isAlive()){
            setList(list);
        }
    }
}
