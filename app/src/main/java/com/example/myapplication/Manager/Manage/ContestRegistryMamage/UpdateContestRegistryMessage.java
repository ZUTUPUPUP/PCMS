package com.example.myapplication.Manager.Manage.ContestRegistryMamage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.dao.ContestRegistryDao;
import com.example.myapplication.domain.ContestRegistry;
import com.example.myapplication.domain.ContestRegistryMessage;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateContestRegistryMessage extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_update_contesregmessage_contestname, tv_update_contesregmessage_username;
    private EditText et_update_contesregmessage_relname, et_update_contesregmessage_class;
    private TextView tv_update_contesregmessage_gender, tv_update_contesregmessage_depname;
    private EditText et_update_contesregmessage_email;
    private ContestRegistryMessage contestRegistryMessage;
    private ContestRegistryDao contestRegistryDao;
    private ImageView delEmail, delClass, delRelName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contest_registry_message);
        Intent intent = getIntent();
        String ids = intent.getStringExtra("ID");
        //Log.v("MyInfo", ids + " ");
        bindUI();
        contestRegistryDao = new ContestRegistryDao(UpdateContestRegistryMessage.this);
        contestRegistryMessage  = contestRegistryDao.findById(Integer.parseInt(ids));
        Log.v("MyInfo", contestRegistryMessage.toString());
        showUI();
        delMessage();
    }

    //确定修改信息
    public void updateSubmit(View v) {
        String relName = et_update_contesregmessage_relname.getText().toString().trim();
        String Class = et_update_contesregmessage_class.getText().toString().trim();
        String email = et_update_contesregmessage_email.getText().toString().trim();
        contestRegistryDao.update(new ContestRegistry(contestRegistryMessage.get_id(), contestRegistryMessage.getContestId(), contestRegistryMessage.getSTNumberId(), contestRegistryMessage.getDepId(), Class, contestRegistryMessage.getGender(), email, relName));
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
    }

    private void delMessage() {
        delRelName.setOnClickListener(this);
        delEmail.setOnClickListener(this);
        delClass.setOnClickListener(this);
    }


    private void showUI() {
        tv_update_contesregmessage_contestname.setText(contestRegistryMessage.getContestName());
        tv_update_contesregmessage_username.setText(contestRegistryMessage.getUserName());
        et_update_contesregmessage_relname.setText(contestRegistryMessage.getRelName());
        et_update_contesregmessage_class.setText(contestRegistryMessage.getClassAndGrade());
        tv_update_contesregmessage_depname.setText(contestRegistryMessage.getName());
        tv_update_contesregmessage_gender.setText(contestRegistryMessage.getGender());
        et_update_contesregmessage_email.setText(contestRegistryMessage.getEmail());
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