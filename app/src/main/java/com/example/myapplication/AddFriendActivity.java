package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Base.BaseActivity;
import com.example.myapplication.Coder.Util;
import com.example.myapplication.Entity.AddFriendsMessage;
import com.example.myapplication.Entity.SearchMessage;
import com.example.myapplication.NettyClient.User;
import com.example.myapplication.UI.Contacts;

public class AddFriendActivity extends BaseActivity {

    public TextView textView_name;
    public TextView textView_motto;
    public EditText editText_id;
    public Button button_search;
    public Button button_request;

    public static int SHOW_INFO = 1;

    public static int SHOW_EXIST = 2;

    public static AddFriendActivity addFriendActivity;

    private String friendId;

    public AddFriendActivity() {
        addFriendActivity = this;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
           if (msg.what == SHOW_INFO) {
               String id = msg.getData().getString("id");
               String name = msg.getData().getString("name");
               String motto = msg.getData().getString("motto");
               textView_name.setText(name);
               textView_motto.setText(motto);
               friendId = id;
               User.USERINSTANCE.friendsInfo.put(id, new Contacts(id, name, motto));
           } else if (msg.what == Util.SEARCH_NO_FIND) {
               textView_name.setText("");
               textView_motto.setText("");
               friendId = "";
               Toast.makeText(AddFriendActivity.this, "没有找到该用户！", Toast.LENGTH_SHORT).show();
           } else if (msg.what == SHOW_EXIST) {
               Toast.makeText(AddFriendActivity.this, "ta已经是你好友了，请勿重复添加！", Toast.LENGTH_SHORT).show();
           }

        }
    };

    public Handler getHandler() {
        return handler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        textView_name = findViewById(R.id.textView_name);
        textView_motto = findViewById(R.id.textView_motto);
        editText_id = findViewById(R.id.edit_search_friend);
        button_search = findViewById(R.id.btn_search_friend);
        button_request = findViewById(R.id.btn_request);


        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //向服务器发送朋友请求
                SearchMessage searchMessage = new SearchMessage("", editText_id.getText().toString(), Util.SEARCH_FREIND);
                client.sendMsg(searchMessage);
            }
        });

        button_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.USERINSTANCE.friendsId.contains(friendId)) {
                    Toast.makeText(AddFriendActivity.this, "好友已存在！", Toast.LENGTH_SHORT).show();
                } else {
                    AddFriendsMessage addFriendsMessage = new AddFriendsMessage(id, friendId,"request");
                    client.sendMsg(addFriendsMessage);
                    Toast.makeText(AddFriendActivity.this, "已发送申请！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
