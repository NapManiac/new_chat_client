package com.example.myapplication.Entity;

import android.os.Message;

import com.example.myapplication.AddFriendActivity;
import com.example.myapplication.Coder.Util;

import java.io.UnsupportedEncodingException;

public class SearchMessage extends Packet {

    private int searchType;

    public SearchMessage(String sendUser, String receiveUser, int searchType){
        super(Util.MSG_SEARCH_FRIEND, sendUser, receiveUser);//接收者是搜索对象，message内容是找群还是找人
        this.searchType = searchType;
    }

    public SearchMessage() {
        setPacketType(Util.MSG_SEARCH_FRIEND);
    }

    @Override
    public byte[] encode() throws UnsupportedEncodingException {

        byte[] superBuffer =  super.encodeInit();

        int lenSuper = superBuffer.length;

        byte[] buffer = new byte[lenSuper + 4];

        System.arraycopy(superBuffer, 0, buffer, 0, superBuffer.length);

        System.arraycopy(Util.int2bytes(searchType), 0, buffer, superBuffer.length, 4);
        return buffer;
    }


    @Override
    public void decode(byte[] buffer) throws UnsupportedEncodingException {
        super.decodeInit(buffer);
        searchType = Util.bytes2int(buffer, getStartMsgPos());
    }

    @Override
    public void process() {
        Message message=new Message();
        message.what = Util.SEARCH_NO_FIND;
        AddFriendActivity.addFriendActivity.getHandler().sendMessage(message);
    }

    public int getSearchType() {
        return searchType;
    }
}
