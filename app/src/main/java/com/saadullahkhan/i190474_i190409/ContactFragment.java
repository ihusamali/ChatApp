package com.saadullahkhan.i190474_i190409;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView contactsRecycler;
    ChatListAdapter chatListAdapter;
    List<User> ls;
    List<User> contactsDataBase;
    List<String> numbers;
    int contactPermissionCode;
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result) {
                        GetNumber();
                        Toast.makeText(getActivity(),"Permission Granted",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(),"Permission not Granted",Toast.LENGTH_LONG).show();
                    }
                }
            }
    );
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ls = new ArrayList<>();
        numbers = new ArrayList<>();
        contactsDataBase = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED) {
            GetNumber();
        }else{
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS);
        }
        StringRequest request=new StringRequest(Request.Method.GET, "http://192.168.0.124/assignment_3/getphone.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj=new JSONObject(response);
                            if(obj.getInt("code")==1)
                            {
                                JSONArray contacts=obj.getJSONArray("contacts");
                                for (int i=0; i<contacts.length();i++)
                                {
                                    JSONObject contact=contacts.getJSONObject(i);
                                    Home activity = (Home) getActivity();
                                    if(!activity.getId().equals(contact.getString("id"))){
                                    contactsDataBase.add(new User(contact.getString("name"),contact.getString("phone_number"),null,null,contact.getString("id")));
                                    if(numbers.contains(contact.getString("phone_number"))) {
                                        ls.add(new User(contact.getString("name"), contact.getString("phone_number"), null, null, contact.getString("id")));
                                    }
                                    }
                                }

                            }
                            else{
                                Toast.makeText(getContext(),obj.get("msg").toString(),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"Error Connecting Server",Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);
        contactsRecycler = getView().findViewById(R.id.contactsRecycler);
        chatListAdapter = new ChatListAdapter(ls, getActivity());
        contactsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactsRecycler.setAdapter(chatListAdapter);
        swipeRefreshLayout = getView().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                for(int i=0 ; i< ls.size();i++){
                    ls.remove(i);
                }
                for(int i=0 ; i< contactsDataBase.size();i++){
                    Home activity = (Home) getActivity();
                    if(numbers.contains(contactsDataBase.get(i).getNumber()) && !activity.getId().equals(contactsDataBase.get(i).getId())) {
                        ls.add(new User(contactsDataBase.get(i).getName(), contactsDataBase.get(i).getNumber(), null, null,contactsDataBase.get(i).getId()));
                    }
                }
                chatListAdapter.notifyItemChanged(ls.size()-1);
                swipeRefreshLayout.setRefreshing(false);
            }
        });


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
            phoneNumber =  phoneNumber.replace(" ","");
            phoneNumber =  phoneNumber.replace("-","");
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