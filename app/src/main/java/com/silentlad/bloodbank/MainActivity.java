package com.silentlad.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.silentlad.bloodbank.donor_ui.donorLogin.DonorLogin;
import com.silentlad.bloodbank.admin_ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private Button admin;
    private Button donor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        admin = (Button) findViewById(R.id.admin);
        donor = (Button) findViewById(R.id.donor);

        admin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent admin_login_intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(admin_login_intent);
                    }
                }
        );
        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donor_login_intent = new Intent(getApplicationContext(), DonorLogin.class);
                startActivity(donor_login_intent);
            }
        });
    }
}
