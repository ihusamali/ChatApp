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

public class SpecificChatAdapter extends RecyclerView.Adapter<ContactListAdapter.MyViewHolder>{
    List<Messages> ls;
    Context c;
    public SpecificChatAdapter(List<Messages> ls, Context c ) {
        this.ls = ls;
        this.c = c;
    }
    @NonNull
    @Override
    public ContactListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(c).inflate(R.layout.row_message_chat,parent,false);
        return new ContactListAdapter.MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.MyViewHolder holder, int position) {
        //holder.message.setText(ls.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //public ImageView mImageView;
        public TextView message;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //mImageView = itemView.findViewById(R.id.sideimage);
            message = itemView.findViewById(R.id.singlemessage);
        }

    }
}
