package com.example.myapplication.Login_Register;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;


public class RegisterActivity extends AppCompatActivity {

    //用户名，密码，再次输入的密码的控件
    private EditText et_user_name, et_passWd, et_passsWd_again;
    private Button btn_register;
    private RadioGroup Sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置页面布局 ,注册界面
        setContentView(R.layout.activity_register);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bindUI();
    }


    private void bindUI() {

        //从activity_register.xml 页面中获取对应的UI控件
        et_user_name = findViewById(R.id.et_user_name);
        et_passWd = findViewById(R.id.et_psw);
        et_passsWd_again= findViewById(R.id.et_psw_again);
        Sex= findViewById(R.id.SexRadio);
        //注册按钮
//        btn_register.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                //获取输入在相应控件中的字符串
//                getEditString();
//                //判断输入框内容
//                int sex;
//                int sexChoseId = Sex.getCheckedRadioButtonId();
//                switch (sexChoseId) {
//                    case R.id.mainRegisterRdBtnFemale:
//                        sex = 0;
//                        break;
//                    case R.id.mainRegisterRdBtnMale:
//                        sex = 1;
//                        break;
//                    default:
//                        sex = -1;
//                        break;
//                }
//
//                if(TextUtils.isEmpty(userName)){
//                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
//                }else if(TextUtils.isEmpty(psw)){
//                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
//                }else if(TextUtils.isEmpty(pswAgain)) {
//                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
//                } else if (sex<0){
//                    Toast.makeText(RegisterActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
//                }else if(!psw.equals(pswAgain)){
//                    Toast.makeText(RegisterActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
//
//                    /**
//                     *从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
//                     */
//                }else if(isExistUserName(userName)){
//                    Toast.makeText(RegisterActivity.this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
//
//                }else{
//                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//                    //把账号、密码和账号标识保存到sp里面
//                    /**
//                     * 保存账号和密码到SharedPreferences中
//                     */
//                    saveRegisterInfo(userName, psw);
//                    //注册成功后把账号传递到LoginActivity.java中
//                    // 返回值到loginActivity显示
//                    Intent data = new Intent();
//                    data.putExtra("userName", userName);
//                    setResult(RESULT_OK, data);
//                    //RESULT_OK为Activity系统常量，状态码为-1，
//                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
//                    RegisterActivity.this.finish();
//                }
//            }
//        });
    }

    //注册按钮点击事件
    public void Register_reg(View v) {

    }
}

