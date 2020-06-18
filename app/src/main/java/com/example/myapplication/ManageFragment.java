package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;


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
        rg_tab = (RadioGroup) view.findViewById(R.id.rg_tab_bar_awards);
        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_awards_add:
                        controller.showFragment(0);
                        break;
                    case R.id.rb_awards_delete:
                        controller.showFragment(1);
                        break;
                    case R.id.rb_awards_update:
                        controller.showFragment(2);
                        break;
                    case R.id.rb_awards_query:
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