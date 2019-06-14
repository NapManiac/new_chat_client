package com.example.myapplication.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapter.ChatAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.NettyClient.User;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatFragment extends Fragment {
    private ChatAdapter chatAdapter;
    private List<Contacts> mList;
    private RecyclerView recyclerView;
    public static ChatFragment chatFragment;

    public ChatFragment() {
        chatFragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        MainActivity.houtai += 1;

        View view =inflater.inflate(R.layout.chat_frag, container,false);
        mList = getContactsList();
        recyclerView = view.findViewById(R.id.chat_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        chatAdapter = new ChatAdapter(mList);
        recyclerView.setAdapter(chatAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.houtai -= 1;
    }

    public List<Contacts> getContactsList() {
        List<Contacts> contactsList = new ArrayList<>();
        for (String string : User.USERINSTANCE.chatList.keySet()) {
            contactsList.add(User.USERINSTANCE.friendsInfo.get(string));
            Log.d("addAdapter", string);
        }
        Collections.reverse(contactsList);
        return contactsList;
    }

    public void addAdapter(Contacts contacts) {
        if (mList.contains(contacts)) {
            chatAdapter.notifyItemMoved(mList.indexOf(contacts), 0);
            chatAdapter.notifyItemChanged(0);
            mList.remove(contacts);
            mList.add(0, contacts);

        } else {
            Log.d("addAdapter", contacts.getName());
            mList.add(0, contacts);
            chatAdapter.notifyItemInserted(0);
            recyclerView.scrollToPosition(0);
            chatAdapter.notifyItemChanged(0);
        }

    }

    public boolean setRedPoint() {
        if (chatAdapter != null) {
            chatAdapter.setRedPoint();
            return true;
        }
        return false;
    }

    public void cancelRedPoint() {
        if (chatAdapter != null) {
            chatAdapter.cancelRedPoint();
        }
    }

}
