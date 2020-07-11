package com.example.myapplication.Login_Register;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.domain.Dep;

import java.util.List;

public class RegisterAdapter extends BaseAdapter {
    private final Context context;
    private final List<Dep> data;

    public RegisterAdapter(Context context, List<Dep> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_reg_dep, null);
            holder.dep_name = convertView.findViewById(R.id.tv_reg_dep_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Dep dep = data.get(position);
        holder.dep_name.setText(dep.getName());
        return convertView;
    }

    class ViewHolder { //视图的容器
        public TextView dep_name;
    }
}
