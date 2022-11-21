package com.saadullahkhan.i190474_i190409;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {
    RecyclerView contactsRecycler;
    ChatListAdapter chatListAdapter;
    List<User> ls;
    List<String> numbers;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ls = new ArrayList<>();
        numbers = new ArrayList<>();
        GetNumber();
        contactsRecycler = getView().findViewById(R.id.contactsRecycler);
        for(int i=0 ; i< numbers.size();i++){
            ls.add(new User("name....", numbers.get(i), null));
        }

        chatListAdapter = new ChatListAdapter(ls, getActivity());
        contactsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactsRecycler.setAdapter(chatListAdapter);


    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact,container,false);
    }
    private void GetNumber() {
        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            int index = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String phoneNumber = phones.getString(index);
            phoneNumber =  phoneNumber.replace("+92","0");
            phoneNumber = "+92" + phoneNumber;
            phoneNumber = phoneNumber.replace("+920","+92");
            if(!numbers.contains(phoneNumber)){
                numbers.add(phoneNumber);
            }
        }
        phones.close();
    }



}