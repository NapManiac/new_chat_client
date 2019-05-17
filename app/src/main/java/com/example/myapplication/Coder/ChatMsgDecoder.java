package com.example.myapplication.Coder;



import com.example.myapplication.Entity.ChatMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @author gg_boy
 * 解码器，继承MessageToMessageDecoder并指定接受参数泛型为ByteBuf，重新decode方法，
 * 将字节流解码为一个ChatMessage对象。
 */
public class ChatMsgDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        final int length = byteBuf.readableBytes();  // 获得可以读取的字节数长度
        final byte[] array = new byte[length];
        // 从byteBuf这个缓冲区的readerIndex()位置开始读取length个长度的字节写入到byte类型的数组中
        byteBuf.getBytes(byteBuf.readerIndex(),array,0,length);
        // 利用MessagePack().read()方法重新将byte类型的数据转换成ChatMessage对象
        ChatMessage msg = new ChatMessage();
        msg.decode(array);
        list.add(msg);
    }
}