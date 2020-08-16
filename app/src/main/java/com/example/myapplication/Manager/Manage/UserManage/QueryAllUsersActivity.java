package com.example.myapplication.Manager.Manage.UserManage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.dao.DepDao;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.User;
import com.example.myapplication.utils.BaseUrl;
import com.example.myapplication.utils.MD5Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QueryAllUsersActivity extends AppCompatActivity {

    private static final String TAG = "QueryAllUsersActivity";
    private EditText et_user_query_username;
    private ListView lv_user_query_listview;
    private UserDao userDao;
    private DepDao depDao;
    private List<User> data;
    private MyAdapter adapter;

    OkHttpClient client = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(QueryAllUsersActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    data.remove((int)msg.obj);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(QueryAllUsersActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    adapter.notifyDataSetChanged();
                    et_user_query_username.setText("");
                    Toast.makeText(QueryAllUsersActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_query_all_users);
        bindUI();
        getDataGetByOKHttpUtils();
        //userDao = new UserDao(this);
        //depDao = new DepDao(this);
        //data = userDao.findAll();
        //adapter = new MyAdapter();
        //lv_user_query_listview.setAdapter(adapter);

        lv_user_query_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(QueryAllUsersActivity.this)
                        .setTitle("删除用户")
                        .setMessage("您确定要删除该用户吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final User user = data.get(position);
                                //userDao.deleteByUserName(user.getUserName());

                                //userDao.update(user);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String url = BaseUrl.BASE_URL + "user/deleteByUserName.do";
                                        //String userJson = JSON.toJSONString(user);
                                        //RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userJson);
                                        RequestBody body = new FormBody.Builder()
                                                .add("userName", user.getUserName())
                                                .build();
                                        System.out.println(body);
                                        Request request = new Request.Builder()
                                                .url(url)
                                                .post(body)
                                                .build();
                                        Call call = client.newCall(request);
                                        call.enqueue(new Callback() {
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                // Log.d(TAG,"<<<<e="+e);
                                            }

                                            @Override
                                            public void onResponse(Call call, Response response) throws IOException {
                                                if(response.isSuccessful()) {
                                                    //String d = response.body().string();
                                                    //Log.d(TAG,"<<<<e="+e);
                                                    //user = JSON.parseObject(d, User.class);
                                                }
                                            }
                                        });
                                    }
                                }).start();
                                Message msg = Message.obtain();
                                msg.what = 2;
                                msg.obj = position;
                                handler.sendMessage(msg);
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
        final String userName = et_user_query_username.getText().toString();
        final User user = new User();
        if (!userName.isEmpty() && userName.length() != 0) user.setUserName("%" + userName + "%");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "user/findByLikeUserName.do";
                //Log.v("MyInfo", JSON.toJSONString(json));
                String userJson = JSON.toJSONString(user);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userJson);
                System.out.println(body);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //Log.d(getActivity(),"<<<<e="+e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()) {
                            String d = response.body().string();
                            //Log.d(getActivity(),"<<<<d=" + d);
                            data = JSON.parseArray(d, User.class);
                            //flag[0] = true;
                            Message msg = Message.obtain();
                            msg.what = 3;
                            handler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();
        //data = userDao.findByLikeUserName(userName);
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
            holder.dep_name.setText(user.getDep().getName());
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
                                    user.setPasswd(MD5Utils.md5(newPassWd));
                                    //userDao.update(user);

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String url = BaseUrl.BASE_URL + "user/update.do";
                                            String userJson = JSON.toJSONString(user);
                                            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userJson);
                                            System.out.println(body);
                                            Request request = new Request.Builder()
                                                    .url(url)
                                                    .post(body)
                                                    .build();
                                            Call call = client.newCall(request);
                                            call.enqueue(new Callback() {
                                                @Override
                                                public void onFailure(Call call, IOException e) {
                                                    // Log.d(TAG,"<<<<e="+e);
                                                }

                                                @Override
                                                public void onResponse(Call call, Response response) throws IOException {
                                                    if(response.isSuccessful()) {
                                                        //String d = response.body().string();
                                                        //Log.d(TAG,"<<<<e="+e);
                                                        //user = JSON.parseObject(d, User.class);
                                                    }
                                                }
                                            });
                                        }
                                    }).start();
                                    Message msg = Message.obtain();
                                    msg.what = 1;
                                    handler.sendMessage(msg);

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


    /**
     * get请求
     */
    public void getDataGetByOKHttpUtils() {
        String url = BaseUrl.BASE_URL + "user/findAll.do";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new QueryAllUsersActivity.MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {
            e.printStackTrace();
//            tv_result.setText();
//            Toast.makeText(RegisterActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.v(TAG, "onError:" + e.getMessage());
        }
        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
//            tv_result.setText();
//            Toast.makeText(RegisterActivity.this, "onResponse:" + response, Toast.LENGTH_SHORT).show();
            Log.v(TAG, "onResponse:" + response);
            switch (id) {
                case 100:
                    Toast.makeText(QueryAllUsersActivity.this, "http:数据请求成功",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(QueryAllUsersActivity.this, "https:数据请求成功",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
            System.out.println(response);
            if(response != null) {
                //解析数据
                parseData(response);
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
        }
    }

    private void parseData(String json) {
        data = JSON.parseArray(json, User.class);
        Log.v("MyInfo", data.toString());
        adapter = new MyAdapter();
        lv_user_query_listview.setAdapter(adapter);
    }
}