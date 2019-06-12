package com.example.myapplication.Entity;

import android.os.Bundle;
import android.os.Message;

import com.example.myapplication.Coder.Util;
import com.example.myapplication.MainActivity;
import com.example.myapplication.NettyClient.User;

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

        Message message = new Message();
        Bundle bundle = new Bundle();
        //如果是申请的消息
        if (getMessage().equals("request")) {
//            message.what = MainActivity.ADD_REQUEST;
//            bundle.putString("id", getSendUser());
//            message.setData(bundle);
//            MainActivity.mainActivity.getMsghandler().sendMessage(message);
//            User.USERINSTANCE.friendRequest.add(getSendUser());
//            Log.d("request", getSendUser());

        } else if (getMessage().equals("agree")) {
            message.what = MainActivity.ADD_FRIEND;
            bundle.putString("id", getSendUser());
            message.setData(bundle);
            if (!User.USERINSTANCE.friendsId.contains(getSendUser())) {

                User.USERINSTANCE.friendsId.add(getSendUser());
                MainActivity.mainActivity.getMsghandler().sendMessage(message);
            }
        }

    }
}
