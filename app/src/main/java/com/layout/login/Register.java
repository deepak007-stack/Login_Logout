package com.layout.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Register extends AppCompatActivity {

    private AutoCompleteTextView filledExposedDropdown;
    private TextInputEditText name;
    private TextInputEditText email;
    private TextInputEditText phone;
    private TextInputEditText password;
    private AutoCompleteTextView customerTextView;
    private AppCompatButton submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (TextInputEditText) findViewById(R.id.name);
        email = (TextInputEditText) findViewById(R.id.email);
        phone = (TextInputEditText) findViewById(R.id.phone);
        password = (TextInputEditText) findViewById(R.id.password);
        customerTextView = (AutoCompleteTextView) findViewById(R.id.customerTextView);
        submitButton = (AppCompatButton) findViewById(R.id.submitButton);
        initUI();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uname = name.getText().toString().trim();
                String uemail = email.getText().toString().trim();
                String uphone = phone.getText().toString().trim();
                String upassword = password.getText().toString().trim();
                String ucustomerTextView = customerTextView.getText().toString().trim();

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                String url = "http://192.168.196.93/login_andro/insert.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String result = jsonObject.getString("resultx");

                            if (result.equals("1")) {
                                Toasty.success(getApplicationContext(), "Register Successful ", Toast.LENGTH_SHORT, true).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toasty.error(getApplicationContext(), "Account already exists !", Toast.LENGTH_SHORT, true).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Register.this, "error" + error, Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap hashMap = new HashMap();

                        hashMap.put("key_uname", uname);
                        hashMap.put("key_uemail", uemail);
                        hashMap.put("key_uphone", uphone);
                        hashMap.put("key_upassword", upassword);
                        hashMap.put("key_ucustomerTextView", ucustomerTextView);

                        return hashMap;
                    }
                };

                requestQueue.add(stringRequest);

            }
        });

    }

    private void initUI() {
        //UI reference of textView
        final AutoCompleteTextView customerAutoTV = findViewById(R.id.customerTextView);

        // create list of customer
        ArrayList<String> customerList = getCustomerList();

        //Create adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Register.this, android.R.layout.simple_spinner_item, customerList);

        //Set adapter
        customerAutoTV.setAdapter(adapter);

        //submit button click event registration
        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Register.this, customerAutoTV.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<String> getCustomerList() {
        ArrayList<String> customers = new ArrayList<>();
        customers.add("Android developer");
        customers.add("Android designer");
        customers.add("Web developer");
        customers.add("Web designer");
        customers.add("Software developer");

        return customers;
    }


}