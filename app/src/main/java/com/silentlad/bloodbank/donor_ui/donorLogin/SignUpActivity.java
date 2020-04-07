package com.silentlad.bloodbank.donor_ui.donorLogin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.silentlad.bloodbank.R;
import com.silentlad.bloodbank.data.Result;
import com.silentlad.bloodbank.data.databasehelper.DatabaseHelper_Users;
import com.silentlad.bloodbank.donor_ui.FixAppointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public class SignUpActivity extends AppCompatActivity {
    private RadioGroup radio_group;
    //    private RadioButton radio_sex_button;
    private EditText pwd_edit;
    private TextView age_edit;
    private EditText phone_edit;
    private EditText city_edit;
    private Spinner weight_dropdown;
    private Spinner bloodGroup_dropdown;

    private DatabaseHelper_Users db;

    private String[] weight_options = new String[]{"Below 50kg", "Above 50kg"};
    private String[] blood_groups = new String[]{"AB+", "AB-", "A+", "A-", "B+", "B-", "O+", "0-", "Other"};

    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = new DatabaseHelper_Users(this);
        age_edit = findViewById(R.id.age_edit);
        age_edit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calender = Calendar.getInstance();
                int year = calender.get(Calendar.YEAR);
                int month = calender.get(Calendar.MONTH);
                int day = calender.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SignUpActivity.this,
                        android.R.style.Theme_Material_Dialog_MinWidth,
                        onDateSetListener, year, month, day
                );

                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Calendar dob = Calendar.getInstance();
                Calendar now = Calendar.getInstance();

                dob.set(year, month, dayOfMonth);

                int age = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

                if (now.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                    age--;
                }
                age_edit.setText(String.valueOf(age));
            }
        };


        // BUTTONS
        Button sign_up_button = findViewById(R.id.sign_up_button);
        Button back_to_login = findViewById(R.id.confirm_button);

        // EDIT FIELDS

        phone_edit = findViewById(R.id.phone_edit);
        city_edit = findViewById(R.id.city_edit);
        pwd_edit = findViewById(R.id.password_edit);

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
        String name = phone_edit.getText().toString().trim();
        String city = city_edit.getText().toString().trim();
        String phone = pwd_edit.getText().toString().trim();
        String age = age_edit.getText().toString().trim();
        String weight = weight_dropdown.getSelectedItem().toString().trim();
        String bloodGroup = bloodGroup_dropdown.getSelectedItem().toString().trim();
        String gender = addListenerOnButton();

        if (weight.equals("Below 50kg")) {
            Toast.makeText(getApplicationContext(), "Sorry we cannot sign you up!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Result result = db.insertData(id, name, phone, gender, age, weight, bloodGroup, city);

            return result instanceof Result.Success;
        }
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
