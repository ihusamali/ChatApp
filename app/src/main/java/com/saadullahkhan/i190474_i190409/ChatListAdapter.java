package com.saadullahkhan.i190474_i190409;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder>{
    List<User> ls;
    Context c;
    public ChatListAdapter(List<User> ls, Context c ) {
        this.ls = ls;
        this.c = c;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(c).inflate(R.layout.row_chat_list,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(ls.get(position).getName());
        holder.number.setText(ls.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,number;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameRowChat);
            number = itemView.findViewById(R.id.numberRowChat);
        }

    }
}
