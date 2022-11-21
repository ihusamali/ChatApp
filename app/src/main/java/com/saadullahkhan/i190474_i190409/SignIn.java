package com.saadullahkhan.i190474_i190409;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignIn extends AppCompatActivity {
    TextView show ;
    TextView hide ;
    EditText password,number;
    TextView signUp;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        show = findViewById(R.id.showSignIn);
        hide = findViewById(R.id.hideSignIn);
        password = findViewById(R.id.passwordSignIn);
        number = findViewById(R.id.numberSignIn);
        signUp = findViewById(R.id.signUpSignIn);
        signIn = findViewById(R.id.signInButton);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this, CreateAccount.class));
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(number.getText())){
                    Toast.makeText(getApplicationContext(),"Input Invalid",Toast.LENGTH_LONG).show();

                }else{
                    RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest request=new StringRequest(Request.Method.GET, "http://192.168.0.101/assignment_3/get.php",
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
                                                String id = contact.getString("id");
                                                Intent intent = new Intent(SignIn.this,Home.class);
                                                intent.putExtra("id",id);
                                                startActivity(intent);
                                            }

                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"Enter Correct Credentials",Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(),"Error Connecting Server",Toast.LENGTH_LONG).show();
                                }
                            });

                    queue.add(request);




                }
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.setVisibility(View.INVISIBLE);
                hide.setVisibility(View.VISIBLE);
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.setVisibility(View.VISIBLE);
                hide.setVisibility(View.INVISIBLE);
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

    }
}