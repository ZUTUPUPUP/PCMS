package com.example.myapplication.Manager.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.myapplication.Manager.ManageFragmentController;
import com.example.myapplication.Manager.Manager.Contest.AddContestActivity;
import com.example.myapplication.Manager.Manager.UserManager.QueryAllUsersActivity;
import com.example.myapplication.R;

import androidx.fragment.app.Fragment;


public class ManageFragment extends Fragment {

    private RadioGroup rg_tab;//顶部选择框
    private ManageFragmentController controller;

    public ManageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage, container, false);
        controller = ManageFragmentController.getInstance(this, R.id.manage_fragment);
        controller.showFragment(0);
        rg_tab = (RadioGroup) view.findViewById(R.id.rg_tab_bar_manage);
        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_manage_contest:
                        //controller.showFragment(0);
                        Intent intent0=new Intent();
                        intent0.setClass(getActivity(),AddContestActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.rb_manage_awards:
                        controller.showFragment(1);
                        break;
                    case R.id.rb_manage_user:
                        startActivity(new Intent(getActivity(), QueryAllUsersActivity.class));
                        controller.showFragment(2);
                        break;
                    case R.id.rb_manage_sign:
                        controller.showFragment(3);
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }
}