package com.saadullahkhan.i190474_i190409;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecificChat extends AppCompatActivity {
    TextView name;
    EditText message;
    ImageView dp,callButton,sendButton;
    SystemDataBase systemDataBase;
    List<Messages> messageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_chat);
        name = findViewById(R.id.nameChat);
        dp = findViewById(R.id.topDpChat);
        message = findViewById(R.id.messageBlockChat);
        sendButton = findViewById(R.id.sendButtonChat);
        callButton = findViewById(R.id.callButtonChat);
        messageList = new ArrayList<>();
        systemDataBase = new SystemDataBase(SpecificChat.this);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),CallActivity.class);
                intent.putExtra("id",getIntent().getStringExtra("id"));
                startActivity(intent);
            }
        });
        StringRequest request=new StringRequest(Request.Method.POST, "http://192.168.0.124/assignment_3/getdp.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj=new JSONObject(response);
                            if(obj.getInt("code")==1)
                            {
                                JSONArray arr=obj.getJSONArray("dp");
                                JSONObject details=arr.getJSONObject(0);
                                byte[] y = Base64.getDecoder().decode(details.getString("dp"));
                                dp.setImageBitmap(BitmapFactory.decodeByteArray(y, 0, y.length));
                                name.setText(details.getString("name"));
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Invalid ID",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error Connecting Server Because "+error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("id",getIntent().getStringExtra("id"));
                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!message.getText().toString().equals("")){
                    StringRequest request=new StringRequest(
                            Request.Method.POST,
                            "http://192.168.0.124/assignment_3/insertchat.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj=new JSONObject(response);
                                        if(obj.getInt("code")==1)
                                        {

                                            Toast.makeText(getApplicationContext(),"Chat Sent",Toast.LENGTH_LONG).show();
                                            message.setText("");
                                        }
                                        else{
                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    obj.get("msg").toString()
                                                    ,Toast.LENGTH_LONG
                                            ).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                        Toast.makeText(
                                                getApplicationContext(),
                                                "Incorrect JSON "+e
                                                ,Toast.LENGTH_LONG
                                        ).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "Cannot Connect to the Server."+"\n"+error.toString()
                                            ,Toast.LENGTH_LONG
                                    ).show();
                                }
                            })
                    {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params=new HashMap<>();
                            params.put("id1",systemDataBase.getId());
                            params.put("id2",getIntent().getStringExtra("id"));
                            params.put("message",message.getText().toString());
                            return params;
                        }
                    };
                    RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);

                }
            }
        });

    }
    void getMessageData()
    {

        // Initiating Request to Check
        String url = "http://192.168.0.124/assignment_3/getchat.php";
        StringRequest jsonObjReq = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject obj=new JSONObject(response);
                            if (obj.getInt("code") == 1)
                            {
                                // Successfully Retrieved Data
                                JSONArray jsonData = obj.getJSONArray("messages");
                                if (jsonData != null)
                                {
                                    for (int i = 0; i < jsonData.length(); i++)
                                    {
                                        messageList.add( new Messages( jsonData.getJSONArray(i).getString(0),Integer.parseInt(jsonData.getJSONArray(i).getString(1))));
                                    }

//                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                            else
                            {
                                // Wrong Credentials
                                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();


                            Toast.makeText(
                                    getApplicationContext(),
                                    e.getMessage()
                                    ,Toast.LENGTH_LONG
                            ).show();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Cannot Connect to the Server."
                                ,Toast.LENGTH_LONG
                        ).show();
                    }
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams()throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("id1", systemDataBase.getId());
                params.put("id2", getIntent().getStringExtra("id"));
                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjReq);

    }
}