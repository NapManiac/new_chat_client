package com.example.myapplication.NettyClient;

import com.example.myapplication.UI.Contacts;
import com.example.myapplication.UI.Msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class User {


    private String id = "";
    private String name = "";
    private String motto = "";

    public List<String> friendsId = new ArrayList<>();

    public Map<String, Contacts> friendsInfo = new HashMap<>();

    public List<String> friendRequest = new ArrayList<>();

    public Map<String, List<Msg>> chatList = new LinkedHashMap<>();


    public static User USERINSTANCE = new User();

    private User() {}

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

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }


}
