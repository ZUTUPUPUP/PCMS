package com.example.myapplication.Manager;


import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;


public class ManagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    private TextView txt_topbar;
    //底部框
    private RadioGroup rg_tab_bar;
    //编辑
    private RadioButton rb_edit;
    //管理
    private RadioButton rb_manage;
    //答疑
    private RadioButton rb_reply;
    private ViewPager vpager;

    private ManagerAdapter mAdapter;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        setContentView(R.layout.activity_manager);
        mAdapter = new ManagerAdapter(getSupportFragmentManager(),1);
        buildUI();
        bindViews();
        rb_edit.setChecked(true);
    }
    public  void buildUI(){
        //设置底部图标大小
        RadioButton rb[]=new RadioButton[3];
        rb[0]= (RadioButton)findViewById(R.id.rb_manage);
        rb[1] = (RadioButton)findViewById(R.id.rb_edit);
        rb[2]= (RadioButton)findViewById(R.id.rb_reply);
        for(RadioButton rb1:rb){
            Drawable drs[] = rb1.getCompoundDrawables();
            Rect r = new Rect(0, 0, drs[1].getMinimumWidth()*10/48, drs[1].getMinimumHeight()*10/48);
            drs[1].setBounds(r);
            rb1.setCompoundDrawables(null,drs[1],null,null);
        }
    }
    private void bindViews() {
        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_edit = (RadioButton) findViewById(R.id.rb_edit);
        rb_manage = (RadioButton) findViewById(R.id.rb_manage);
        rb_reply = (RadioButton) findViewById(R.id.rb_reply);
        rg_tab_bar.setOnCheckedChangeListener(this);

        vpager = (ViewPager) findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_edit:
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rb_manage:
                vpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.rb_reply:
                vpager.setCurrentItem(PAGE_THREE);
                break;
        }
    }


    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    rb_edit.setChecked(true);
                    break;
                case PAGE_TWO:
                    rb_manage.setChecked(true);
                    break;
                case PAGE_THREE:
                    rb_reply.setChecked(true);
                    break;
            }
        }
    }
}
