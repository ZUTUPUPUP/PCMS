package com.example.myapplication;



import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.User;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;


/**
 *一个activity理解成是一个页面
 */
public class MainActivity extends AppCompatActivity {
    private EditText et_user_name, et_passWd;//编辑框
    private UserDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bindUI();
        dao = new UserDao(this);
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
            MainActivity.this.finish();
            startActivity(intent);
        } else {
            Toast.makeText(this, "用户名或者密码错误,请重新输入!", Toast.LENGTH_SHORT).show();
            et_user_name.setText("");
            et_passWd.setText("");
        }
    }

    //获取界面控件
    private void bindUI() {
        //从main_title_bar中获取的id
        //从activity_login.xml中获取的
        TextView tv_register = findViewById(R.id.tv_register);
        Button btn_login = findViewById(R.id.btn_login);
        et_user_name = findViewById(R.id.et_user_name);
        et_passWd = findViewById(R.id.et_psw);
        //立即注册控件的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //为了跳转到注册界面，并实现注册功能
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });
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
        if(data != null){
            //是获取注册界面回传过来的用户名
            // getExtra().getString("***");
            String userName = data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                //设置用户名到 et_user_name 控件
                et_user_name.setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
                et_user_name.setSelection(userName.length());
            }
        }
    }
}
