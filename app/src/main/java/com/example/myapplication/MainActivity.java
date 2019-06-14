package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.Base.BaseActivity;
import com.example.myapplication.Entity.InitRequestMessage;
import com.example.myapplication.NettyClient.User;
import com.example.myapplication.UI.ChatFragment;
import com.example.myapplication.UI.Contacts;
import com.example.myapplication.UI.FindFragment;
import com.example.myapplication.UI.MailListFragment;
import com.example.myapplication.UI.Msg;
import com.example.myapplication.UI.MyFragment;
import com.example.myapplication.UI.TopFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    public static int houtai = 0;
    public static int houtai1 = 0;
    public static int houtai2 = 0;
    private String notifi_id = "channel_1";
    int importance = NotificationManager.IMPORTANCE_HIGH;

    public static ChatFragment chatFragment;
    public static MailListFragment mailListFragment;
    public static FindFragment findFragment;
    public static MyFragment myFragment;

    public static int CHAR = 1;
    public static int MAILLIST = 2;
    public static int FIND = 3;
    public static int MY = 4;

    public static int SHOW_CHAT = 11;
    public static int SHOW_ADD_FRIEND = 12;
    public static int SHOW_ADD_GROUP = 13;

    public static int INIT_MAIL = 21;
    public static int INIT_REQUEST = 22;
    public static int ADD_REQUEST = 23;
    public static int ADD_FRIEND = 24;


    public boolean redPoint1 = false;//新朋友红点是否需要亮
    public boolean redPoint2 = false;//新消息红点是否需要亮

    public static MainActivity mainActivity;

    public MainActivity() {
        mainActivity = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        houtai1 += 1;
    }

    @Override
    protected void onStop() {
        super.onStop();
        houtai1 -= 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatFragment = new ChatFragment();
        mailListFragment = new MailListFragment();
        findFragment = new FindFragment();
        myFragment = new MyFragment();
        setContentView(R.layout.activity_main);
        replaceFragment(chatFragment, 1);
        initMail();
        Button search = findViewById(R.id.btn_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddFriendActivity.class);
                startActivity(intent);
            }
        });
    }

    public void replaceFragment(Fragment fragment, int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        TopFragment topFragment = (TopFragment) getSupportFragmentManager().findFragmentById(R.id.top_fragment);
        if (id == CHAR) {
            topFragment.setText("微信");
        } else if (id == MAILLIST) {
            topFragment.setText("联系人");
        } else if (id == FIND) {
            topFragment.setText("发现");
        } else if (id == MY) {
            topFragment.setText("我");
        }

        transaction.replace(R.id.center_fragment, fragment);
        transaction.commitAllowingStateLoss();
    }


    public void initMail() {
        Contacts newFriend = new Contacts();
        newFriend.setName("新朋友");
        User.USERINSTANCE.friendsInfo.put("newFriend", newFriend);
        //申请好友列表 和 好友列表 消息
        InitRequestMessage init_mail_list = new InitRequestMessage(id, "");
        client.sendMsg(init_mail_list);

    }

    private Handler msghandler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void handleMessage(Message msg) {
            Contacts contacts;
            if (msg.what == INIT_MAIL) {
                contacts = (Contacts) msg.getData().get("contacts");
                if (!User.USERINSTANCE.friendsId.contains(contacts.getId())) {

                    User.USERINSTANCE.friendsId.add(contacts.getId());
                    User.USERINSTANCE.friendsInfo.put(contacts.getId(), contacts);
                }

            } else if (msg.what == INIT_REQUEST) {
                contacts = (Contacts) msg.getData().get("contacts");
                if (!User.USERINSTANCE.friendRequest.contains(contacts.getId())) {

                    User.USERINSTANCE.friendRequest.add(contacts.getId());
                    User.USERINSTANCE.friendsInfo.put(contacts.getId(), contacts);
                }

            } else if (msg.what == ADD_REQUEST) {
                String id = msg.getData().getString("id");
                String name = msg.getData().getString("name");
                String motto = msg.getData().getString("motto");
                contacts = new Contacts(id, name, motto);
                if (!User.USERINSTANCE.friendRequest.contains(contacts.getId())) {

                    User.USERINSTANCE.friendRequest.add(contacts.getId());
                    User.USERINSTANCE.friendsInfo.put(contacts.getId(), contacts);
                }
                if (!MailListFragment.mailListFragment.setRedPoint()) {
                    redPoint1 = true;
                }
            } else if (msg.what == ADD_FRIEND) {
                String string = (String) msg.getData().get("id");
                Log.d("string", string + " s");
                Contacts contacts1 = User.USERINSTANCE.friendsInfo.get(string);
                if (MailListFragment.mailListFragment.getmList() != null) {
                    MailListFragment.mailListFragment.addAdapter(contacts1);
                }

            } else if (msg.what == SHOW_CHAT) {
                String string = (String) msg.getData().get("sendId");
                String stringMsg = (String) msg.getData().get("msg");
                if ((houtai == 0 && houtai2 == 0) || (houtai == 0 && houtai1 == 1) || (houtai1 == 0 && houtai2 == 0)) {
                    try {
                        addNotification(0, string, stringMsg);
                    } catch (Exception e) {

                    }
                }

                User.USERINSTANCE.friendsInfo.get(string).setTemp(stringMsg);

                if (!User.USERINSTANCE.chatList.containsKey(string))
                    User.USERINSTANCE.chatList.put(string, new ArrayList<Msg>());

                if (ChatWithOthersActivity.chatWithOthersActivity == null || !ChatWithOthersActivity.chatWithOthersActivity.contacts.getId().equals(string)) {
                    List<Msg> list = User.USERINSTANCE.chatList.get(string);
                    list.add(new Msg(stringMsg, Msg.TYPE_RECEIVED));
                } else {
                    ChatWithOthersActivity.chatWithOthersActivity.addAdapter(string, new Msg(stringMsg, Msg.TYPE_RECEIVED));
                }
                if (ChatFragment.chatFragment.setRedPoint()) {
                    redPoint2 = true;
                }

                ChatFragment.chatFragment.addAdapter(User.USERINSTANCE.friendsInfo.get(string));
            }

        }
    };

    public Handler getMsghandler() {
        return msghandler;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addNotification(int type, String title, String content) {
        PendingIntent pi = null;
        if (type == 0) { //聊天消息
            Intent intent = new Intent(this, ChatWithOthersActivity.class);
            intent.putExtra("id", title);
            pi = PendingIntent.getActivity(this, 0, intent, 0);
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel;
        try {
            notificationChannel = new NotificationChannel(notifi_id, "test", importance);
            notificationManager.createNotificationChannel(notificationChannel);
        } catch (NoClassDefFoundError error) {

        }

        Notification notification = new NotificationCompat.Builder(this, notifi_id)
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_user))
                .setContentIntent(pi)
                .setVibrate(new long[] {0, 500})
                .setLights(Color.GREEN, 1000, 1000)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();

        notificationManager.notify(1, notification);
    }

}
