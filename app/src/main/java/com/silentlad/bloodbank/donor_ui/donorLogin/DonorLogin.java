package com.silentlad.bloodbank.donor_ui.donorLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.silentlad.bloodbank.R;
import com.silentlad.bloodbank.data.DonorLoginDataSource;
import com.silentlad.bloodbank.data.DonorLoginRepository;
import com.silentlad.bloodbank.data.Result;
import com.silentlad.bloodbank.donor_ui.DonorHomeScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DonorLogin extends AppCompatActivity {

    private DonorLoginViewModel donorLoginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_login);
        donorLoginViewModel = ViewModelProviders.of(this, new DonorLoginViewModelFactory()).get(DonorLoginViewModel.class);

        final EditText name_edit = findViewById(R.id.phone_edit);
        final EditText password_edit = findViewById(R.id.password_edit);
        final Button login_button = findViewById(R.id.login);
        final Button sign_up_button = findViewById(R.id.sign_up_button);


        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_fragment = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(register_fragment);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // navigate to donor screen
                DonorLoginRepository repo = new DonorLoginRepository(new DonorLoginDataSource());
                String name = name_edit.getText().toString().trim();
                String phone = password_edit.getText().toString().trim();

                if (!name.equals("") && !phone.equals("")) {
                    if (repo.login(name, phone, getApplicationContext()) instanceof Result.Success) {
                        donorLoginViewModel.login(name, phone, getApplicationContext());
                        Intent donor_home_intent = new Intent(getApplicationContext(), DonorHomeScreen.class);
                        startActivity(donor_home_intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Enter valid data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
