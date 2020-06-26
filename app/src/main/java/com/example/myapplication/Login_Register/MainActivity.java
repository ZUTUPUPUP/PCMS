package com.example.myapplication.Login_Register;


import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Manager.ManagerActivity;
import com.example.myapplication.R;
import com.example.myapplication.User.UserActivity;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.User;
import com.example.myapplication.utils.MD5Utils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;


/**
 *一个activity理解成是一个页面
 */
public class MainActivity extends AppCompatActivity {
    private EditText et_user_name, et_passWd;//编辑框
    private UserDao dao;
    private static final int PERMISSION_REQUEST = 1001; //申请权限
    //申请权限
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE};
    //申请权限
    List<String> permissionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bindUI();
        initPermissions();//申请权限
        dao = new UserDao(this);
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
                break;
            default:
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
    public void Login(View v) {
        String userName = et_user_name.getText().toString();
        String passWd = et_passWd.getText().toString();
        User user = dao.findByUserName(userName);
        if(user != null && MD5Utils.md5(passWd).equals(user.getPasswd())) {
            Intent intent = null;
            if(user.getStatus_id() == 1) {
                intent = new Intent(this, ManagerActivity.class);
                intent.putExtra("userName", user.getUserName());
            } else if (user.getStatus_id() == 2){
                intent = new Intent(this, UserActivity.class);
                intent.putExtra("userName", user.getUserName());
            }
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(intent);
        } else {
            Toast.makeText(this, "用户名或者密码错误,请重新输入!", Toast.LENGTH_SHORT).show();
            et_user_name.setText("");
            et_passWd.setText("");
        }
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
        intent.putExtra("userName", "admin");
        startActivity(intent);
        this.finish();
    }
    //用户测试需删掉
    public void LoginOnUser(View view) {
        Intent intent=null;
        intent = new Intent(this, UserActivity.class);
        intent.putExtra("userName", "user");
        startActivity(intent);
        this.finish();
    }
}
