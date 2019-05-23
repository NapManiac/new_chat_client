package com.example.myapplication.Entity;

import com.example.myapplication.Coder.PacketDecodeErrorException;
import com.example.myapplication.Coder.Util;


import java.io.UnsupportedEncodingException;

public class PacketFactory {
    public static Packet createPacketFromBuffer(byte[] buffer) throws UnsupportedEncodingException, PacketDecodeErrorException {
        int messageType = Util.bytes2int(buffer, 0);
        Packet packet = null;
        if (messageType == Util.MSG_INIT) {
            packet = new InitMessage();
            packet.decode(buffer);
        } else if (messageType == Util.MSG_CHAT) {
            packet = new ChatMessage();
            packet.decode(buffer);
        } else if (messageType == Util.MSG_ADDFRIENDS) {
            packet = new AddFriendsMessage();
            packet.decode(buffer);
        } else {
            throw new PacketDecodeErrorException("没有该子类");
        }
        return packet;
    }
}
