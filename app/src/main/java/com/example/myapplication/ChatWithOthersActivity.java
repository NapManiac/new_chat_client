package com.example.myapplication;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Adapter.MsgAdapter;
import com.example.myapplication.Base.BaseActivity;
import com.example.myapplication.Entity.ChatMessage;
import com.example.myapplication.NettyClient.User;
import com.example.myapplication.UI.Contacts;
import com.example.myapplication.UI.Msg;

import java.util.List;

public class ChatWithOthersActivity extends BaseActivity {

    private List<Msg> msgList;
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyckerView;
    private MsgAdapter adapter;
    private Contacts contacts;
    public static ChatWithOthersActivity chatWithOthersActivity;

    public ChatWithOthersActivity() {
        chatWithOthersActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with_others);
        NotificationManager manager = (NotificationManager) getSystemService
                (NOTIFICATION_SERVICE);
        manager.cancel(1);
        final Intent intent = getIntent();
        final String friendId = intent.getStringExtra("id");

        TextView textView_friendId = findViewById(R.id.friend_id);


        contacts = User.USERINSTANCE.friendsInfo.get(friendId);
        textView_friendId.setText("与" + contacts.getName() + "聊天中");

        send = findViewById(R.id.send);
        inputText = findViewById(R.id.input_text);
        msgRecyckerView = findViewById(R.id.msg_recycler_view);
        //这里要加数据
        msgList = User.USERINSTANCE.chatList.get(friendId);



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        msgRecyckerView.setLayoutManager(linearLayoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyckerView.setAdapter(adapter);

        msgRecyckerView.scrollToPosition(msgList.size() - 1);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);


                    User.USERINSTANCE.friendsInfo.get(friendId).setTemp(content);


                    adapter.notifyItemInserted(msgList.size() - 1); // 当有新消息时，
                    msgRecyckerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText(""); // 清空输入框中的内容
                    ChatMessage chatMessage = new ChatMessage(id, friendId, content);
                    client.sendMsg(chatMessage);
                }
            }
        });



    }

    public void addAdapter(String id, Msg msg) {
        msgList = User.USERINSTANCE.chatList.get(id);
        msgList.add(msg);

        adapter.notifyItemInserted(msgList.size() - 1);
        msgRecyckerView.scrollToPosition(msgList.size() - 1);
    }


}

