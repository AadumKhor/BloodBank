package com.silentlad.bloodbank.admin_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.silentlad.bloodbank.R;
import com.silentlad.bloodbank.data.databasehelper.DatabaseHelper_Hospitals;

public class ChangeHospitalInfo extends AppCompatActivity {

    private EditText hospital_edit;
    private EditText city_edit;
    private EditText working_days_edit;
    private EditText gmaps_edit;
    private EditText working_hours_edit;
    private Button changeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_change_hospital_info);
        initView();
        final DatabaseHelper_Hospitals db = new DatabaseHelper_Hospitals(this);
        hospital_edit.setText(getIntent().getStringExtra("name"));
        city_edit.setText(getIntent().getStringExtra("city"));
        String workingDays = getIntent().getStringExtra("startDay")+ "-" + getIntent().getStringExtra("endDay");
        working_days_edit.setText(workingDays);
        String workingHours = getIntent().getStringExtra("startTime")+ "-" + getIntent().getStringExtra("endTime");
        working_hours_edit.setText(workingHours);
        gmaps_edit.setText(getIntent().getStringExtra("gmaps"));

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getIntent().getStringExtra("id");
                String hospital = hospital_edit.getText().toString();
                String city = city_edit.getText().toString();
                String[] workingDays = working_days_edit.getText().toString().split("-");
                String[] workingHours = working_hours_edit.getText().toString().split("-");
                String gmaps = gmaps_edit.getText().toString();

                if(db.updateData(id,hospital, city, workingDays[0], workingDays[1], workingHours[0], workingHours[1], gmaps)){
                    Toast.makeText(getApplicationContext(), "Data has been updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Data has not been updated", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), AdminHomeScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        hospital_edit = findViewById(R.id.change_hospital);
        city_edit = findViewById(R.id.change_city);
        working_days_edit = findViewById(R.id.change_working_days);
        working_hours_edit = findViewById(R.id.change_working_hours);
        gmaps_edit = findViewById(R.id.change_gmaps);
        changeButton = findViewById(R.id.change_button);
    }
}
