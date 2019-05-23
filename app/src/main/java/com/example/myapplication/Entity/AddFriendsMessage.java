package com.example.myapplication.Entity;

import android.os.Bundle;
import android.os.Message;

import com.example.myapplication.Coder.Util;
import com.example.myapplication.Main2Activity;




import java.io.UnsupportedEncodingException;



public class AddFriendsMessage extends Packet {

    private String message;

    public String getMessage() {
        return message;
    }

    public AddFriendsMessage() { // 空参构造
    }

    public AddFriendsMessage(String sendUser, String receiveUser, String message){
        super(Util.MSG_ADDFRIENDS, sendUser, receiveUser);
        this.message=message;

    }

    @Override
    public byte[] encode() throws UnsupportedEncodingException {
        byte[] superEncode = super.encodeInit();
        int superMsg = superEncode.length;
        int lenMsg = message.getBytes().length;
        byte[] buffer = new byte[lenMsg + superMsg];
        System.arraycopy(superEncode, 0, buffer, 0, superEncode.length);
        System.arraycopy(message.getBytes(), 0, buffer, superMsg, message.getBytes("UTF-8").length);
        return buffer;
    }

    @Override
    public void decode(byte[] buffer) throws UnsupportedEncodingException {
        super.decodeInit(buffer);
        message = new String(buffer, getStartMsgPos(), buffer.length - getStartMsgPos(), "UTF-8");
    }

    @Override
    public void process() {
        super.process();

        String strMsg = "";
        Message message=new Message();

        Bundle bundle=new Bundle();

        message.what = Main2Activity.SHOW_MSG;

        if (getMessage().equals("request")) {
            strMsg = getSendUser();
            bundle.putString("msg",strMsg);
            message.what = Main2Activity.FRIEND_REQUEST;
            message.setData(bundle);

        } else if (getMessage().equals("agree")) {
            strMsg = "[私聊-"+getSendUser()+"：]"+ "我们已经是朋友了！";
            bundle.putString("msg",strMsg);
            message.setData(bundle);

        } else if (getMessage().equals("reject")) {
            strMsg = "[私聊-"+getSendUser()+"：]"+ "我拒绝了你的好友申请！";
            bundle.putString("msg",strMsg);
            message.setData(bundle);
        }
        Main2Activity.getMainActivity().getMsghandler().sendMessage(message);

    }
}
