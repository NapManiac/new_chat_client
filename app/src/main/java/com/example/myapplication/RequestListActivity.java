package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Base.BaseActivity;
import com.example.myapplication.Entity.AddFriendsMessage;
import com.example.myapplication.NettyClient.User;
import com.example.myapplication.UI.Contacts;
import com.example.myapplication.UI.MailListFragment;

public class RequestListActivity extends BaseActivity {

    Contacts contacts;
    TextView friendId;
    TextView friendName;
    TextView friendMotoo;
    Button btn_agree;
    Button btn_reject;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        Intent intent = getIntent();
        contacts = (Contacts)intent.getSerializableExtra("contacts_info");
         friendId = findViewById(R.id.contacts_id);
         friendName = findViewById(R.id.contacts_name);
         friendMotoo = findViewById(R.id.textView_motto);
        result = findViewById(R.id.result);
        btn_agree = findViewById(R.id.btn_agree);
        btn_reject = findViewById(R.id.btn_reject);
        friendId.setText("id：" + contacts.getId());
        friendName.setText("昵称：" + contacts.getName());
        friendMotoo.setText("个性签名：" + contacts.getMotto());

        if (User.USERINSTANCE.friendsId.contains(contacts.getId())) {
            btn_agree.setVisibility(View.INVISIBLE);
            btn_reject.setVisibility(View.INVISIBLE);
            result.setText("已同意");
        }

        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriendsMessage addFriendsMessage = new AddFriendsMessage(id, contacts.getId(), "agree");
                client.sendMsg(addFriendsMessage);
                btn_agree.setVisibility(View.INVISIBLE);
                btn_reject.setVisibility(View.INVISIBLE);
                result.setText("已同意");
                if (!User.USERINSTANCE.friendsId.contains(contacts.getId())) {

                    User.USERINSTANCE.friendsId.add(contacts.getId());;
                    MailListFragment.mailListFragment.addAdapter(contacts);
                }

            }
        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_agree.setVisibility(View.INVISIBLE);
                btn_reject.setVisibility(View.INVISIBLE);
                result.setText("已拒绝");
            }
        });
    }
}
