package com.example.myapplication.Manager.Manage.ContestRegistryMamage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContestRegistryDao;
import com.example.myapplication.domain.ContestRegistryMessage;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class QueryAllContestRegistryActivity extends AppCompatActivity {

    private ListView lv_contestreg_query_listview;
    private ContestRegistryDao contestRegistryDao;
    private List<ContestRegistryMessage> data;
    private Myadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_all_contest_registry);
        lv_contestreg_query_listview = findViewById(R.id.lv_contestreg_query_listview);
        contestRegistryDao = new ContestRegistryDao(this);
        data = contestRegistryDao.findAll();
        adapter = new Myadapter();
        lv_contestreg_query_listview.setAdapter(adapter);
        lv_contestreg_query_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(QueryAllContestRegistryActivity.this)
                        .setTitle("删除报名信息")
                        .setMessage("您确定要删除该用户报名信息吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ContestRegistryMessage contestRegistryMessage = data.get(position);
                                contestRegistryDao.deleteById(contestRegistryMessage.get_id());
                                data.remove(position);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(QueryAllContestRegistryActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(QueryAllContestRegistryActivity.this, "取消删除该报名信息", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        data = contestRegistryDao.findAll();
        adapter.notifyDataSetChanged();
    }

    class Myadapter extends BaseAdapter {

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
                convertView = View.inflate(QueryAllContestRegistryActivity.this, R.layout.item_listview_contestregistry, null);
                holder.userName = convertView.findViewById(R.id.tv_item_query_contestreg_username);
                holder.contestName = convertView.findViewById(R.id.tv_item_query_contestreg_contestname);
                holder.name = convertView.findViewById(R.id.tv_item_query_contestreg_name);
                holder.classAndGrade = convertView.findViewById(R.id.tv_item_query_contestreg_class);
                holder.gender = convertView.findViewById(R.id.tv_item_query_contestreg_gender);
                holder.updateMessage = convertView.findViewById(R.id.tv_item_query_contestreg_update_message);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final ContestRegistryMessage contestRegistryMessage = data.get(position);
            holder.userName.setText(contestRegistryMessage.getUserName());
            holder.contestName.setText(contestRegistryMessage.getContestName());
            holder.name.setText(contestRegistryMessage.getRelName());
            holder.classAndGrade.setText(contestRegistryMessage.getClassAndGrade());
            holder.gender.setText(contestRegistryMessage.getGender());
            holder.updateMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(QueryAllContestRegistryActivity.this, UpdateContestRegistryMessage.class);
                    intent.putExtra("ID", contestRegistryMessage.get_id() + "");
                    QueryAllContestRegistryActivity.this.startActivity(intent);
                }
            });
            return convertView;
        }
    }
    class ViewHolder { //视图的容器
        TextView userName;
        TextView contestName;
        TextView name;
        TextView classAndGrade;
        TextView gender;
        Button updateMessage;
    }
}