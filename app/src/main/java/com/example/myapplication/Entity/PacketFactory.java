package com.example.myapplication.Entity;

import android.util.Log;

import com.example.myapplication.Coder.PacketDecodeErrorException;
import com.example.myapplication.Coder.Util;


import java.io.UnsupportedEncodingException;

public class PacketFactory {
    public static Packet createPacketFromBuffer(byte[] buffer) throws UnsupportedEncodingException, PacketDecodeErrorException {
        int messageType = Util.bytes2int(buffer, 0);
        Packet packet = null;
        if (messageType == Util.MSG_INIT) {
            packet = new InitMessage();
        } else if (messageType == Util.MSG_CHAT) {
            packet = new ChatMessage();
        } else if (messageType == Util.MSG_ADDFRIENDS) {
            packet = new AddFriendsMessage();
        } else if (messageType == Util.MSG_REGISTER) {
            packet = new RegisterMessage();
        } else if (messageType == Util.MSG_SEARCH_FRIEND) {
            packet = new SearchMessage();
        } else if (messageType == Util.MSG_USER_INFO) {
            packet = new UserInfoMessage();
        } else if (messageType == Util.MSG_INIT_REQUEST_INFO) {
            packet = new InitRequestMessage();
        }

        else {
            throw new PacketDecodeErrorException("没有该子类");
        }
        Log.d("type", String.valueOf(messageType));
        packet.decode(buffer);
        return packet;
    }
}
