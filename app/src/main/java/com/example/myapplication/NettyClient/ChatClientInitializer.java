package com.example.myapplication.NettyClient;

import com.example.myapplication.Coder.ChatMsgDecoder;
import com.example.myapplication.Coder.ChatMsgEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * 对于channel中pipeline进行一些初始化，在初始化完成后会将自身移除
 */
public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //建立管道 在初始化完成后,ChatClientInitializer会从channel的pipeline中移除
        ChannelPipeline pipeline = channel.pipeline();
        // 在使用解码器对数据进行解码时，会判断这个数据包的长度是否超过65536，如果超过则会丢弃该数据包
        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65536, 0, 2, 0, 2));
        pipeline.addLast("msgpack decoder",new ChatMsgDecoder()); // 解码器
        pipeline.addLast("frameEncoder", new LengthFieldPrepender(2));
        pipeline.addLast("msgpack encoder",new ChatMsgEncoder()); // 编码器
        // 实际上建立客户端与服务器连接的步骤是在ChatClientHandler中完成的
        // 即在ChatClientHandler中完成业务处理
        pipeline.addLast("handler",new ChatClientHandler());

    }
}