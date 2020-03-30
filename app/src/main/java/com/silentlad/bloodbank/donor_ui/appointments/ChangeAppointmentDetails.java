package com.silentlad.bloodbank.donor_ui.appointments;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.silentlad.bloodbank.R;
import com.silentlad.bloodbank.data.Result;
import com.silentlad.bloodbank.data.databasehelper.DatabaseHelper_Appointments;
import com.silentlad.bloodbank.donor_ui.FixAppointment;

public class ChangeAppointmentDetails extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private TextView datePickText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_activity_change_appointment_details);
        final DatabaseHelper_Appointments db = new DatabaseHelper_Appointments(this);
        final String id = getIntent().getStringExtra("id");
        final String hospitalName = getIntent().getStringExtra("name");
        String city = getIntent().getStringExtra("city");
        String date = getIntent().getStringExtra("date");
        String startTime = getIntent().getStringExtra("time");
        int imageR = getIntent().getIntExtra("image", 0);

        TextView tHospitalName = findViewById(R.id.change_app_hospital_name);
        TextView tCity = findViewById(R.id.change_app_city);

        datePickText = findViewById(R.id.change_app_s1);
        datePickText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calender = Calendar.getInstance();
                int year = calender.get(Calendar.YEAR);
                int month = calender.get(Calendar.MONTH);
                int day = calender.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ChangeAppointmentDetails.this,
                        android.R.style.Theme_Material_Dialog_MinWidth,
                        onDateSetListener, year, month, day
                );

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        datePickText.setText(date);
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                datePickText.setText(date);
            }
        };

        final Spinner tStartTime = findViewById(R.id.change_app_s2);
        createDropDownList2(tStartTime);
        ImageView tImageR = findViewById(R.id.change_app_imageView);

        Button confirmButton = findViewById(R.id.change_app_button);

        tHospitalName.setText(hospitalName);
        tCity.setText(city);
        tImageR.setImageResource(imageR);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTime = tStartTime.getSelectedItem().toString();
                String newDate = datePickText.getText().toString();

                boolean isDataValid = !newTime.equals("") && !newDate.equals("");
                boolean updateData = db.updateData(id, newDate, newTime) instanceof Result.Success;
                if (isDataValid && updateData) {
                    Toast.makeText(getApplicationContext(), "Appointment updated.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Appointment not updated.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void createDropDownList2(Spinner dropDown_time) {
        // add a list of items to it for mock
        String[] mock_items = new String[]{"11:00", "12:00", "13:00", "14:00", "15:00"};
        // basic adapter to display items
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mock_items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDown_time.setAdapter(adapter);
    }
}
