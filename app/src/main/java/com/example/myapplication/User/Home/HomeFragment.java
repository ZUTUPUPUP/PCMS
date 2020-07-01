package com.example.myapplication.User.Home;

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

import com.example.myapplication.Manager.News.NewsAdd;
import com.example.myapplication.Manager.Reply.ContactOneUserActivity;
import com.example.myapplication.R;
import com.example.myapplication.User.Mine.Contact.UserContactActivity;
import com.example.myapplication.dao.NewsDao;
import com.example.myapplication.domain.Contact;
import com.example.myapplication.domain.News;
import com.example.myapplication.utils.News.NewsListItemAdapter;

import java.util.Collections;
import java.util.List;


public class HomeFragment extends Fragment {


    private View view;
    private Button addNews;
    private NewsDao newsDao;
    private List<News> allNews;
    private ListView lv;
    private Intent getIntent;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        BuildUI();
        getList();//读取用户列表
        return view;
    }

    private void getList() {
        newsDao = new NewsDao(getContext());
        allNews = newsDao.findAll();
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
                intent.putExtra("contestId",news.getContestId());
                intent.putExtra("id",news.get_id());
                startActivity(intent);
            }
        });
    }

    private void BuildUI() {
        lv = (ListView)view.findViewById(R.id.lv);

    }


}
