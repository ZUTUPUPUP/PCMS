package com.example.myapplication.Manager.Manage.Contest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        Contest contest=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView ContestImage=(ImageView)view.findViewById(R.id.ContestImage);
        TextView ContestName=(TextView)view.findViewById(R.id.tv_item_query_contest_name);
        TextView ContestTime=(TextView)view.findViewById(R.id.tv_item_query_contest_time);
        TextView ContestNote=(TextView)view.findViewById(R.id.tv_item_query_contest_node);
        ContestImage.setImageResource(R.drawable.contest);
        ContestName.setText(contest.getContestName());
        ContestTime.setText(contest.getContestTime());
        ContestNote.setText(contest.getContestNote());
        return view;
    }
}
