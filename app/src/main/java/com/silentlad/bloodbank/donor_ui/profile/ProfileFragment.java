package com.silentlad.bloodbank.donor_ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.silentlad.bloodbank.R;
import com.silentlad.bloodbank.data.Result;
import com.silentlad.bloodbank.data.databasehelper.DatabaseHelper_Users;
import com.silentlad.bloodbank.data.model.DonorLoggedInUser;
import com.silentlad.bloodbank.donor_ui.donorLogin.DonorLoginViewModel;
import com.silentlad.bloodbank.donor_ui.donorLogin.DonorLoginViewModelFactory;

import java.util.Objects;

//import com.silentlad.bloodbank.donor_ui.R;

public class ProfileFragment extends Fragment {
    private String id;

    private DatabaseHelper_Users db;

    private RadioGroup radio_group;
    private EditText phone_edit;
    private EditText age_edit;
    private EditText name_edit;
    private EditText city_edit;
    private Spinner weight_dropdown;
    private Spinner bloodGroup_dropdown;

    private String[] weight_options = new String[]{"Below 50kg", "Above 50kg"};
    private String[] blood_groups = new String[]{"AB+", "AB-", "A+", "A-", "B+", "B-", "O+", "0-", "Other"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.d_fragment_profile, container, false);
        final Button confirm_button = root.findViewById(R.id.confirm_button);

        db = new DatabaseHelper_Users(getContext());
        DonorLoginViewModel donorLoginViewModel = ViewModelProviders.of(this, new DonorLoginViewModelFactory()).get(DonorLoginViewModel.class);

        Log.println(Log.DEBUG, "id", "viewmodel done");
        DonorLoggedInUser loggedInUser = donorLoginViewModel.getLoggedInUser();
        Log.println(Log.DEBUG, "id", "got user");
        id = loggedInUser.getDonorId();
        Log.println(Log.DEBUG, "id", id);

        // EDIT FIELDS

        name_edit = root.findViewById(R.id.name_edit);
        age_edit = root.findViewById(R.id.age_edit);
        city_edit = root.findViewById(R.id.city_edit);
        phone_edit = root.findViewById(R.id.phone_edit);

        // RADIO BUTTON GROUP
        radio_group = root.findViewById(R.id.radioGrp);

        // SPINNERS
        weight_dropdown = root.findViewById(R.id.spinner_weight);
        bloodGroup_dropdown = root.findViewById(R.id.blood_group_spinner);
        dropDowns();
        setTexts();
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Result result = db.updateData(id.trim(), name_edit.getText().toString().trim(), phone_edit.getText().toString().trim(),
                        String.valueOf(radio_group.getCheckedRadioButtonId()), age_edit.getText().toString().trim(),
                        weight_dropdown.getSelectedItem().toString().trim(),
                        bloodGroup_dropdown.getSelectedItem().toString().trim(),
                        city_edit.getText().toString().trim()
                );

                if(result instanceof Result.Success){
                    Toast toast = Toast.makeText(getContext(), "Details Updated", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getContext(), "Could not update details", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        return root;
    }

    private void setTexts() {
        Result result = db.getData(id);
        Log.println(Log.DEBUG, "id", "Outside if");
        if(result instanceof  Result.Success){
            Log.println(Log.DEBUG, "id", "Inside setTexts if");
            String[] array = (String[])((Result.Success) result).getData();

            name_edit.setText(array[0]);
            phone_edit.setText(array[1]);
            age_edit.setText(array[3]);
            city_edit.setText(array[6]);

            int bgPos = 0, weightPos = 0;

            for(int i = 0; i < blood_groups.length ; i++){
                if(blood_groups[i].equals(array[5])){
                    bgPos = i;
                }
            }

            for(int i = 0; i < weight_options.length ; i++){
                if(blood_groups[i].equals(array[4])){
                    weightPos = i;
                }
            }

            bloodGroup_dropdown.setSelection(bgPos);
            weight_dropdown.setSelection(weightPos);

        }
    }

    private void dropDowns() {
        ArrayAdapter<String> weight_adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_dropdown_item, weight_options);
        ArrayAdapter<String> blood_group_adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, blood_groups);
        weight_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blood_group_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight_dropdown.setAdapter(weight_adapter);
        bloodGroup_dropdown.setAdapter(blood_group_adapter);
    }
}
