package com.example.myapplication.UI;

import java.io.Serializable;
import java.util.List;

public class Contacts implements Serializable {

    private String id = "";
    private String name = "";
    private String temp = "";//聊天的最后一句话

    private String motto;
    private int imageID;

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    private List<Msg> chatList;

    public List<Msg> getChatList() {
        return chatList;
    }

    public void setChatList(List<Msg> chatList) {
        this.chatList = chatList;
    }

    public Contacts() {

    }


    public Contacts(String id, String name, String motto) {
        this.id = id;
        this.name = name;
        this.motto = motto;
    }


    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        imageID = imageID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

}
