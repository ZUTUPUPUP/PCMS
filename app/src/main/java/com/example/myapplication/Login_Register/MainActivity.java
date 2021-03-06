package com.example.myapplication.Login_Register;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.Manager.ManagerActivity;
import com.example.myapplication.R;
import com.example.myapplication.User.UserActivity;
import com.example.myapplication.domain.Message;
import com.example.myapplication.domain.User;
import com.example.myapplication.utils.BaseUrl;
import com.example.myapplication.utils.MD5Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import android.os.Message;


/**
 *一个activity理解成是一个页面
 */
public class MainActivity extends AppCompatActivity {


    private EditText et_user_name, et_passWd;//编辑框
    Boolean flag = false;
    //private UserDao dao;
    private User user;
    private Context context;
    private List<Message> messages;
    private static final int PERMISSION_REQUEST = 1001; //申请权限
    //申请权限
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET}; //android.permission.INTERNET
    //申请权限
    List<String> permissionsList = new ArrayList<>();

    private static final String TAG = "MainActivity";
    OkHttpClient client = new OkHttpClient();
    private static final int GET = 1;
    private static final int POST = 2;
    private static final int SEND = 3;
    String userName = null;
    String passWd = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET:
                    Toast.makeText(MainActivity.this, "get数据请求成功", Toast.LENGTH_SHORT).show();
                    break;
                case POST:
                    Toast.makeText(MainActivity.this, "post数据请求成功", Toast.LENGTH_SHORT).show();
                    break;
                case SEND:
                    for(int i=0;i<messages.size();i++){
                        Message message = messages.get(i);
                        SendMsg(message);
                    }
                    break;
                case 4:
                    if (user != null && MD5Utils.md5(passWd).equals(user.getPasswd())) {
                        Intent intent = null;
                        if (user.getStatus().get_id() == 1) {
                            String json = JSON.toJSONString(user);
                            intent = new Intent(MainActivity.this, ManagerActivity.class);
                            intent.putExtra("user", json);
                        } else if (user.getStatus().get_id() == 2) {
                            String json = JSON.toJSONString(user);
                            intent = new Intent(MainActivity.this, UserActivity.class);
                            intent.putExtra("user", json);
                            findAllMessageByUserId(userName);
                        }
                        //Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "用户名或者密码错误,请重新输入!", Toast.LENGTH_SHORT).show();
                        et_user_name.setText("");
                        et_passWd.setText("");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        context = MainActivity.this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_main);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bindUI();
        initPermissions();//申请权限
        //dao = new UserDao(this);
    }
    /**
     * 请求权限
     */
    private void initPermissions() {
        permissionsList.clear();

        //判断哪些权限未授予
        for(String permission : permissions){
            if(ActivityCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
                permissionsList.add(permission);
            }
        }

        //请求权限
        if(!permissionsList.isEmpty()){
            String[] permissions = permissionsList.toArray(new String[permissionsList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST);
        }

    }

    /**
     * 权限回调,
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_REQUEST:
                Toast.makeText(MainActivity.this, "http:数据请求成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(MainActivity.this, "https:数据请求成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    //获取界面控件
    private void bindUI() {
        //从main_title_bar中获取的id
        //从activity_login.xml中获取的
        et_user_name = findViewById(R.id.et_user_name);
        et_passWd = findViewById(R.id.et_psw);
    }

    //登录点击事件
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void Login(View v) throws IOException {
        userName = et_user_name.getText().toString();
        passWd = et_passWd.getText().toString();
        //User user = dao.findByUserName(userName);
       // Log.v("MyInfo", user.toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "user/findByUserName.do";
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("userName", userName)
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
                        Log.d(TAG,"<<<<e="+e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()) {
                            String d = response.body().string();
                            Log.d(TAG,"<<<<d=" + d);
                            user = JSON.parseObject(d, User.class);
                            android.os.Message msg = android.os.Message.obtain();
                            msg.what = 4;
                            handler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();
    }


    //注册点击事件
    public void Register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, 1);//带结果的返回
    }

    //删除密码
    public void mainDelPassWd(View v) {
        et_passWd.setText("");
    }

    /**
     * 注册成功的数据返回至此
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 数据
     */
    @Override
    //显示数据， onActivityResult
    //startActivityForResult(intent, 1); 从注册界面中获取数据
    //int requestCode , int resultCode , Intent data
    // LoginActivity -> startActivityForResult -> onActivityResult();
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 2) {
            String userName = data.getStringExtra("userName");
            String passWd = data.getStringExtra("passWd");
            et_user_name.setText(userName);
            et_passWd.setText(passWd);
            //setSelection()方法来设置光标位置
            et_user_name.setSelection(userName.length());
        }
    }

    //管理员测试需删掉
    public void LoginOnAdmin(View view) {
        Intent intent=null;
        intent = new Intent(this, ManagerActivity.class);
        String json = "{\"_id\":1,\"userName\":\"admin\",\"passwd\":\"e15015a3b9df6b0da040e2e557a7150c\",\"nickName\":\"管理员\",\"gender\":\"女\",\"dep\":{\"_id\":1,\"name\":\"计算机学院\"},\"status\":{\"_id\":1,\"name\":\"管理员\"}}";
        intent.putExtra("user", json);
        startActivity(intent);
        this.finish();
    }
    //用户测试需删掉
    public void LoginOnUser(View view) {

        Intent intent = null;
        intent = new Intent(this, UserActivity.class);
        String json = "{\"_id\":5,\"userName\":\"user\",\"passwd\":\"e15015a3b9df6b0da040e2e557a7150c\",\"nickName\":\"用户\",\"gender\":\"女\",\"dep\":{\"_id\":1,\"name\":\"计算机学院\"},\"status\":{\"_id\":2,\"name\":\"普通用户\"}}";
        intent.putExtra("user", json);
        startActivity(intent);
        findAllMessageByUserId("user");
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void findAllMessageByUserId(final String userId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "message/findAllNoVis.do";
                OkHttpClient client = new OkHttpClient();
                //Log.v("MyInfo", JSON.toJSONString(json));
                RequestBody body = new FormBody.Builder()
                        .add("userId", userId)
                        .build();
                //System.out.println(body);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "<<<<e=" + e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }
                        else {
                            String d = response.body().string();
                            Log.d("d=-----",d);
                            messages = JSON.parseArray(d, Message.class);
                            android.os.Message msg = android.os.Message.obtain();
                            msg.what = SEND;
                            handler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();
    }

    //发送通知
    private void SendMsg(Message message){
        String title = message.getTitle();
        String content = message.getContent();
        String time = message.getTime();
        //1 获取系统通知管理员
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //2 获取通知构造者
        Notification.Builder builder = new Notification.Builder(context);
        //3 添加自定义通知视图
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_listview_message);
        //4 设置主要参数 给自定义布局的控件赋值
        remoteViews.setTextViewText(R.id.tv_message_title,title);
        remoteViews.setTextViewText(R.id.tv_message_content,content);
        remoteViews.setTextViewText(R.id.tv_message_time,time);
        builder.setContent(remoteViews);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true); //点击取消通知
        //关闭通知
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,new Intent(),0);
        builder.setContentIntent(pendingIntent);

        //更新通知，发送通知
        notificationManager.notify(1,builder.build());
    }
}
