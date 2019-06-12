package com.example.myapplication.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class BottomFragment extends Fragment implements View.OnClickListener{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_frag, container, false);
        Button buttonChat = view.findViewById(R.id.btn_chat);
        Button buttonMailList = view.findViewById(R.id.btn_mail_list);
        Button buttonFind = view.findViewById(R.id.btn_find);
        Button buttonMy = view.findViewById(R.id.btn_my);
        buttonChat.setOnClickListener(this);
        buttonMailList.setOnClickListener(this);
        buttonFind.setOnClickListener(this);
        buttonMy.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        MainActivity activity = (MainActivity) getActivity();

        switch (v.getId()) {
            case R.id.btn_chat: {
                activity.replaceFragment(MainActivity.chatFragment, 1);
                break;
            }
            case R.id.btn_mail_list: {
                activity.replaceFragment(MainActivity.mailListFragment, 2);
                break;
            }
            case R.id.btn_find: {
                activity.replaceFragment(MainActivity.findFragment, 3);
                break;
            }
            case R.id.btn_my: {
                activity.replaceFragment(MainActivity.myFragment, 4);
                break;
            }
        }
    }
}
