package com.example.myapplication.Manager.Manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Manager.Manage.Awards.AwardsActivity;
import com.example.myapplication.Manager.Manage.Contest.AddContestActivity;
import com.example.myapplication.R;


public class ManageFragment extends Fragment {

    private RadioGroup rg_tab;//顶部选择框

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
                        Intent intent1=new Intent();
                        intent1.setClass(getActivity(), AwardsActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.rb_manage_user:

                        break;
                    case R.id.rb_manage_sign:

                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }
}