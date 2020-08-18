package com.example.myapplication.Manager.Manage.Awards;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.R;
import com.example.myapplication.dao.AwardsDao;
import com.example.myapplication.domain.AwardsInfo;
import com.example.myapplication.domain.Pie;
import com.example.myapplication.utils.BaseUrl;
import com.example.myapplication.views.PieChart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AwardsStatisticsActivity extends AppCompatActivity {

    private AwardsDao awardsDao;
    private PieChart pieChart1,pieChart2;
    private EditText et_contestname;
    private EditText et_awards;
    private List<AwardsInfo> data;

    OkHttpClient client = new OkHttpClient();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    setData(data);
                    Toast.makeText(AwardsStatisticsActivity.this, "统计成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_awards_query);
        pieChart1 = findViewById(R.id.piechart1);
        pieChart2 = findViewById(R.id.piechart2);
        et_contestname = findViewById(R.id.et_contestname);
        et_awards = findViewById(R.id.et_awards);
        awardsDao = new AwardsDao(this);
        //awardsDao.findByContestName("acm新生赛");
        //List<AwardsInfo> classInfo = awardsDao.findByContestAndAward("acm新生赛","二等奖");
        //进入页面默认全部查询
//        List<AwardsInfo> allInfo = find();
//        setData(allInfo);
    }

    //查询数据库数据结果排序分类处理装入图饼状图
    private void setData(List<AwardsInfo> Info) {
        ArrayList<Pie> list = new ArrayList();
        // 数据用map拆分
        if (Info != null && Info.size() > 0) {
            HashMap<String, Integer> map = new HashMap<>();
            for (AwardsInfo info : Info) {
                int count = map.get(info.getClassName()) == null ? 0 : map.get(info.getClassName());
                map.put(info.getClassName(), ++count);
            }
            //重新组装数据
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                Pie pie = new Pie();
                pie.setStr(entry.getKey());
                pie.setValue(entry.getValue());
                pie.setNum(entry.getValue());
                list.add(pie);
            }
        }
        pieChart1.setData(list);

        ArrayList<Pie> list2 = new ArrayList();
        // 数据用map拆分
        if (Info != null && Info.size() > 0) {
            HashMap<String, Integer> map = new HashMap<>();
            for (AwardsInfo info : Info) {
                int count = map.get(info.getDepName()) == null ? 0 : map.get(info.getDepName());
                map.put(info.getDepName(), ++count);
            }
            //重新组装数据
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                Pie pie = new Pie();
                pie.setStr(entry.getKey());
                pie.setValue(entry.getValue());
                pie.setNum(entry.getValue());
                list2.add(pie);
            }
        }
        pieChart2.setData(list2);
    }



    public void find(final AwardsInfo awardsInfo){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = BaseUrl.BASE_URL + "awards/findAllByPrint.do";
                //Log.v("MyInfo", JSON.toJSONString(json));
                String contestRegistry1Json = JSON.toJSONString(awardsInfo);
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), contestRegistry1Json);
                System.out.println(body);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //Log.d(getActivity(),"<<<<e="+e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()) {
                            String d = response.body().string();
                            //Log.d(getActivity(),"<<<<d=" + d);
                            data = JSON.parseArray(d, AwardsInfo.class);
                            //flag[0] = true;
                            android.os.Message msg = android.os.Message.obtain();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();
    }


    public void click(View view) {
        String awards=et_awards.getText().toString().trim();
        String contestname=et_contestname.getText().toString().trim();
        AwardsInfo info = new AwardsInfo();
        info.setContestName(contestname);
        info.setAwardLevel(awards);
        find(info);
    }
}