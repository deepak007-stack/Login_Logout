package com.layout.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.PopupMenu;

public class Home extends AppCompatActivity {

    private AppCompatImageView more;
    private TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        more = (AppCompatImageView) findViewById(R.id.more);
        user = (TextView) findViewById(R.id.user);

        SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);

        String name = sharedPreferences.getString("key_name","");

        user.setText(name);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(Home.this, more);
                popupMenu.getMenuInflater().inflate(R.menu.option_menu_bar, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        switch (id) {
                            case R.id.logout:

                                editor.remove("key_name");
                                editor.remove("key_phone");
                                editor.remove("key_profession");
                                editor.commit();

                                Intent intent = new Intent(Home.this, Login.class);
                                startActivity(intent);
                                finish();

                                break;

                            case R.id.new_account:

                                editor.remove("key_name");
                                editor.remove("key_phone");
                                editor.remove("key_profession");
                                editor.commit();

                                Intent intent1 = new Intent(Home.this, Register.class);
                                startActivity(intent1);
                                finish();

                                break;

                        }

                        return true;
                    }
                });
            }
        });


    }

}