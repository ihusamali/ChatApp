package com.saadullahkhan.i190474_i190409;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {
    TextView show ;
    TextView hide ;
    EditText password,name,number;
    TextView signIn;
    Button signUp;
//    byte[] bytes;
    Bitmap bitmap;
    ImageView dp;
    Uri dpp;
    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result != null && result.getResultCode()== RESULT_OK){
                if(result.getData()!=null) {
                    dpp = result.getData().getData();
                    dp.setImageURI(dpp);
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        show = findViewById(R.id.showCreateAccount);
        hide = findViewById(R.id.hideCreateAccount);
        password = findViewById(R.id.passwordCreateAccount);
        name = findViewById(R.id.nameSignUp);
        number = findViewById(R.id.numberSignUp);
        signIn=findViewById(R.id.signInSignUp);
        dp = findViewById(R.id.dpSignUp);
        signUp = findViewById(R.id.signUpButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.setVisibility(View.INVISIBLE);
                hide.setVisibility(View.VISIBLE);
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dpp==null){
                    Toast.makeText(getApplicationContext(),"Please Select Image",Toast.LENGTH_LONG).show();
                }else if(!name.getText().toString().equals("") && !number.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), dpp);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
//                        bytes = stream.toByteArray();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    StringRequest request=new StringRequest(
                            Request.Method.POST,
                            "http://192.168.0.101/assignment_3/insert.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj=new JSONObject(response);
                                        if(obj.getInt("code")==1)
                                        {
                                            startActivity(new Intent(CreateAccount.this, Home.class));
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
                                                "Incorrect JSON"
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
                            params.put("name",name.getText().toString());
                            params.put("phone_number",number.getText().toString());
                            params.put("password",password.getText().toString());
                            params.put("dp",bitmap.toString());
                            return params;
                        }
                    };
                    RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);

                }else{
                    Toast.makeText(getApplicationContext(),"Please Enter All Details",Toast.LENGTH_LONG).show();
                }

            }
        });
        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startForResult.launch(Intent.createChooser(i,"Pick your DP"));
            }
        });
    }
}