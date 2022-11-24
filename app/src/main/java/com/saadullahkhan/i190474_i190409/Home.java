package com.saadullahkhan.i190474_i190409;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    ImageView drawer;
    TextView username;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNav;
    String id;
    ImageView dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNav = findViewById(R.id.bottomNavigationChat);
        drawer = findViewById(R.id.drawerButton);
        NavigationView navigationView = findViewById(R.id.nav);
        View header = navigationView.getHeaderView(0);
        username =  header.findViewById(R.id.usernameNav);
        dp =  header.findViewById(R.id.dpNav);
        drawerLayout = findViewById(R.id.drawerLayout);
        id = getIntent().getStringExtra("id");
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
                                Toast.makeText(getApplicationContext(),details.getString("name"),Toast.LENGTH_LONG).show();
                                username.setText(details.getString("name"));
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
                params.put("id",id);
                return params;
            }
        };
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerChat,new ContactFragment(),"Contacts").commit();
        bottomNav.getMenu().findItem(R.id.bcContact).setChecked(true);
        NavigationView navView = findViewById(R.id.nav);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.i1){
//                    Fragment fragment = new ProfileFragment();
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragmentContainer, fragment, "profile");
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
                }
                if(id == R.id.i2){
//                    Fragment fragment = new EditProfileFragment();
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragmentContainer, fragment, "editProfile");
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
                }
                if(id == R.id.i3){
                    finish();
                }
                return true ;
            }
        });
        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
    }
    public String getId(){
        return id;
    }
}