package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Base.BaseActivity;
import com.example.myapplication.NettyClient.User;
import com.example.myapplication.UI.Contacts;

import java.util.ArrayList;
import java.util.List;

public class DealAddFriend extends BaseActivity {


    public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

        private List<Contacts> mcontactsList;

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView contactsImage;
            TextView contactsName;

            public ViewHolder(View view) {
                super(view);
                contactsImage = view.findViewById(R.id.contacts_image);
                contactsName = view.findViewById(R.id.contacts_name);
            }
        }

        public ContactsAdapter(List<Contacts> contactsList) {
            mcontactsList = contactsList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contacts_item2, parent, false);
            final ViewHolder holder = new ViewHolder(view);

            holder.contactsName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Contacts contacts = mcontactsList.get(position);
                    Intent intent = new Intent(DealAddFriend.this, RequestListActivity.class);
                    intent.putExtra("contacts_info", contacts);
                    Log.d("dealaddfriend", contacts.getId());
                    startActivity(intent);
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Contacts contacts = mcontactsList.get(position);
            holder.contactsName.setText(contacts.getName());
        }

        @Override
        public int getItemCount() {
            return mcontactsList.size();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_add_friend);

        RecyclerView recyclerView =  findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ContactsAdapter adapter = new ContactsAdapter(getRequest());
        recyclerView.setAdapter(adapter);

    }

    public List<Contacts> getRequest() {
        List<Contacts> contactsList = new ArrayList<>();
        Contacts contacts;
        for (String string : User.USERINSTANCE.friendRequest) {
            contacts = User.USERINSTANCE.friendsInfo.get(string);
            contactsList.add(contacts);
        }
        return contactsList;
    }
}
