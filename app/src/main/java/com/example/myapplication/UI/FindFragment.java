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
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FindFragment extends Fragment {
    class FindsAdapter extends RecyclerView.Adapter<FindsAdapter.ViewHolder> {
        private List<Finds> mList;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textViewName;
            public ViewHolder(View view) {
                super(view);
                textViewName = view.findViewById(R.id.find_name);
            }
        }

        public FindsAdapter(List<Finds> list) {
            mList = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.find_item, viewGroup, false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Finds finds = mList.get(i);
            viewHolder.textViewName.setText(finds.getName());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.find_frag, container,false);

        RecyclerView recyclerView = view.findViewById(R.id.find_recycler_view);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        if (getFinds() == null) {
            Log.d("TAG", "view is null");
        }
        FindsAdapter findsAdapter = new FindsAdapter(getFinds());
        recyclerView.setAdapter(findsAdapter);
        return view;
    }

    public List<Finds> getFinds() {
        List<Finds> list = new ArrayList<>();
        Finds[] finds = new Finds[9];
        finds[0] = new Finds("朋友圈");
        finds[1] = new Finds("扫一扫");
        finds[2] = new Finds("摇一摇");
        finds[3] = new Finds("看一看");
        finds[4] = new Finds("搜一搜");
        finds[5] = new Finds("附近的人");
        finds[6] = new Finds("购物");
        finds[7] = new Finds("游戏");
        finds[8] = new Finds("小程序");
        for (int i = 0; i < 9; i++) {
            list.add(finds[i]);
        }
        return list;
    }
}
