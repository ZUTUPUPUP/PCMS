package com.example.myapplication.User.Mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.Login_Register.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.User.Mine.Contact.UserContactActivity;
import com.example.myapplication.dao.DepDao;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.User;
import com.example.myapplication.utils.BaseUrl;
import com.example.myapplication.utils.MD5Utils;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MineFragment extends Fragment {

    private View view;
    private Button btn_contact;
    private String userName;
    private UserDao userDao;
    private DepDao depDao;
    private Button btn_mine_update_msg;
    private TextView tv_mine_nickname, tv_mine_username, tv_mine_dep, tv_mine_gender;
    User user = null;
    private boolean flagMineUser;
    OkHttpClient client = new OkHttpClient();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public MineFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        bindUI();
        Intent MainIntent=getActivity().getIntent();//得到main里传进来的intent
        String json = MainIntent.getStringExtra("user");
        user = JSON.parseObject(json, User.class);
        userName = user.getUserName();
        initContact();
        //userDao = new UserDao(getActivity());
        //depDao = new DepDao(getActivity());
        //user = userDao.findByUserName(userName);
        System.out.println(user);
        //Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
        //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
        //用户个人信息展示
        showUserMessage();
        //点击事件修改密码
        updateUserPassWd();
        //点击事件修改昵称
        updateUserNickName();
        //点击事件报名信息查询
        findMineRegMessage();
        //我的获奖
        findMineAwardMessage();
        view.findViewById(R.id.btn_manage_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }

    //点击事件报名信息查询
    public void findMineRegMessage() {
        view.findViewById(R.id.findMineRegMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MineRegActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
    }

    //我的获奖点击事件
    public void findMineAwardMessage() {
        view.findViewById(R.id.findMineAwardMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MineAwardActivity.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
    }

    //点击事件修改昵称
    private void updateUserNickName() {
        tv_mine_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(getActivity());
                editText.setHint(user.getNickName());
                editText.setSingleLine(true);
                editText.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                //显示AlertDialog
                new AlertDialog.Builder(getActivity())
                        .setTitle("修改姓名")
                        .setView(editText)
                        .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newNickName = editText.getText().toString();
                                tv_mine_nickname.setText(newNickName);
                                user.setNickName(newNickName);
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
                                msg.what = 2;
                                handler.sendMessage(msg);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
    }

    private void bindUI() {
        tv_mine_nickname = view.findViewById(R.id.tv_mine_nickname);
        tv_mine_username = view.findViewById(R.id.tv_mine_username);
        tv_mine_dep = view.findViewById(R.id.tv_mine_dep);
        tv_mine_gender = view.findViewById(R.id.tv_mine_gender);
        btn_mine_update_msg = view.findViewById(R.id.btn_mine_update_msg);
    }

    //用户信息的展示
    private void showUserMessage() {
        tv_mine_username.setText("学号：" + user.getUserName());
        tv_mine_nickname.setText(user.getNickName());
        tv_mine_dep.setText(user.getDep().getName());
        tv_mine_gender.setText(user.getGender());
    }

    //用户密码的修改
    public void updateUserPassWd() {
        btn_mine_update_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String passwd = user.getPasswd();
                final EditText editText = new EditText(getActivity());
                editText.setHint("加密后：" + passwd);
                editText.setSingleLine(true);
                editText.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                //显示AlertDialog
                new AlertDialog.Builder(getActivity())
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
                                msg.what = 2;
                                handler.sendMessage(msg);

                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
    }

    private void initContact() {
        System.out.print(userName);
        btn_contact = (Button)view.findViewById(R.id.contact);
        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), UserContactActivity.class);
                intent.putExtra("userName", userName);//传到联系管理员的界面
                startActivity(intent);
            }
        });

    }
}