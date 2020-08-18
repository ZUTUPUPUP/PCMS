package com.example.myapplication.Login_Register;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.Dep;
import com.example.myapplication.domain.Status;
import com.example.myapplication.domain.User;
import com.example.myapplication.utils.BaseUrl;
import com.example.myapplication.utils.DensityUtil;
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


public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "RegisterActivity";
    //用户名，密码，再次输入的密码的控件
    private EditText et_user_name, et_passWd, et_passsWd_again, et_reg_nickname;
    private ImageView iv_reg_del;
    private TextView tv_user_dep;
    private RadioGroup rg_reg_sex;
    private ListView listView;
    private List<Dep> data;
    private RegisterAdapter adapter;
    private User user = new User();
    UserDao userDao = new UserDao(this);

    String name = null;
    String passWd = null;

    //一个能显示View的窗体
    private PopupWindow popupWindow;


    OkHttpClient client = new OkHttpClient();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String res = (String) msg.obj;
                    if (res.equals("STNumber Error")) {
                        Toast.makeText(RegisterActivity.this, "请输入正确的学号!", Toast.LENGTH_SHORT).show();
                    } else if (res.equals("STNumber Exist")) {
                        Toast.makeText(RegisterActivity.this, "该学号已经存在,请重新输入!", Toast.LENGTH_SHORT).show();
                    } else if (res.equals("passWd Short")) {
                        Toast.makeText(RegisterActivity.this, "密码长度不符合要求,请修改密码!", Toast.LENGTH_SHORT).show();
                    } else if(res.equals("Insert Success")) {
                        Intent data = new Intent();
                        data.putExtra("userName", name);
                        data.putExtra("passWd", passWd);
                        setResult(2, data);
                        finish();
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置页面布局 ,注册界面
        setContentView(R.layout.activity_register);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bindUI();
        listView = new ListView(this);
        listView.setBackgroundResource(R.mipmap.listview_background);

        getDataGetByOKHttpUtils();
        //给listView的项设置点击事件
        listView.setOnItemClickListener(this);
        Dep dep = new Dep();
        dep.set_id(1);
        user.setDep(dep);
        user.setGender("女");
        rg_reg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = findViewById(checkedId);
                String gender = button.getText().toString();
                user.setGender(gender);
            }
        });

        iv_reg_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_passWd.setText("");
                et_passsWd_again.setText("");
            }
        });

    }
    //注册按钮点击事件
    public void Register_reg(View v) {
        name = et_user_name.getText().toString().trim();
        passWd = et_passWd.getText().toString().trim();
        final String passWdAgain = et_passsWd_again.getText().toString().trim();
        user.setUserName(name);
        user.setPasswd(passWd);
        String nickName = et_reg_nickname.getText().toString().trim();
        user.setNickName(nickName.isEmpty() ? user.getUserName() : nickName);
        Status status = new Status();
        status.set_id(2);
        user.setStatus(status);

        if(!passWd.equals(passWdAgain)) {
            Toast.makeText(this, "两次密码不一致,请检查!", Toast.LENGTH_SHORT).show();
        } else if (user.getUserName().isEmpty()) {
            Toast.makeText(this, "请输入学号!", Toast.LENGTH_SHORT).show();
        }else {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = BaseUrl.BASE_URL + "user/insert.do";
                    //Log.v("MyInfo", JSON.toJSONString(json));
                    String userJson = JSON.toJSONString(user);
//                JSONObject json = new JSONObject();
//                try {
//                    json.put("user", userJson);
//                    json.put("passWdAgain", JSON.toJSONString(passWdAgain));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
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
                            Log.d(TAG,"<<<<e="+e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if(response.isSuccessful()) {
                                String d = response.body().string();
                                Log.d(TAG,"<<<<d=" + d);
                                Message msg = Message.obtain();
                                msg.what = 1;
                                msg.obj = d;
                                handler.sendMessage(msg);
                                //res[0] = JSON.parseObject(d, String.class);
                            }
                        }
                    });
                }
            }).start();
        }
    }

    //ListView点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Dep dep = data.get(position);
        user.setDep(dep);
        tv_user_dep.setText(dep.getName());
        //选择后取消
        if(popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    private void bindUI() {
        //从activity_register.xml 页面中获取对应的UI控件
        et_user_name = findViewById(R.id.et_user_name);
        et_passWd = findViewById(R.id.et_psw);
        et_passsWd_again = findViewById(R.id.et_psw_again);
        tv_user_dep = findViewById(R.id.et_user_dep);
        rg_reg_sex = findViewById(R.id.rg_reg_sex);
        et_reg_nickname = findViewById(R.id.et_reg_nickname);
        iv_reg_del = findViewById(R.id.iv_reg_del);
    }

    //学院下拉点击事件
    public void regDownArrow(View v) {
        if(popupWindow == null) {
            popupWindow = new PopupWindow(this);
            //设置宽高
            popupWindow.setWidth(tv_user_dep.getWidth());
            int height = DensityUtil.dip2px(this, 300);//dp->px
            popupWindow.setHeight(height);

            popupWindow.setContentView(listView);
            popupWindow.setFocusable(true);//设置焦点,点击事件才会起作用
        }
        //设置显示在文本框下面以及位置
        popupWindow.showAsDropDown(tv_user_dep, 0, 0);
    }

    /**
     * get请求
     */
    public void getDataGetByOKHttpUtils() {
        String url = BaseUrl.BASE_URL + "dep/findAll.do";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
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
                    Toast.makeText(RegisterActivity.this, "http:数据请求成功",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(RegisterActivity.this, "https:数据请求成功",
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
        data = JSON.parseArray(json, Dep.class);
        Log.v("MyInfo", data.toString());
        adapter = new RegisterAdapter(this, data);
        listView.setAdapter(adapter);
    }
}

