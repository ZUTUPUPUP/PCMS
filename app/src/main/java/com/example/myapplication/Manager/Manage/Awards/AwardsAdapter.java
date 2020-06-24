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

import com.example.myapplication.R;
import com.example.myapplication.dao.AwardsDao;
import com.example.myapplication.domain.AwardsInfo;

import java.util.List;

import androidx.appcompat.app.AlertDialog;


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
            final int wo = position;
            awardsDao = new AwardsDao(context);
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
                        awardsDao.deleteByUserId(awardsInfo.get_id());
                        list = awardsDao.findAll();
                        setList(list);
                        notifyDataSetChanged();
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
}
