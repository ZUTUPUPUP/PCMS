package com.example.myapplication.Manager.Manage.UserManage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.DepDao;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.User;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class QueryAllUsersActivity extends AppCompatActivity {

    private EditText et_user_query_username;
    private ListView lv_user_query_listview;
    private UserDao userDao;
    private DepDao depDao;
    private List<User> data;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_all_users);
        bindUI();
        userDao = new UserDao(this);
        depDao = new DepDao(this);
        data = userDao.findAll();
        adapter = new MyAdapter();
        lv_user_query_listview.setAdapter(adapter);

        lv_user_query_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(QueryAllUsersActivity.this)
                        .setTitle("删除用户")
                        .setMessage("您确定要删除该用户吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                User user = data.get(position);
                                userDao.deleteByUserName(user.getUserName());
                                data.remove(position);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(QueryAllUsersActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(QueryAllUsersActivity.this, "取消删除该用户", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });

    }
    //根据学号模糊查询用户信息,后创建的展示在上面
    public void UsersFindByUserName(View v) {
        String userName = et_user_query_username.getText().toString();
        data = userDao.findByLikeUserName(userName);
        adapter.notifyDataSetChanged();
    }
    public void delUserName(View v) {
        et_user_query_username.setText("");
    }

    private void bindUI() {
        et_user_query_username = findViewById(R.id.et_user_query_username);
        lv_user_query_listview = findViewById(R.id.lv_user_query_listview);
    }

    class MyAdapter extends BaseAdapter {

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
                convertView = View.inflate(QueryAllUsersActivity.this, R.layout.item_listview_user, null);
                holder.username = convertView.findViewById(R.id.tv_item_query_username);
                holder.nickname = convertView.findViewById(R.id.tv_item_query_nickname);
                holder.dep_name = convertView.findViewById(R.id.tv_item_query_dep);
                holder.update_passwd = convertView.findViewById(R.id.tv_item_query_update_passwd);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final User user = data.get(position);
            holder.username.setText(user.getUserName());
            holder.nickname.setText(user.getNickName());
            holder.dep_name.setText(depDao.findById(user.getDepartment_id()).getName());
            holder.update_passwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String passwd = user.getPasswd();
                    final EditText editText = new EditText(QueryAllUsersActivity.this);
                    editText.setHint("加密后：" + passwd);
                    editText.setSingleLine(true);
                    editText.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                    //显示AlertDialog
                    new AlertDialog.Builder(QueryAllUsersActivity.this)
                            .setTitle("修改密码")
                            .setView(editText)
                            .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String newPassWd = editText.getText().toString();
                                    user.setPasswd(newPassWd);
                                    userDao.update(user);
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
            });
            return convertView;
        }
    }
    class ViewHolder { //视图的容器
        public TextView username;
        private TextView nickname;
        private TextView dep_name;
        private Button update_passwd;
    }
}