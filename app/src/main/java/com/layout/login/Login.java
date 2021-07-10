package com.layout.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private ImageView logo;
    private TextInputEditText userName;
    private TextInputEditText userPassword;
    private TextView forgotPassword;
    private AppCompatButton login;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        logo = (ImageView) findViewById(R.id.logo);
        userName = (TextInputEditText) findViewById(R.id.user_name);
        userPassword = (TextInputEditText) findViewById(R.id.user_password);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);
        login = (AppCompatButton) findViewById(R.id.login);
        signUp = (TextView) findViewById(R.id.sign_up);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Update.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uname = userName.getText().toString();
                String upass = userPassword.getText().toString();

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                String url = "http://192.168.196.93/login_andro/fetch_single.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                String data = jsonObject.getString("my_data");
                                JSONObject jsonObject1 = new JSONObject(data);
                                String name = jsonObject1.getString("name");
                                String phone = jsonObject1.getString("phone");
                                String profession = jsonObject1.getString("profession");

                                SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("key_name", name);
                                editor.putString("key_phone", phone);
                                editor.putString("key_profession", profession);
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Account does not match ", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Login.this, "error" + error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap hashMap = new HashMap();

                        hashMap.put("key_name", uname);
                        hashMap.put("key_pass", upass);

                        return hashMap;
                    }
                };

                requestQueue.add(stringRequest);
            }
        });


    }

}