package com.silentlad.bloodbank.donor_ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.net.Uri;
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
import com.silentlad.bloodbank.data.model.DonorLoggedInUser;
import com.silentlad.bloodbank.donor_ui.donorLogin.DonorLoginViewModel;
import com.silentlad.bloodbank.donor_ui.donorLogin.DonorLoginViewModelFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class FixAppointment extends AppCompatActivity {
    private TextView name_textView;
    private TextView city_textView;
    private ImageView hospital_image;
    private ImageView info_image;
    private Spinner dropDown_time;

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private TextView datePickText;

    private DatabaseHelper_Appointments db_app;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_activity_fix_appointment);
        DonorLoginViewModel donorLoginViewModel = ViewModelProviders.of(this,
                new DonorLoginViewModelFactory()).get(DonorLoginViewModel.class);

        DonorLoggedInUser loggedInUser = donorLoginViewModel.getLoggedInUser();
        userId = loggedInUser.getDonorId();
        // getting data sent with intent
        String hospitalName = getIntent().getStringExtra("name");
        String city = getIntent().getStringExtra("city");
        int image = getIntent().getIntExtra("image", 0);

        // call data base helper class and initialize view

        db_app = new DatabaseHelper_Appointments(this);
        initView();
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                datePickText.setText(date);
            }
        };
        try {
            createDropDownList2();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        name_textView.setText(hospitalName);
        city_textView.setText(city);
        hospital_image.setImageResource(image);
    }

    private void initView() {
        info_image = findViewById(R.id.info_image);
        info_image.setTag(getIntent().getStringExtra("gmap"));
        name_textView = findViewById(R.id.fix_app_hospital_name);
        city_textView = findViewById(R.id.fix_app_city);
        hospital_image = findViewById(R.id.fix_app_imageView);
        Button confirm_button = findViewById(R.id.fix_app_button);
        datePickText = findViewById(R.id.fix_app_s2);

        info_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "geo:0,0?q=%s", info_image.getTag());
                Log.println(Log.DEBUG, "id", "Url = " + uri);
                Intent gmapsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(gmapsIntent);
            }
        });

        datePickText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calender = Calendar.getInstance();
                int year = calender.get(Calendar.YEAR);
                int month = calender.get(Calendar.MONTH);
                int day = calender.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(FixAppointment.this,
                        android.R.style.Theme_Material_Dialog_MinWidth,
                        onDateSetListener, year, month, day
                );

                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }


        });

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = random();
                String hosId = getIntent().getStringExtra("id");
                String date = datePickText.getText().toString();
                String time = dropDown_time.getSelectedItem().toString();

                assert hosId != null;
                boolean isDataFilled = !id.equals("") && !hosId.equals("") &&
                        !date.equals("") && !time.equals("");

                boolean doesNotExist = !db_app.checkIfExists(hosId, userId, time, date);
                if (isDataFilled && doesNotExist) {
                    boolean insertData = db_app.insert(id, hosId, userId, time, date) instanceof Result.Success;

                    if (insertData) {
                        Toast.makeText(getApplicationContext(), "Appointment Made.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Appointment cannot be Made.", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    private static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = 6;
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(57) + 65);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    private void createDropDownList2() throws ParseException {
        String id = getIntent().getStringExtra("hosId");
        String startTime = getIntent().getStringExtra("startTime");
        String endTime = getIntent().getStringExtra("endTime");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        assert startTime != null;
        assert endTime != null;
        int difference = Integer.parseInt(endTime) - Integer.parseInt(startTime);
        if (!startTime.contains(":00:00")) {
            startTime = startTime.concat(":00:00");
        }

        if (!endTime.contains(":00:00")) {
            endTime = endTime.concat(":00:00");
        }
        Log.println(Log.DEBUG, "time", "new " + startTime);
        String sTime = Objects.requireNonNull(format.parse(startTime)).toString();
        String eTime = Objects.requireNonNull(format.parse(endTime)).toString();

        ArrayList<String> timeList = new ArrayList<>();
        timeList.add(0, sTime.split(" ")[3]);
//        timeList.add(difference-1, endTime);
        for (int i = 1; i <= difference - 2; i++) {
            startTime = startTime.replace(":00:00", "");
            int tempTime = Integer.parseInt(startTime) + i;

            timeList.add(String.valueOf(tempTime).concat(":00:00"));
        }
        timeList.add(difference - 1, eTime.split(" ")[3]);
//        Log.println(Log.DEBUG, "time", String.valueOf(timeList.size()));

        dropDown_time = findViewById(R.id.fix_app_s1);
//        // basic adapter to display items
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDown_time.setAdapter(adapter);
    }
}
