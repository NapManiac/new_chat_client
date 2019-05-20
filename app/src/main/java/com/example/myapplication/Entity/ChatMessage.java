package com.example.myapplication.Entity;

import android.util.Log;

import com.example.myapplication.Coder.Util;

import java.io.UnsupportedEncodingException;

/**
 * 消息实体类，协议
 */

public class ChatMessage {

    private String sendUser;

    private String receiveUser;

    private String message;

    private int messagetype;//1:初始化认证消息，2：聊天消息

    public ChatMessage() { // 空参构造
    }

    public ChatMessage(String sendUser, String receiveUser, String message, int messagetype){
        this.sendUser=sendUser;
        this.receiveUser=receiveUser;
        this.message=message;
        this.messagetype=messagetype;
    }

    public byte[] encode() throws UnsupportedEncodingException {
        int lenSendUesr = sendUser.getBytes().length;
        int lenReceiveUser = receiveUser.getBytes().length;
        int lenMsg = message.getBytes().length;
        int totalLength = lenMsg + lenReceiveUser + lenSendUesr + 4 * 4;

        byte[] buffer = new byte[totalLength];
        int offset = 0;

        byte[] messageTypeBuffer;
        messageTypeBuffer = Util.int2bytes(messagetype);
        System.arraycopy(messageTypeBuffer, 0, buffer, offset, 4);
        offset += 4;

        messageTypeBuffer = Util.int2bytes(lenSendUesr);
        System.arraycopy(messageTypeBuffer, 0, buffer, offset, 4);
        offset += 4;

        messageTypeBuffer = Util.int2bytes(lenReceiveUser);
        System.arraycopy(messageTypeBuffer, 0, buffer, offset, 4);
        offset += 4;

        messageTypeBuffer = Util.int2bytes(lenMsg);
        System.arraycopy(messageTypeBuffer, 0, buffer, offset, 4);
        offset += 4;

        System.arraycopy(sendUser.getBytes("UTF-8"), 0, buffer, offset, sendUser.getBytes("UTF-8").length);
        offset += sendUser.getBytes("UTF-8").length;

        System.arraycopy(receiveUser.getBytes("UTF-8"), 0, buffer, offset, receiveUser.getBytes("UTF-8").length);
        offset += receiveUser.getBytes("UTF-8").length;

        System.arraycopy(message.getBytes("UTF-8"), 0, buffer, offset, message.getBytes("UTF-8").length);
        offset += message.getBytes("UTF-8").length;

        return buffer;
    }

    public void decode(byte[] buffer) throws UnsupportedEncodingException {
        int offeset = 0;
        messagetype = Util.bytes2int(buffer, offeset);
        offeset += 4;

        int lenSendUesr = Util.bytes2int(buffer, offeset);
        offeset += 4;

        int lenReceiveUser = Util.bytes2int(buffer, offeset);
        offeset += 4;

        int lenMsg = Util.bytes2int(buffer, offeset);
        offeset += 4;
        System.out.println("类型：" + messagetype + " 发送人：" + lenSendUesr + " 接收者：" + lenReceiveUser + " 消息：" + lenMsg);

        sendUser = new String(buffer, offeset, lenSendUesr, "UTF-8");
        offeset += lenSendUesr;

        receiveUser = new String(buffer, offeset, lenReceiveUser, "UTF-8");
        offeset += lenReceiveUser;

        message = new String(buffer, offeset, lenMsg, "UTF-8");

    }

    /**
     * 获得消息的发送者
     * @return 返回消息的发送者
     */
    public String getSendUser() {
        return sendUser;
    }

    /**
     * 设置要发送消息的对象
     * @param sendUser 发送者
     */
    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    /**
     * 获得消息的接受者
     * @return 返回消息的接受者
     */
    public String getReceiveUser() {
        return receiveUser;
    }

    /**
     * 设置消息的接受者
     * @param receiveUser 接受者
     */
    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    /**
     * 得到消息内容
     * @return 消息的内容
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置要发送的消息内容
     * @param message 消息的内容
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获得发送消息的种类
     * @return 返回消息的种类
     */
    public int getMessagetype() {
        return messagetype;
    }

    /**
     * 设置发送消息的类型
     * 1:初始化认证消息，2：聊天消息
     * @param messagetype 消息的类型
     */
    public void setMessagetype(int messagetype) {
        this.messagetype = messagetype;
    }

}