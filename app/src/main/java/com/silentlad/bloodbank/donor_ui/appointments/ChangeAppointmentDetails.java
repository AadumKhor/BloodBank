package com.silentlad.bloodbank.donor_ui.appointments;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class ChangeAppointmentDetails extends AppCompatActivity {
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private TextView datePickText;
    private Spinner tStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_activity_change_appointment_details);
        final DatabaseHelper_Appointments db = new DatabaseHelper_Appointments(this);
        final String id = getIntent().getStringExtra("id");
        final String hospitalName = getIntent().getStringExtra("name");
        String city = getIntent().getStringExtra("city");
        String date = getIntent().getStringExtra("date");
        String setTime = getIntent().getStringExtra("setTime");
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

        tStartTime = findViewById(R.id.change_app_s2);
        try {
            createDropDownList2();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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


    private void createDropDownList2() throws ParseException {
        String startTime = getIntent().getStringExtra("startTime");
        String endTime = getIntent().getStringExtra("endTime");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        assert startTime != null;
        assert endTime != null;
        int difference = Integer.parseInt(endTime) - Integer.parseInt(startTime);
        if(!startTime.contains(":00:00")) {
            startTime = startTime.concat(":00:00");
        }

        if(!endTime.contains(":00:00")) {
            endTime = endTime.concat(":00:00");
        }
        Log.println(Log.DEBUG, "time", "new "+startTime);
        String sTime = Objects.requireNonNull(format.parse(startTime)).toString();
        String eTime = Objects.requireNonNull(format.parse(endTime)).toString();

        ArrayList<String> timeList = new ArrayList<>();
        timeList.add(0, sTime.split(" ")[3]);
//        timeList.add(difference-1, endTime);
        for(int i = 1; i <= difference-2; i++){
            startTime = startTime.replace(":00:00", "");
            int tempTime = Integer.parseInt(startTime) + i;

            timeList.add(String.valueOf(tempTime).concat(":00:00"));
        }
        timeList.add(difference-1, eTime.split(" ")[3]);
//        Log.println(Log.DEBUG, "time", String.valueOf(timeList.size()));
//        // basic adapter to display items
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tStartTime.setAdapter(adapter);
    }
}
