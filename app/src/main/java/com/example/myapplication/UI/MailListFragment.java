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

import com.example.myapplication.Adapter.ContactsAdapter;
import com.example.myapplication.NettyClient.User;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MailListFragment extends Fragment {


    private ContactsAdapter adapter;
    public static MailListFragment mailListFragment;

    private List<Contacts> mList;

    private RecyclerView recyclerView;

    public List<Contacts> getmList() {
        return mList;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.mail_list_frag, container,false);
        mList = getContactsList();
        recyclerView = view.findViewById(R.id.mail_list_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ContactsAdapter(mList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public List<Contacts> getContactsList() {
        List<Contacts> contactsList = new ArrayList<>();
        Contacts contacts = new Contacts("", "新朋友", "");
        contactsList.add(contacts);
        for (String string : User.USERINSTANCE.friendsId) {
            contactsList.add(User.USERINSTANCE.friendsInfo.get(string));
            Log.d("getContactsList", string + " s");
        }
        return contactsList;
    }

    public MailListFragment() {
        mailListFragment = this;

    }

    public void addAdapter(Contacts contacts) {
        mList.add(contacts);
        adapter.notifyItemInserted(mList.size() - 1);
        recyclerView.scrollToPosition(mList.size() - 1);
    }

    public boolean setRedPoint() {
        if (adapter != null) {
            adapter.setRedPoint();
            return true;
        }
        return false;
    }


}
