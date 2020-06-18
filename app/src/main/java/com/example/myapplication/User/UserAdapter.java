package com.example.myapplication.User;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.User.Home.HomeFragment;
import com.example.myapplication.User.Message.MessageFragment;
import com.example.myapplication.User.Mine.MineFragment;

public class UserAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 3;
    private HomeFragment homeFragment = null;
    private MessageFragment messageFragment = null;
    private MineFragment mineFragment = null;


    public UserAdapter(FragmentManager fm,int w) {
        super(fm,w);
        homeFragment = new HomeFragment();
        messageFragment = new MessageFragment();
        mineFragment = new MineFragment();
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
            case UserActivity.PAGE_ONE:
                fragment = homeFragment;
                break;
            case UserActivity.PAGE_TWO:
                fragment = messageFragment;
                break;
            case UserActivity.PAGE_THREE:
                fragment = mineFragment;
                break;
        }
        return fragment;
    }
}