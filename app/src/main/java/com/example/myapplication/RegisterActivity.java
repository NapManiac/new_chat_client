package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Base.BaseActivity;
import com.example.myapplication.Entity.Packet;
import com.example.myapplication.Entity.RegisterMessage;

public class RegisterActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText id = findViewById(R.id.id);
        final EditText password = findViewById(R.id.password);
        final EditText name = findViewById(R.id.name);
        final EditText motto = findViewById(R.id.motto);

        Button button = findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterMessage registerMessage = new RegisterMessage("", "server", id.getText().toString(), password.getText().toString(), name.getText().toString(), motto.getText().toString());
                send(registerMessage);
            }
        });

    }

    public void send(Packet cmsg){
        client.sendMsg(cmsg);
    }

    private static RegisterActivity registerActivity;
    public RegisterActivity(){
        registerActivity = this;
    }

    public static RegisterActivity getRegisterActivity() {
        return registerActivity;
    }

    private Handler msghandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String register_result = msg.getData().getString("register");
            if (register_result.equals("exist")) {
                Toast.makeText(RegisterActivity.this, "该账号已被注册！", Toast.LENGTH_SHORT).show();
            } else if (register_result.equals("success")) {
                Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                //并实现相关跳转
                finish();
            }
        }
    };

    public Handler getMsghandler() {
        return msghandler;
    }

}
