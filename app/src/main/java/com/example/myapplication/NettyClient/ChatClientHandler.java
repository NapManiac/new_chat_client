package com.example.myapplication.NettyClient;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.myapplication.Entity.ChatMessage;
import com.example.myapplication.Main2Activity;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 *
 */
public class ChatClientHandler extends ChannelInboundHandlerAdapter {
    private static final String TAG = "ChatClientHandler";
    /**
     * 在channel注册好后，激活channel，即将channel所对于的客户端与服务器进行连接
     * @param ctx ChannelHandlerContext类型
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChatMessage msg = new ChatMessage(Main2Activity.username, "server","login！" ,1);
        Log.e(TAG, "channelActive: "+ msg.toString());
        // 向服务器发送认证消息，通知服务器新加入了一个客户端
        ctx.channel().writeAndFlush(msg);
    }


    /**
     * 当服务器发送消息过来时，即channel读取到数据时
     * @param ctx
     * @param msg 读取到的消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String strmsg="";
        // 转换成我们定义的消息实体类ChatMessage对象
        ChatMessage cmsg = (ChatMessage)msg;
        if(cmsg.getReceiveUser().equals("")){//发给所有人
            strmsg="[全体-"+cmsg.getSendUser()+"：]"+cmsg.getMessage();
        }else{ //发给指定用户
            strmsg="[私聊-"+cmsg.getSendUser()+"：]"+cmsg.getMessage();
        }

        Message message=new Message();

        Bundle bundle=new Bundle();

        message.what = Main2Activity.SHOW_MSG;

        // 将Bundle对象放入message对象中，在MainActivity的Handler中通过msg这个键来获取对应的消息值
        // 刷新MainActivity界面中的显示消息的文本框
        if (cmsg.getMessagetype() == 2) {
            bundle.putString("msg",strmsg);
            message.setData(bundle);
            Main2Activity.getMainActivity().getMsghandler().sendMessage(message);
        } else if (cmsg.getMessagetype() == 3) {

            if (cmsg.getMessage().equals("request")) {
                strmsg = cmsg.getSendUser();
                Log.d("TAG", strmsg);
                bundle.putString("msg",strmsg);
                message.what = Main2Activity.FRIEND_REQUEST;
                message.setData(bundle);
                Main2Activity.getMainActivity().getMsghandler().sendMessage(message);

            } else if (cmsg.getMessage().equals("agree")) {
                strmsg = "[私聊-"+cmsg.getSendUser()+"：]"+ "我们已经是朋友了！";
                bundle.putString("msg",strmsg);
                message.setData(bundle);
                Main2Activity.getMainActivity().getMsghandler().sendMessage(message);
            } else if (cmsg.getMessage().equals("reject")) {
                strmsg = "[私聊-"+cmsg.getSendUser()+"：]"+ "我拒绝了你的好友申请！";
                bundle.putString("msg",strmsg);
                message.setData(bundle);
                Main2Activity.getMainActivity().getMsghandler().sendMessage(message);
            }
        }
    }

    /**
     * 捕获异常，关闭channel
     * @param ctx
     * @param cause 异常的类型
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 获得当前的channel
        Channel newchannel = ctx.channel();
        Log.e(TAG, "exceptionCaught: "+"["+newchannel.remoteAddress()+"]：通讯异常");
        Log.e(TAG, "exceptionCaught: "+cause.getMessage());
        newchannel.close();
    }
}