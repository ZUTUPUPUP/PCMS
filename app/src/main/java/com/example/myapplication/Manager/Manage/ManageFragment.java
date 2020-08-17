package com.example.myapplication.Manager.Manage;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.Login_Register.MainActivity;
import com.example.myapplication.Manager.Manage.Awards.AwardsActivity;
import com.example.myapplication.Manager.Manage.Contest.ManagerContestActivity;
import com.example.myapplication.Manager.Manage.ContestRegistryMamage.QueryAllContestRegistryActivity;
import com.example.myapplication.Manager.Manage.Notice.NoticeActivity;
import com.example.myapplication.Manager.Manage.UserManage.QueryAllUsersActivity;
import com.example.myapplication.R;
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


public class ManageFragment extends Fragment {


    OkHttpClient client = new OkHttpClient();

    private View view;
    private TextView tv_manage_nickname, tv_manage_username, tv_manage_dep, tv_manage_gender;
    private Button btn_manage_update_msg;
    private String userName;
    private UserDao userDao;
    private DepDao depDao;
    private Button btn_mine_update_msg;
    private TextView tv_mine_nickname, tv_mine_username, tv_mine_dep, tv_mine_gender;
    private ImageView iv_manage_user, iv_manage_contest, iv_manage_register, iv_manage_awards, iv_manage_notice;


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

    User user = null;
    public ManageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manage, container, false);
        Intent MainIntent = getActivity().getIntent();//得到main里传进来的intent
        String json = MainIntent.getStringExtra("user");
        user = JSON.parseObject(json, User.class);
        bindUI();
        //userDao = new UserDao(getActivity());
        //depDao = new DepDao(getActivity());
        //user = userDao.findByUserName(userName);
//        final boolean[] flag = {false};
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String url = BaseUrl.BASE_URL + "user/findByUserName.do";
//                //Log.v("MyInfo", JSON.toJSONString(json));
//                RequestBody body = new FormBody.Builder()
//                        .add("userName", userName)
//                        .build();
//                System.out.println(body);
//                Request request = new Request.Builder()
//                        .url(url)
//                        .post(body)
//                        .build();
//                Call call = client.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        //Log.d(getActivity(),"<<<<e="+e);
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        if(response.isSuccessful()) {
//                            String d = response.body().string();
//                            //Log.d(getActivity(),"<<<<d=" + d);
//                            user = JSON.parseObject(d, User.class);
//                            flag[0] = true;
//                        }
//                    }
//                });
//            }
//        }).start();
//        while (!flag[0]) continue;
        Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
        //管理员信息展示
        showManageMessage();
        //点击事件修改密码
        updateManagePassWd();
        //点击事件修改昵称
        updateManageNickName();
        //点击事件监听进行系统的管理
        clickManageAll();

//        rg_tab = (RadioGroup) view.findViewById(R.id.rg_tab_bar_manage);
//        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rb_manage_contest:
//                        //controller.showFragment(0);
//                        Intent intent0=new Intent();
//                        intent0.setClass(getActivity(), ManagerContestActivity.class);
//                        startActivity(intent0);
//                        break;
//                    case R.id.rb_manage_awards:
//                        Intent intent1=new Intent();
//                        intent1.setClass(getActivity(), AwardsActivity.class);
//                        startActivity(intent1);
//                        break;
//                    case R.id.rb_manage_user:
//                        startActivity(new Intent(getActivity(), QueryAllUsersActivity.class));
//                        break;
//                    case R.id.rb_manage_sign:
//                        startActivity(new Intent(getActivity(), QueryAllContestRegistryActivity.class));
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
        return view;
    }

    private void clickManageAll() {
        //用户管理
        iv_manage_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), QueryAllUsersActivity.class));
            }
        });
        //竞赛管理
        iv_manage_contest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent0=new Intent();
                intent0.setClass(getActivity(), ManagerContestActivity.class);
                startActivity(intent0);
            }
        });
        //报名管理
        iv_manage_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), QueryAllContestRegistryActivity.class));
            }
        });
        //奖项管理
        iv_manage_awards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AwardsActivity.class));
            }
        });
        iv_manage_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NoticeActivity.class));
            }
        });
        //通知管理
        view.findViewById(R.id.btn_manage_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
    }

    private void updateManageNickName() {
        tv_manage_nickname.setOnClickListener(new View.OnClickListener() {
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
                                tv_manage_nickname.setText(newNickName);
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

    private void updateManagePassWd() {

        btn_manage_update_msg.setOnClickListener(new View.OnClickListener() {
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

    private void showManageMessage() {
        tv_manage_nickname.setText(user.getNickName());
        tv_manage_username.setText("账号：" + user.getUserName());
        tv_manage_dep.setText(user.getDep().getName());
        tv_manage_gender.setText(user.getGender());
    }

    private void bindUI() {
        tv_manage_nickname = view.findViewById(R.id.tv_manage_nickname);
        tv_manage_username = view.findViewById(R.id.tv_manage_username);
        tv_manage_dep = view.findViewById(R.id.tv_manage_dep);
        tv_manage_gender = view.findViewById(R.id.tv_manage_gender);
        btn_manage_update_msg = view.findViewById(R.id.btn_manage_update_msg);
        iv_manage_user = view.findViewById(R.id.iv_manage_user);
        iv_manage_contest = view.findViewById(R.id.iv_manage_contest);
        iv_manage_register = view.findViewById(R.id.iv_manage_register);
        iv_manage_awards = view.findViewById(R.id.iv_manage_awards);
        iv_manage_notice = view.findViewById(R.id.iv_manage_notice);
    }
}