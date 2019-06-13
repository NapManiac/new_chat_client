package com.example.myapplication.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.ChatWithOthersActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.UI.Contacts;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<Contacts> mList;
    public ViewHolder holderFirst;
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewTemp;
        ImageView redPoint;
        public ViewHolder(View view) {
            super(view);
            redPoint = view.findViewById(R.id.red_point);
            textViewName = view.findViewById(R.id.contacts_name);
            textViewTemp = view.findViewById(R.id.contacts_temp);
        }
    }

    public ChatAdapter(List<Contacts> list) {
        mList = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contacts_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        if (holderFirst == null) {
            holderFirst = holder;
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                holder.redPoint.setVisibility(View.INVISIBLE);
                MainActivity.mainActivity.redPoint2 = false;
                Contacts contacts = mList.get(position);
                Intent intent = new Intent(MainActivity.mainActivity, ChatWithOthersActivity.class);
                intent.putExtra("id", contacts.getId());
                MainActivity.mainActivity.startActivity(intent);
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Contacts contacts = mList.get(i);
        viewHolder.textViewName.setText(contacts.getName());
        viewHolder.textViewTemp.setText(contacts.getTemp());
        if (MainActivity.mainActivity.redPoint2) {
            viewHolder.redPoint.setVisibility(View.VISIBLE);

        }

    }




    @Override
    public int getItemCount() {
        return mList.size();
    }

    public boolean setRedPoint() {
        if (holderFirst != null) {
            holderFirst.redPoint.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }


}
