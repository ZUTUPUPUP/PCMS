package com.example.myapplication.Manager.Edit;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.NewsFragment;
import com.example.myapplication.NoticeFragment;

import java.util.ArrayList;

public class EditFragmentController {
    private int containerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private static EditFragmentController controller;

    public static EditFragmentController getInstance(Fragment parentFragment, int containerId) {
        if (controller == null) {
            controller = new EditFragmentController(parentFragment, containerId);
        }
        return controller;
    }

    private EditFragmentController(Fragment fragment, int containerId) {
        this.containerId = containerId;
        //fragment嵌套fragment，调用getChildFragmentManager
        fm = fragment.getChildFragmentManager();

        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new NewsFragment());
        fragments.add(new NoticeFragment());

        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commitAllowingStateLoss();
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            if(fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }
}
