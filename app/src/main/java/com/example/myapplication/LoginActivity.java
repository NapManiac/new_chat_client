package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Base.BaseActivity;
import com.example.myapplication.Entity.InitMessage;
import com.example.myapplication.NettyClient.ChatClient;
import com.example.myapplication.NettyClient.User;

import java.io.IOException;

public class LoginActivity extends BaseActivity {

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private CheckBox rememberPass;

    private EditText accountEdit;

    private EditText passwordEdit;

    private Button register;

    private Button login;



    private String password;

    public static InitMessage loginMessage;



    public static LoginActivity loginActivity;

    public LoginActivity(){
        loginActivity = this;
    }


    public static LoginActivity getLoginActivity() {
        return loginActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        new Thread(){
            @Override
            public void run() {
                // 与服务器进行连接
                connect();
                Log.d("TAG", "连接");
            }
        }.start();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass = findViewById(R.id.remember_pass);

        accountEdit = findViewById(R.id.id);
        passwordEdit = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        boolean isRemember = pref.getBoolean("remember_password", false);

        if (isRemember) {
            String account = pref.getString("id", "");
            String password = pref.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = accountEdit.getText().toString();
                password = passwordEdit.getText().toString();
                //向服务端发送消息 确认账号密码
                loginMessage = new InitMessage(id, "server", id, password);
                client.sendMsg(loginMessage);
                User.USERINSTANCE.setId(id);

            }
        });
    }


    private Handler msghandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String login_result = msg.getData().getString("login");
            if (login_result.equals("error_pw")) {
                Toast.makeText(LoginActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
            } else if (login_result.equals("success")) {

                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                //并实现相关跳转
                editor = pref.edit();
                if (rememberPass.isChecked()) {
                    editor.putBoolean("remember_password", true);
                    editor.putString("id", id);
                    editor.putString("password", password);
                } else {
                    editor.clear();
                }
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            } else if (login_result.equals("no_register")) {
                Toast.makeText(LoginActivity.this, "账号未注册！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public Handler getMsghandler() {
        return msghandler;
    }

    public void connect(){
        try {
            // 创建一个ChatClient实例
//            client = new ChatClient(host.getText().toString(),8888);
            client = new ChatClient("192.168.1.101",8888);
//            client = new ChatClient("192.168.1.107",8888);
            // 开始尝试连接服务器
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
