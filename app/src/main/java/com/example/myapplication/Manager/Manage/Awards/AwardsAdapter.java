package com.example.myapplication.Manager.Manage.Awards;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
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
import com.example.myapplication.dao.AwardsDao;
import com.example.myapplication.domain.AwardsInfo;

import java.util.List;


public class AwardsAdapter extends BaseAdapter {
    private Context context;
    private List<AwardsInfo> list;
    private AwardsDao awardsDao;
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
            final int wo =position;
            awardsDao = new AwardsDao(context);
            convertView = LayoutInflater.from(context).inflate(R.layout.awards_item, null);
            hui.name = (TextView) convertView.findViewById(R.id.tv_name);
            hui.college = (TextView) convertView.findViewById(R.id.tv_college);
            hui.level = (TextView) convertView.findViewById(R.id.tv_level);
            hui.type = (TextView) convertView.findViewById(R.id.tv_type);
            hui.update = (Button) convertView.findViewById(R.id.but_name);
            hui.delete = (Button) convertView.findViewById(R.id.but_delete);

            //
            //修改按钮
            hui.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,AwardsUpdateActivity.class);
                    int on = (int) hui.update.getTag();
                    AwardsInfo awardsInfo = list.get(on);
                    intent.putExtra("id",awardsInfo.getUserId());
                    context.startActivity(intent);

                }
            });
            convertView.setTag(hui);
        }else{
            hui = (HUi) convertView.getTag();
        }

        hui.update.setTag(position);
        hui.name.setText(java.getUserName());
        hui.college.setText(java.getCollege());
        hui.type.setText(java.getCompetitionType());
        hui.level.setText(java.getAwardLevel());
        final String userId = java.getUserId();
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
                        awardsDao.deleteByUserId(userId);
                        list = awardsDao.findAll();
                        setList(list);
                        notifyDataSetChanged();
                        Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                    }
                }).show();

            }
        });
        return convertView;
    }

    class HUi {
        TextView id,name,college,type,level;
        Button update,delete;
    }
}
