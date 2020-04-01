package com.silentlad.bloodbank.admin_ui.addNewHospital;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.silentlad.bloodbank.R;
import com.silentlad.bloodbank.data.databasehelper.DatabaseHelper_Hospitals;
import com.silentlad.bloodbank.donor_ui.nearbyHospitals.NhRvAdapter;

import java.util.Random;

public class AddHospitalFragment extends Fragment {

    private DatabaseHelper_Hospitals db;

    private EditText hospitalName;
    private EditText city;
    private EditText workingDays;
    private EditText workingHours;
    private EditText gMapsUrl;
    private Button addButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.a_fragment_add, container, false);
        // initialize all elements in the view
        db = new DatabaseHelper_Hospitals(getContext());
        initView(root);

        // only empty checks are added
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (workingDays.getText().toString().contains("-") && workingHours.getText().toString().contains("-")) {
                    String sId = java.util.UUID.randomUUID().toString().replace("-", "");
                    String sHospitalName = hospitalName.getText().toString();
                    String sCity = city.getText().toString();
                    String[] sWorkingDays = workingDays.getText().toString().split("-");
                    String[] sWorkingTime = workingHours.getText().toString().split("-");
                    String gmaps = gMapsUrl.getText().toString();

                    boolean isDataFilled = !sId.equals("") && !sHospitalName.equals("") && !sCity.equals("") && sWorkingDays.length != 0 &&
                            sWorkingTime.length != 0
                            && !gmaps.equals("");

                    if (isDataFilled && db.insertData(sId, sHospitalName, sCity, sWorkingDays[0], sWorkingDays[1],
                            sWorkingTime[0], sWorkingTime[1], gmaps)) {
                        Toast.makeText(getContext(), "Data added.", Toast.LENGTH_SHORT).show();

                        // clear data after showing toast
                        hospitalName.getText().clear();
                        city.getText().clear();
                        workingDays.getText().clear();
                        workingHours.getText().clear();
                        gMapsUrl.getText().clear();
                    } else {
                        Toast.makeText(getContext(), "Data not added.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Please add data in given format", Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }

    private void initView(View root) {
        hospitalName = root.findViewById(R.id.hospital_name_edit);
        city = root.findViewById(R.id.city_edit);
        workingDays = root.findViewById(R.id.working_days_edit);
        workingHours = root.findViewById(R.id.working_hours_edit);
        gMapsUrl = root.findViewById(R.id.gmaps_url_edit);
        addButton = root.findViewById(R.id.add_button);
    }

}
