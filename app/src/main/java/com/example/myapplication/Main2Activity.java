package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Coder.Util;
import com.example.myapplication.Entity.AddFriendsMessage;
import com.example.myapplication.Entity.ChatMessage;
import com.example.myapplication.Entity.Packet;
import com.example.myapplication.NettyClient.ChatClient;

import java.io.IOException;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Main2Activity";
    public static String username;
    public static final int SHOW_MSG=1;
    public static final int FRIEND_REQUEST = 2;
    private Button connectbtn;
    private Button sendbtn;
    private Button requestbtn;
    private TextView messageview;
    private EditText editText;
    private EditText editsender;
    private EditText editreceiver;
    private EditText host;
    private EditText friendName;
    //    private Button connectServer;
    // 客户端对象
    private ChatClient client;
    private static Main2Activity mainActivity;
    public Main2Activity(){
        mainActivity=this;
    }

    public static Main2Activity getMainActivity() {
        return mainActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 控件的初始化
        connectbtn=(Button)findViewById(R.id.connectbtn);
        sendbtn=(Button)findViewById(R.id.sendbtn);
        messageview=(TextView)findViewById(R.id.messageview);
        editText=(EditText)findViewById(R.id.editmessage);
        editsender=(EditText)findViewById(R.id.editsender);
        editreceiver=(EditText)findViewById(R.id.editreceiver);
        requestbtn = findViewById(R.id.request_btn);
        host = (EditText) findViewById(R.id.host);
        friendName = findViewById(R.id.friend_name);
        //        connectServer = (Button) findViewById(R.id.start_connect_server);
        connectbtn.setOnClickListener(this);
        sendbtn.setOnClickListener(this);
        //        connectServer.setOnClickListener(this);
        requestbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.connectbtn: {
                System.out.println("连接服务器！");
                username = editsender.getText().toString();
                new Thread(){
                    @Override
                    public void run() {
                        // 与服务器进行连接
                        Log.e(TAG, "run: 尝试连接");
                        connect();
                    }
                }.start();
                break;
            }

            case R.id.sendbtn: {
                ChatMessage msg = new ChatMessage(username, editreceiver.getText().toString(),editText.getText().toString());
                send(msg);
                break;
            }

            case R.id.request_btn: {
                AddFriendsMessage msg=new AddFriendsMessage(username, friendName.getText().toString(),"request");
                send(msg);
                break;
            }
        }
    }

    /**
     * 创建ChatClient对象，用于连接服务器
     */
    public void connect(){
        try {
            // 创建一个ChatClient实例
//            client = new ChatClient(host.getText().toString(),8888);
            client = new ChatClient("192.168.1.101",8888);
            Log.d("TAG", "110");
            // 开始尝试连接服务器
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     * @param cmsg 对应的消息实体类
     */
    public void send(Packet cmsg){
        client.sendMsg(cmsg);
    }

    /**
     * 开启一个定时器，用于接收服务器传递的消息，并且刷新UI，将消息显示在界面上
     */
    private Handler msghandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==SHOW_MSG){
                messageview.setText(messageview.getText().toString()+msg.getData().getString("msg")+"\n");
            } else if (msg.what == FRIEND_REQUEST) {
                showFriendRequestAlertDialog(msg.getData().getString("msg"));
            }
        }
    };

    public Handler getMsghandler() {
        return msghandler;
    }

    public void showFriendRequestAlertDialog(final String friendName) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Main2Activity.this);
        dialog.setTitle("好友申请");
        dialog.setMessage("对方昵称：" + friendName);
        dialog.setPositiveButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddFriendsMessage msg = new AddFriendsMessage(username, friendName, "agree");
                send(msg);
            }
        });
        dialog.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddFriendsMessage msg = new AddFriendsMessage(username, friendName, "reject");
                send(msg);
            }
        });
        dialog.show();

    }
}
