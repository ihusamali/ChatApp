package com.saadullahkhan.i190474_i190409;

import androidx.annotation.Nullable;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetPassword extends AppCompatActivity {
    TextView show ;
    TextView hide ;
    EditText password,number;
    TextView signIn;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        show = findViewById(R.id.showForgetPassword);
        hide = findViewById(R.id.hideForgetPassword);
        password = findViewById(R.id.passwordForgetPassword);
        number = findViewById(R.id.numberForgetPassword);
        update = findViewById(R.id.updateForgetPassword);
        signIn = findViewById(R.id.forgetPasswordSignIn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(number.getText())){
                    Toast.makeText(getApplicationContext(),"Input Invalid",Toast.LENGTH_LONG).show();
                }else {
                    StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.124/assignment_3/forget.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        if (obj.getInt("code") == 1) {
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Enter Correct Credentials", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Error Connecting Server", Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("phone_number", number.getText().toString());
                            params.put("password", password.getText().toString());
                            return params;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);
                }

            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
}