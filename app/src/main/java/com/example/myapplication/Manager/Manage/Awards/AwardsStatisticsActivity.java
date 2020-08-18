package com.example.myapplication.Manager.Manage.Awards;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.dao.AwardsDao;
import com.example.myapplication.domain.AwardsInfo;
import com.example.myapplication.domain.Pie;
import com.example.myapplication.views.PieChart;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class AwardsStatisticsActivity extends AppCompatActivity {

    private AwardsDao awardsDao;
    private PieChart pieChart;
    private EditText et_contestname;
    private EditText et_awards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_awards_query);
        pieChart = findViewById(R.id.piechart);
        et_contestname = findViewById(R.id.et_contestname);
        et_awards = findViewById(R.id.et_awards);
        awardsDao = new AwardsDao(this);
        //awardsDao.findByContestName("acm新生赛");
        //List<AwardsInfo> classInfo = awardsDao.findByContestAndAward("acm新生赛","二等奖");
        //进入页面默认全部查询
        List<AwardsInfo> classInfo = awardsDao.findByContestAndAward();
        setData(classInfo);
        //点击条件查询
        click();
    }

    //查询数据库数据结果排序分类处理装入图饼状图
    private void setData(List<AwardsInfo> classInfo) {
        ArrayList<Pie> list = new ArrayList();
        // 数据用map拆分
        if (classInfo != null && classInfo.size() > 0) {
            HashMap<String, Integer> map = new HashMap<>();
            for (AwardsInfo info : classInfo) {
                int count = map.get(info.getClassName()) == null ? 0 : map.get(info.getClassName());
                map.put(info.getClassName(), ++count);
            }
            //重新组装数据
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                Pie pie = new Pie();
                pie.setClassName(entry.getKey());
                pie.setValue(entry.getValue());
                list.add(pie);
            }
        }
        pieChart.setData(list);
    }

    private void click() {
        findViewById(R.id.but_StatisticsClassOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_awards.getText().toString().trim().equals("") &&
                        et_contestname.getText().toString().trim().equals("")) {
                    Snackbar.make(et_awards, "请输入竞赛名和获奖等级", 5000).show();
                } else if(et_contestname.getText().toString().trim().equals("")){
//                   List<AwardsInfo> classInfo = awardsDao.findByAward(et_contestname.getText().toString().trim());
//                   setData(classInfo);
                    Snackbar.make(et_awards, "请输入竞赛名", 5000).show();
                } else if(et_awards.getText().toString().trim().equals("")){
                    List<AwardsInfo> classInfo = awardsDao.findByContestName(et_contestname.getText().toString().trim());
                    if(classInfo.size() == 0) Snackbar.make(et_awards, "请输入正确比赛名称", 5000).show();
                    setData(classInfo);
                } else {
                    List<AwardsInfo> classInfo = awardsDao.findByContestAndAward(et_contestname.getText().toString().trim(), et_awards.getText().toString().trim());
                    if(classInfo.size() == 0) Snackbar.make(et_awards, "请输入正确比赛名称与奖项", 5000).show();
                    setData(classInfo);
                }
            }
        });
    }

}