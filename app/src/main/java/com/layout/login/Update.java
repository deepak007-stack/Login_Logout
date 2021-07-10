package com.layout.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {

    private TextInputEditText userName;
    private TextInputEditText userPassword;
    private AppCompatButton changePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        userName = (TextInputEditText) findViewById(R.id.user_name);
        userPassword = (TextInputEditText) findViewById(R.id.user_password);
        changePass = (AppCompatButton) findViewById(R.id.change_pass);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = userName.getText().toString();
                String password = userPassword.getText().toString();

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                String url ="http://192.168.196.93/login_andro/update.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(Update.this, response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Update.this, "error"+error, Toast.LENGTH_SHORT).show();

                    }
                })
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap hashMap =new HashMap();

                        hashMap.put("key_name",name);
                        hashMap.put("key_password",password);

                        return hashMap;
                    }
                };

                requestQueue.add(stringRequest);
            }
        });

    }
}