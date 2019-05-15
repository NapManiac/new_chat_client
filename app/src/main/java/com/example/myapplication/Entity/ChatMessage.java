package com.example.myapplication.Entity;

import org.msgpack.annotation.Index;
import org.msgpack.annotation.Message;

/**
 * 消息实体类，协议
 */
@Message  // 为了防止消息无法被序列化编码
public class ChatMessage {
    @Index(0)  // @Index()标签表明数据的顺序
    private String sendUser;
    @Index(1)
    private String receiveUser;
    @Index(2)
    private String message;
    @Index(3)
    private int messagetype;//1:初始化认证消息，2：聊天消息

    public ChatMessage() { // 空参构造
    }

    public ChatMessage(String sendUser, String receiveUser, String message, int messagetype){
        this.sendUser=sendUser;
        this.receiveUser=receiveUser;
        this.message=message;
        this.messagetype=messagetype;
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

    @Override
    public String toString() {
        return "ChatMessage{" +
                "sendUser='" + sendUser + '\'' +
                ", receiveUser='" + receiveUser + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}