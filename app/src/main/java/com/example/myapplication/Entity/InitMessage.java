package com.example.myapplication.Entity;



import com.example.myapplication.Coder.Util;

import java.io.UnsupportedEncodingException;



public class InitMessage extends Packet {

    private String message;

    public String getMessage() {
        return message;
    }

    public InitMessage() { // 空参构造
    }

    public InitMessage(String sendUser, String receiveUser, String message){
        super( Util.MSG_INIT, sendUser, receiveUser);
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

    }
}
