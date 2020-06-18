package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

public class EditFragment extends Fragment {

    private RadioGroup rg_tab;//顶部选择框
    private EditFragmentController controller;


    public EditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        controller = EditFragmentController.getInstance(this, R.id.edit_fragment);
        controller.showFragment(0);
        rg_tab = (RadioGroup) view.findViewById(R.id.rg_tab_bar_edit);
        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_edit_news:
                        controller.showFragment(0);
                        break;
                    case R.id.rb_edit_notice:
                        controller.showFragment(1);
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }
}