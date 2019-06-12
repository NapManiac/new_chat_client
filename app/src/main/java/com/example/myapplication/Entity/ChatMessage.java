package com.example.myapplication.Entity;

import android.os.Bundle;
import android.os.Message;

import com.example.myapplication.Coder.Util;
import com.example.myapplication.MainActivity;

import java.io.UnsupportedEncodingException;



/**
 * 消息实体类，协议
 */

public class ChatMessage extends Packet{

    private String message;


    public ChatMessage() { // 空参构造
    }

    public ChatMessage(String sendUser, String receiveUser, String message){
        super(Util.MSG_CHAT, sendUser, receiveUser);
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

    public String getMessage() {
        return message;
    }

    @Override
    public void process() {
        String id = getSendUser();
        String msg = getMessage();

        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("sendId", id);
        bundle.putString("msg", msg);
        message.setData(bundle);
        message.what = MainActivity.SHOW_CHAT;
        MainActivity.mainActivity.getMsghandler().sendMessage(message);
    }
}