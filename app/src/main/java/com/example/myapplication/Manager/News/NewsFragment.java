package com.example.myapplication.Manager.News;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.Manager.News.NewsAdd;
import com.example.myapplication.Manager.Reply.ContactOneUserActivity;
import com.example.myapplication.R;
import com.example.myapplication.User.Mine.Contact.UserContactActivity;
import com.example.myapplication.dao.NewsDao;
import com.example.myapplication.domain.Contact;
import com.example.myapplication.domain.News;
import com.example.myapplication.utils.BaseUrl;
import com.example.myapplication.utils.Contact.MassageItemAdapter;
import com.example.myapplication.utils.News.NewsListItemAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Request;


public class NewsFragment extends Fragment {


    private View view;
    private Button addNews;
    //private NewsDao newsDao;
    private List<News> allNews;
    private ListView lv;
    private Intent getIntent;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        BuildUI();
        getDataGetByOKHttpUtils();
        //getList();//读取用户列表
        return view;
    }

    private void getList() {
        //newsDao = new NewsDao(getContext());
     //   allNews = newsDao.findAll();
        getIntent = getActivity().getIntent();
        Collections.reverse(allNews);
        NewsListItemAdapter adapter=new NewsListItemAdapter(view.getContext(),R.layout.item_listview_news,allNews);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news=allNews.get(position);
                Intent intent=new Intent(view.getContext(), NewsOne.class);
                intent.putExtra("userName",getIntent.getStringExtra("userName"));
                intent.putExtra("id",news.get_id());
                startActivity(intent);
            }
        });
    }
    /**
     * get请求
     */
    public void getDataGetByOKHttpUtils() {
        String url = BaseUrl.BASE_URL + "news/findAll.do";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MMyStringCallback());
    }

    public class MMyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            //setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            ///setTitle("Sample-okHttp");
        }

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {
            e.printStackTrace();
//            tv_result.setText();
//            Toast.makeText(RegisterActivity.this, "onError:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            // Log.v(TAG, "onError:" + e.getMessage());
        }
        @Override
        public void onResponse(String response, int id) {
            //  Log.e(TAG, "onResponse：complete");
//            tv_result.setText();
//            Toast.makeText(RegisterActivity.this, "onResponse:" + response, Toast.LENGTH_SHORT).show();
            // Log.v(TAG, "onResponse:" + response);
            switch (id) {
                case 100:
                    //  Toast.makeText(RegisterActivity.this, "http:数据请求成功",
                    //          Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    // Toast.makeText(RegisterActivity.this, "https:数据请求成功",
                    //          Toast.LENGTH_SHORT).show();
                    break;
            }
            if(response != null) {
                //解析数据
                parseData(response);
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            //Log.e(TAG, "inProgress:" + progress);
        }
    }

    private void parseData(String json) {
        allNews = JSON.parseArray(json, News.class);
        getIntent = getActivity().getIntent();
        Collections.reverse(allNews);
        NewsListItemAdapter adapter=new NewsListItemAdapter(view.getContext(),R.layout.item_listview_news,allNews);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news=allNews.get(position);
                Intent intent=new Intent(view.getContext(), NewsOne.class);
                intent.putExtra("userName",getIntent.getStringExtra("userName"));
                intent.putExtra("date",news.getDate());
                startActivity(intent);
            }
        });
    }
    private void BuildUI() {
        lv = (ListView)view.findViewById(R.id.lv);
        addNews = (Button) view.findViewById(R.id.add);
        addNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), NewsAdd.class);
                intent.putExtra("userName",getIntent.getStringExtra("userName"));
                startActivity(intent);
            }
        });
    }


}
