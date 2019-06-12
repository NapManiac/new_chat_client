package com.example.myapplication.Entity;

import com.example.myapplication.Coder.Util;

import java.io.UnsupportedEncodingException;

public class InitRequestMessage extends Packet {


    public InitRequestMessage(String send, String receive) {
        super(Util.MSG_INIT_REQUEST_INFO, send, receive);
    }

    public InitRequestMessage() {
        
    }

    @Override
    public byte[] encode() throws UnsupportedEncodingException {
        return super.encodeInit();
    }

    @Override
    public void decode(byte[] buffer) throws UnsupportedEncodingException {
        super.decodeInit(buffer);
    }

    @Override
    public void process() {
        super.process();
    }
}
