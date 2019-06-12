package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Base.BaseActivity;
import com.example.myapplication.NettyClient.User;
import com.example.myapplication.UI.Contacts;
import com.example.myapplication.UI.MailListInfoFragment;
import com.example.myapplication.UI.Msg;

import java.util.ArrayList;

public class MailListInfoActivity extends BaseActivity {

    private ImageView imageView;
    private TextView nameText;
    private TextView idText;
    private TextView textView_motto;


    private Contacts contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_list_info);
        Intent intent = getIntent();
        contacts = (Contacts)intent.getSerializableExtra("contacts_info");
//        Log.d("TAG", "go");
        MailListInfoFragment mailListInfoFragment = (MailListInfoFragment) getSupportFragmentManager().findFragmentById(R.id.mail_list_info_fragment);
        imageView = mailListInfoFragment.getView().findViewById(R.id.contacts_image);
        nameText = mailListInfoFragment.getView().findViewById(R.id.contacts_name);
        idText = mailListInfoFragment.getView().findViewById(R.id.contacts_id);

        nameText.setText("昵称：" + contacts.getName());
        idText.setText("id：" + contacts.getId());
        textView_motto = findViewById(R.id.textView_motto);
        textView_motto.setText("个性签名：" + contacts.getMotto());


        Button buttonChat = findViewById(R.id.btn_chat);
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MailListInfoActivity.this, ChatWithOthersActivity.class);
                intent.putExtra("id", contacts.getId());
                if (!User.USERINSTANCE.chatList.containsKey(contacts.getId())) {
                    User.USERINSTANCE.chatList.put(contacts.getId(), new ArrayList<Msg>());
                }
                startActivity(intent);
            }
        });
    }

}
