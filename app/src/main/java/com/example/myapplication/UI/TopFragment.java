package com.example.myapplication.UI;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

public class TopFragment extends Fragment {

    private TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_frag, container, false);
        textView = view.findViewById(R.id.top_text);
        textView.setText("微信");
        return view;
    }

    public void setText(String s) {
        textView.setText(s);
    }
}
