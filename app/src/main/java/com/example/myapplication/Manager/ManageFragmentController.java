package com.example.myapplication.Manager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Manager.Manager.AwardsAddFragment;
import com.example.myapplication.Manager.Manager.AwardsDeleteFragment;
import com.example.myapplication.Manager.Manager.AwardsQueryFragment;
import com.example.myapplication.Manager.Manager.AwardsUpdateFragment;

import java.util.ArrayList;

public class ManageFragmentController {
    private int containerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private static ManageFragmentController controller;

    public static ManageFragmentController getInstance(Fragment parentFragment, int containerId) {
        if (controller == null) {
            controller = new ManageFragmentController(parentFragment, containerId);
        }
        return controller;
    }

    private ManageFragmentController(Fragment fragment, int containerId) {
        this.containerId = containerId;
        //fragment嵌套fragment，调用getChildFragmentManager
        fm = fragment.getChildFragmentManager();

        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new AwardsAddFragment());
        fragments.add(new AwardsDeleteFragment());
        fragments.add(new AwardsUpdateFragment());
        fragments.add(new AwardsQueryFragment());

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
