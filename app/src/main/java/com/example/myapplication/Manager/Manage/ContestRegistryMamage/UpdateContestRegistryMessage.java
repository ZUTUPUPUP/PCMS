package com.example.myapplication.Manager.Manage.ContestRegistryMamage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.dao.ContestRegistryDao;
import com.example.myapplication.domain.ContestRegistry;
import com.example.myapplication.domain.ContestRegistryMessage;
import com.example.myapplication.utils.BaseUrl;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateContestRegistryMessage extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_update_contesregmessage_contestname, tv_update_contesregmessage_username;
    private EditText et_update_contesregmessage_relname, et_update_contesregmessage_class;
    private TextView tv_update_contesregmessage_gender, tv_update_contesregmessage_depname;
    private EditText et_update_contesregmessage_email;
    private ContestRegistryMessage contestRegistryMessage;
    private ContestRegistryDao contestRegistryDao;
    private ImageView delEmail, delClass, delRelName;
    private ContestRegistry contestRegistry;
    OkHttpClient client = new OkHttpClient();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(UpdateContestRegistryMessage.this, "修改成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_update_contest_registry_message);
        Intent intent = getIntent();
        String json = intent.getStringExtra("contestRegistry");
        contestRegistry = JSON.parseObject(json, ContestRegistry.class);
        System.out.println(contestRegistry);
        //Log.v("MyInfo", ids + " ");
        bindUI();
        //contestRegistryDao = new ContestRegistryDao(UpdateContestRegistryMessage.this);
        //contestRegistryMessage  = contestRegistryDao.findById(Integer.parseInt(ids));
        //Log.v("MyInfo", contestRegistryMessage.toString());
        showUI();
        delMessage();
    }

    //确定修改信息
    public void updateSubmit(View v) {
        String relName = et_update_contesregmessage_relname.getText().toString().trim();
        String Class = et_update_contesregmessage_class.getText().toString().trim();
        String email = et_update_contesregmessage_email.getText().toString().trim();
        contestRegistry.setRelName(relName.isEmpty() ? contestRegistry.getUser().getUserName() : relName);
        contestRegistry.setClassAndGrade(Class.isEmpty() ? "计科171" : Class);
        contestRegistry.setEmail(email.isEmpty() ? "1111@qq.com" : email);
        //contestRegistryDao.update(new ContestRegistry(contestRegistryMessage.get_id(), contestRegistryMessage.getContestId(), contestRegistryMessage.getSTNumberId(), contestRegistryMessage.getDepId(), Class, contestRegistryMessage.getGender(), email, relName));
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "contestRegistry/update.do";
                String contestRegistryJson = JSON.toJSONString(contestRegistry);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), contestRegistryJson);
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
                            Message msg = Message.obtain();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();
        //Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
    }

    private void delMessage() {
        delRelName.setOnClickListener(this);
        delEmail.setOnClickListener(this);
        delClass.setOnClickListener(this);
    }


    private void showUI() {
        tv_update_contesregmessage_contestname.setText(contestRegistry.getContest().getContestName());
        tv_update_contesregmessage_username.setText(contestRegistry.getUser().getUserName());
        et_update_contesregmessage_relname.setText(contestRegistry.getRelName());
        et_update_contesregmessage_class.setText(contestRegistry.getClassAndGrade());
        tv_update_contesregmessage_depname.setText(contestRegistry.getUser().getDep().getName());
        tv_update_contesregmessage_gender.setText(contestRegistry.getGender());
        et_update_contesregmessage_email.setText(contestRegistry.getEmail());
    }

    private void bindUI() {
        tv_update_contesregmessage_contestname = findViewById(R.id.tv_update_contesregmessage_contestname);
        tv_update_contesregmessage_username = findViewById(R.id.tv_update_contesregmessage_username);
        et_update_contesregmessage_relname = findViewById(R.id.et_update_contesregmessage_relname);
        et_update_contesregmessage_class = findViewById(R.id.et_update_contesregmessage_class);
        tv_update_contesregmessage_depname = findViewById(R.id.tv_update_contesregmessage_depname);
        tv_update_contesregmessage_gender = findViewById(R.id.tv_update_contesregmessage_gender);
        et_update_contesregmessage_email = findViewById(R.id.et_update_contesregmessage_email);
        delEmail = findViewById(R.id.iv_update_contesregmessage_delEmail);
        delClass = findViewById(R.id.iv_update_contesregmessage_delClass);
        delRelName = findViewById(R.id.iv_update_contesregmessage_delRelName);
    }

    @Override
    public void onClick(View v) {
        if (v == delEmail) {
            et_update_contesregmessage_email.setText("");
        } else if (v == delRelName) {
            et_update_contesregmessage_relname.setText("");
        } else if (v == delClass) {
            et_update_contesregmessage_class.setText("");
        }
    }
}