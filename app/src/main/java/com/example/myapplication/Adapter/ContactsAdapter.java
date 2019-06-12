package com.example.myapplication.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.DealAddFriend;
import com.example.myapplication.MailListInfoActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.UI.Contacts;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<Contacts> mList;
    private ViewHolder holderFirst;
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView redPoint;
        ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.contacts_name);
            redPoint = view.findViewById(R.id.red_point);
            imageView = view.findViewById(R.id.contacts_image);
        }
    }

    public ContactsAdapter(List<Contacts> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contacts_item2, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        if (holderFirst == null) {
            holderFirst = holder;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position == 0) {
                    holder.redPoint.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(MainActivity.mainActivity, DealAddFriend.class);
                    MainActivity.mainActivity.startActivity(intent);
                } else {

                    Contacts contacts = mList.get(position);
                    Intent intent = new Intent(MainActivity.mainActivity, MailListInfoActivity.class);
                    intent.putExtra("contacts_info", contacts);
                    MainActivity.mainActivity.startActivity(intent);
                }

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Contacts contacts = mList.get(i);
        viewHolder.textViewName.setText(contacts.getName());
        if (i == 0) {
            viewHolder.imageView.setVisibility(View.INVISIBLE);
            viewHolder.textViewName.setText("新朋友");
            if (MainActivity.mainActivity.redPoint) {
                setRedPoint();
                MainActivity.mainActivity.redPoint = false;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRedPoint() {
        holderFirst.redPoint.setVisibility(View.VISIBLE);
    }

}
