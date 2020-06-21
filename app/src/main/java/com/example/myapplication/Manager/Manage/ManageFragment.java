package com.example.myapplication.Manager.Manage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Manager.Manage.Awards.AwardsActivity;
import com.example.myapplication.Manager.Manage.Contest.ManagerContestActivity;
import com.example.myapplication.Manager.Manage.ContestRegistryMamage.QueryAllContestRegistryActivity;
import com.example.myapplication.Manager.Manage.UserManage.QueryAllUsersActivity;
import com.example.myapplication.R;
import com.example.myapplication.dao.DepDao;
import com.example.myapplication.dao.UserDao;
import com.example.myapplication.domain.User;

import androidx.fragment.app.Fragment;


public class ManageFragment extends Fragment {

    private View view;
    private TextView tv_manage_nickname, tv_manage_username, tv_manage_dep, tv_manage_gender;
    private Button btn_manage_update_msg;
    private String userName;
    private UserDao userDao;
    private DepDao depDao;
    private Button btn_mine_update_msg;
    private TextView tv_mine_nickname, tv_mine_username, tv_mine_dep, tv_mine_gender;
    private ImageView iv_manage_user, iv_manage_contest, iv_manage_register, iv_manage_awards;
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
        Intent MainIntent=getActivity().getIntent();//得到main里传进来的intent
        userName = MainIntent.getStringExtra("userName");
        bindUI();
        userDao = new UserDao(getActivity());
        depDao = new DepDao(getActivity());
        user = userDao.findByUserName(userName);
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
        iv_manage_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), QueryAllUsersActivity.class));
            }
        });
        iv_manage_contest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent0=new Intent();
                intent0.setClass(getActivity(), ManagerContestActivity.class);
                startActivity(intent0);
            }
        });
        iv_manage_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), QueryAllContestRegistryActivity.class));
            }
        });
        iv_manage_awards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(getActivity(), AwardsActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void updateManageNickName() {
        tv_manage_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(getActivity());
                editText.setHint(userName);
                editText.setSingleLine(true);
                editText.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                //显示AlertDialog
                new AlertDialog.Builder(getActivity())
                        .setTitle("修改昵称")
                        .setView(editText)
                        .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newNickName = editText.getText().toString();
                                tv_mine_nickname.setText(newNickName);
                                user.setNickName(newNickName);
                                userDao.update(user);
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
                                user.setPasswd(newPassWd);
                                userDao.update(user);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
    }

    private void showManageMessage() {
        tv_manage_nickname.setText(user.getNickName());
        tv_manage_username.setText("账号" + user.getUserName());
        tv_manage_dep.setText(depDao.findById(user.getDepartment_id()).getName());
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
    }
}