package com.silentlad.bloodbank.donor_ui.donorLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.silentlad.bloodbank.R;
import com.silentlad.bloodbank.data.Result;
import com.silentlad.bloodbank.data.databasehelper.DatabaseHelper_Users;

import java.util.Random;
import java.util.UUID;

public class SignUpActivity extends AppCompatActivity {
    private RadioGroup radio_group;
//    private RadioButton radio_sex_button;
    private EditText phone_edit;
    private EditText age_edit;
    private EditText name_edit;
    private EditText city_edit;
    private Spinner weight_dropdown;
    private Spinner bloodGroup_dropdown;

    private DatabaseHelper_Users db;

    private String[] weight_options = new String[]{"Below 50kg", "Above 50kg"};
    private String[] blood_groups = new String[]{"AB+", "AB-", "A+", "A-", "B+", "B-", "O+", "0-", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = new DatabaseHelper_Users(this);

        // BUTTONS
        Button sign_up_button = findViewById(R.id.sign_up_button);
        Button back_to_login = findViewById(R.id.confirm_button);

        // EDIT FIELDS

        name_edit = findViewById(R.id.name_edit);
        age_edit = findViewById(R.id.age_edit);
        city_edit = findViewById(R.id.city_edit);
        phone_edit = findViewById(R.id.phone_edit);

        // RADIO BUTTON GROUP
        radio_group = findViewById(R.id.radioGrp);

        // SPINNERS
        weight_dropdown = findViewById(R.id.spinner_weight);
        bloodGroup_dropdown = findViewById(R.id.blood_group_spinner);


        //access spinners
        dropDowns();

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validSignUp = signUp();
                if (validSignUp) {
                    Toast.makeText(getApplicationContext(), "Signed up!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DonorLogin.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Could not sign you up", Toast.LENGTH_SHORT).show();
                }
            }
        });
        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DonorLogin.class));
                finish();
            }
        });

    }

    private void dropDowns() {
        ArrayAdapter<String> weight_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, weight_options);
        ArrayAdapter<String> blood_group_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, blood_groups);
        weight_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blood_group_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight_dropdown.setAdapter(weight_adapter);
        bloodGroup_dropdown.setAdapter(blood_group_adapter);
    }

    private static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = 8;
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(57) + 65);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    private boolean signUp() {
        String id = java.util.UUID.randomUUID().toString().replace("-", "");
        String name = name_edit.getText().toString().trim();
        String city = city_edit.getText().toString().trim();
        String phone = phone_edit.getText().toString().trim();
        String age = age_edit.getText().toString().trim();
        String weight = weight_dropdown.getSelectedItem().toString().trim();
        String bloodGroup = bloodGroup_dropdown.getSelectedItem().toString().trim();
        String gender = addListenerOnButton();
        Result result = db.insertData(id, name, phone, gender, age, weight, bloodGroup, city);

        return result instanceof Result.Success;
    }

    public String addListenerOnButton() {
        int selectedId = radio_group.getCheckedRadioButtonId();

        if (selectedId == 1) {
            return "Male";
        } else {
            return "Female";
        }
    }
}
