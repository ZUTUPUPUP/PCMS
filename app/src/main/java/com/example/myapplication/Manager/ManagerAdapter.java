package com.example.myapplication.Manager;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.Manager.Edit.EditFragment;
import com.example.myapplication.Manager.Manager.ManageFragment;
import com.example.myapplication.ReplyFragment;

public class ManagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 3;
    private EditFragment editFragment = null;
    private ManageFragment manageFragment = null;
    private ReplyFragment replyFragment = null;


    public ManagerAdapter(FragmentManager fm,int w) {
        super(fm,w);
        editFragment = new EditFragment();
        manageFragment = new ManageFragment();
        replyFragment = new ReplyFragment();
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case ManagerActivity.PAGE_ONE:
                fragment = editFragment;
                break;
            case ManagerActivity.PAGE_TWO:
                fragment = manageFragment;
                break;
            case ManagerActivity.PAGE_THREE:
                fragment = replyFragment;
                break;
        }
        return fragment;
    }
}