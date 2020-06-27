package com.example.myapplication.User.Message;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Manager.Reply.ContactOneUserActivity;
import com.example.myapplication.R;
import com.example.myapplication.dao.MessageDao;
import com.example.myapplication.domain.Contact;
import com.example.myapplication.domain.Message;

import java.util.List;


public class MessageFragment extends Fragment {


    private View view;
    private ListView listView;
    private MessageDao messageDao;
    private List<Message> list;
    private MessageAdapter adapter;
    private String userName;
    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_message, container, false);
        messageDao = new MessageDao(getContext());
        initUI();
        getMessage();
        return view;
    }

    private void getMessage(){
        Intent MainIntent=getActivity().getIntent();//得到main里传进来的intent
        userName = MainIntent.getStringExtra("userName");
        list = messageDao.findByUserId(userName);
        Log.v("用户名：",userName);
        for(int i=0;i<list.size();++i){
            int id = list.get(i).get_id();
            String userId = list.get(i).getUserId();
            Log.v("ID编号：",id+"");
            Log.v("用户名",userId);
        }
        adapter = new MessageAdapter(view.getContext(),list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = list.get(position);
                //打开私聊页面
                int _id = message.get_id();
                Intent intent=new Intent(view.getContext(), MessageDetialActivity.class);
                intent.putExtra("userId",_id+"");
                startActivity(intent);
            }
        });
    }

    private void initUI(){
        listView = view.findViewById(R.id.lv_message);
    }

    //Activity创建或者从被覆盖、后台重新回到前台时被调用
    @Override
    public void onResume() {
        super.onResume();
        //重新获取list数据
        list = messageDao.findByUserId(userName);
        adapter.setList(list);
    }
}