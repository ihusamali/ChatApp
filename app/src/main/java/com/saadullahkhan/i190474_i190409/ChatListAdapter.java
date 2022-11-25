package com.saadullahkhan.i190474_i190409;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ContactListAdapter.MyViewHolder>{
    List<User> ls;
    Context c;
    public ChatListAdapter(List<User> ls, Context c ) {
        this.ls = ls;
        this.c = c;
    }
    @NonNull
    @Override
    public ContactListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(c).inflate(R.layout.row_chat_list,parent,false);
        return new ContactListAdapter.MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.MyViewHolder holder, int position) {
        holder.name.setText(ls.get(position).getName());
        holder.number.setText(ls.get(position).getNumber());
        holder.dp.setImageBitmap(ls.get(position).getDp());
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,number;
        ImageView dp;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameRowChat);
            number = itemView.findViewById(R.id.numberRowChat);
            dp = itemView.findViewById(R.id.rowChatDp);
        }

    }
}
