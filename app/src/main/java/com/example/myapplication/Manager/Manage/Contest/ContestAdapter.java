package com.example.myapplication.Manager.Manage.Contest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.domain.Contest;

import java.util.List;

public class ContestAdapter extends ArrayAdapter<Contest> {
    private int resourceId;
    public ContestAdapter(@NonNull Context context, int resource, @NonNull List<Contest> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final ContestHui hui;
        final Contest contest=getItem(position);
            hui=new ContestHui();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview_contest,null);
            hui.contestImage = convertView.findViewById(R.id.ContestImage);
            hui.contestName = (TextView) convertView.findViewById(R.id.tv_item_query_contest_name);
            hui.contestTime = (TextView) convertView.findViewById(R.id.tv_item_query_contest_time);
            hui.contestNote = (TextView) convertView.findViewById(R.id.tv_item_query_contest_node);
            hui.contestMore=convertView.findViewById(R.id.contestMore);
        hui.contestImage.setImageResource(R.drawable.contest);
        hui.contestName.setText(contest.getContestName()+"(id:"+contest.getContestId()+")");
        hui.contestTime.setText(contest.getContestTime());
        hui.contestNote.setText(contest.getContestNote());
        hui.contestMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ModifyContestActivity.class);
                intent.putExtra("id",String.valueOf(contest.getContestId()));
                intent.putExtra("name",contest.getContestName());
                intent.putExtra("introduction",contest.getContestIntroduction());
                intent.putExtra("time",contest.getContestTime());
                intent.putExtra("note",contest.getContestNote());
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }
    class ContestHui{
        ImageView contestImage,contestMore;
        TextView contestName,contestTime,contestNote;
    }
}
