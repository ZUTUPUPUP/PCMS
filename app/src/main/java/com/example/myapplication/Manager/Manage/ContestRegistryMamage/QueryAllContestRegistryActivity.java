package com.example.myapplication.Manager.Manage.ContestRegistryMamage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.example.myapplication.dao.ContestRegistryDao;
import com.example.myapplication.domain.Contest;
import com.example.myapplication.domain.ContestRegistry;
import com.example.myapplication.domain.Dep;
import com.example.myapplication.domain.User;
import com.example.myapplication.utils.BaseUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QueryAllContestRegistryActivity extends AppCompatActivity {


    private static final String TAG = "QueryAllContestRegistryActivity";
    private EditText et_contestreg_query_contestname, et_contestreg_query_username, et_contestreg_query_depname, et_contestreg_query_class;
    private ListView lv_contestreg_query_listview;
    private ContestRegistryDao contestRegistryDao;
    private List<ContestRegistry> data;
    private Myadapter adapter;

    OkHttpClient client = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    data.remove((int)msg.obj);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(QueryAllContestRegistryActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    if (adapter == null) {
                        adapter = new Myadapter();
                        lv_contestreg_query_listview.setAdapter(adapter);
                    } else adapter.notifyDataSetChanged();
                    Toast.makeText(QueryAllContestRegistryActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                    break;
                case  3:
                    adapter.notifyDataSetChanged();
                    Toast.makeText(QueryAllContestRegistryActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_query_all_contest_registry);
        bindUI();
        //contestRegistryDao = new ContestRegistryDao(this);
        //data = contestRegistryDao.findAll();
       // adapter = new Myadapter();
        //lv_contestreg_query_listview.setAdapter(adapter);
        getDataGetByOKHttpUtils();
        lv_contestreg_query_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(QueryAllContestRegistryActivity.this)
                        .setTitle("删除报名信息")
                        .setMessage("您确定要删除该用户报名信息吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //ContestRegistryMessage contestRegistryMessage = data.get(position);
                                //contestRegistryDao.deleteById(contestRegistryMessage.get_id());
                                //data.remove(position);
                                //adapter.notifyDataSetChanged();
                                //Toast.makeText(QueryAllContestRegistryActivity.this, "删除成功", Toast.LENGTH_SHORT).show();

                                final ContestRegistry contestRegistry = data.get(position);

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String url = BaseUrl.BASE_URL + "contestRegistry/deleteById.do";
                                        String idJson = JSON.toJSONString(contestRegistry.get_id());
                                        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), idJson);
//                                        RequestBody body = new FormBody.Builder()
//                                                .add("userName", String.valueOf(contestRegistry.get_id()))
//                                                .build();
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
                                msg.obj = position;
                                handler.sendMessage(msg);
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


    private void bindUI() {
        lv_contestreg_query_listview = findViewById(R.id.lv_contestreg_query_listview);
        et_contestreg_query_contestname = findViewById(R.id.et_contestreg_query_contestname);
        et_contestreg_query_username = findViewById(R.id.et_contestreg_query_username);
        et_contestreg_query_depname = findViewById(R.id.et_contestreg_query_depname);
        et_contestreg_query_class = findViewById(R.id.et_contestreg_query_class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //data = contestRegistryDao.findAll();
        //getDataGetByOKHttpUtils();
        //adapter.notifyDataSetChanged();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "contestRegistry/findAll.do";
                //String contestRegistryJson = JSON.toJSONString(contestRegistry);
                //RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), contestRegistryJson);
                //System.out.println(body);
                Request request = new Request.Builder()
                        .url(url)
                        .get()
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
                            String d = response.body().string();
                            data = JSON.parseArray(d, ContestRegistry.class);
                            //Log.d(TAG,"<<<<e="+e);
                            //user = JSON.parseObject(d, User.class);
                            Message msg = Message.obtain();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();
    }
    //根据输入查询模糊相关报名信息
    public void findAllMessageByPrint(View view) {
        String contestName = et_contestreg_query_contestname.getText().toString().trim();
        String userName = et_contestreg_query_username.getText().toString().trim();
        String depName = et_contestreg_query_depname.getText().toString().trim();
        String Class = et_contestreg_query_class.getText().toString().trim();
        //data = contestRegistryDao.findLikeAllByPrint(contestName, userName, depName, Class);
        //adapter.notifyDataSetChanged();
        final ContestRegistry contestRegistry1 = new ContestRegistry();
        Contest contest = new Contest();
        if(!contestName.isEmpty() && contestName.length() > 0) contest.setContestName("%" + contestName + "%");
        User user = new User();
        if(!userName.isEmpty() && userName.length() > 0) user.setUserName("%" + userName + "%");
        Dep dep = new Dep();
        if(!depName.isEmpty() && depName.length() > 0) dep.setName("%" + depName + "%");
        user.setDep(dep);
        if(!Class.isEmpty() && Class.length() > 0) contestRegistry1.setClassAndGrade("%" + Class + "%");
        contestRegistry1.setContest(contest);
        contestRegistry1.setUser(user);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "contestRegistry/findLikeAllByPrint.do";
                //Log.v("MyInfo", JSON.toJSONString(json));
                String contestRegistry1Json = JSON.toJSONString(contestRegistry1);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), contestRegistry1Json);
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
                            data = JSON.parseArray(d, ContestRegistry.class);
                            //flag[0] = true;
                            Message msg = Message.obtain();
                            msg.what = 3;
                            handler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();
        et_contestreg_query_contestname.setText("");
        et_contestreg_query_username.setText("");
        et_contestreg_query_depname.setText("");
        et_contestreg_query_class.setText("");
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
            final ContestRegistry contestRegistry = data.get(position);
            holder.userName.setText(contestRegistry.getUser().getUserName());
            holder.contestName.setText(contestRegistry.getContest().getContestName());
            holder.name.setText(contestRegistry.getRelName());
            holder.classAndGrade.setText(contestRegistry.getClassAndGrade());
            holder.gender.setText(contestRegistry.getGender());
            holder.updateMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(QueryAllContestRegistryActivity.this, UpdateContestRegistryMessage.class);
                    intent.putExtra("contestRegistry", JSON.toJSONString(contestRegistry));
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


    /**
     * get请求
     */
    public void getDataGetByOKHttpUtils() {
        String url = BaseUrl.BASE_URL + "contestRegistry/findAll.do";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new QueryAllContestRegistryActivity.MyStringCallback());
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
            //Log.v(TAG, "onError:" + e.getMessage());
        }
        @Override
        public void onResponse(String response, int id) {
            //Log.e(TAG, "onResponse：complete");
//            tv_result.setText();
//            Toast.makeText(RegisterActivity.this, "onResponse:" + response, Toast.LENGTH_SHORT).show();
            //Log.v(TAG, "onResponse:" + response);
            switch (id) {
                case 100:
                    Toast.makeText(QueryAllContestRegistryActivity.this, "http:数据请求成功",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(QueryAllContestRegistryActivity.this, "https:数据请求成功",
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
            //Log.e(TAG, "inProgress:" + progress);
        }
    }

    private void parseData(String json) {
        data = JSON.parseArray(json, ContestRegistry.class);
        Log.v("MyInfo", data.toString());
        adapter = new Myadapter();
        lv_contestreg_query_listview.setAdapter(adapter);
    }
}