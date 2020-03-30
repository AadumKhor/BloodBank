package com.silentlad.bloodbank.admin_ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silentlad.bloodbank.HospitalCard;
import com.silentlad.bloodbank.R;
import com.silentlad.bloodbank.admin_ui.ChangeHospitalInfo;
import com.silentlad.bloodbank.data.HospitalContract;
import com.silentlad.bloodbank.data.databasehelper.DatabaseHelper_Hospitals;
import com.silentlad.bloodbank.donor_ui.nearbyHospitals.NhRvAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment implements LifecycleOwner {

    private DatabaseHelper_Hospitals db;
    private NhRvAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private EditText search;

    //    private ArrayList<HospitalCard> hospitalList = new ArrayList<>();
    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.a_fragment_hospitals, container, false);
        db = new DatabaseHelper_Hospitals(getContext());
        mAdapter = new NhRvAdapter(db.viewData());

        search = root.findViewById(R.id.editText);
//        mAdapter.swapCursor(db.viewData());
        // create the dropdown
        createDropDownList(root);
        buildRecyclerView(root, mAdapter);
//        viewData();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO: Add search thing
            }
        });
        return root;
    }

    private void buildRecyclerView(View root, final NhRvAdapter mAdapter) {
        mRecyclerView = root.findViewById(R.id.admin_hospitals);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new NhRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                String id = Objects.requireNonNull(mRecyclerView.findViewHolderForAdapterPosition(position)).
                        itemView.getTag().toString();
                Log.println(Log.DEBUG, "id", id);
                String[] currentItem = db.getDetailsAdmin(id);
                Log.println(Log.DEBUG, "id", String.valueOf(position));
                Intent intent = new Intent(getContext(), ChangeHospitalInfo.class);
                intent.putExtra("id", currentItem[0]);
                intent.putExtra("name", currentItem[1]);
                intent.putExtra("city", currentItem[2]);
                intent.putExtra("startDay", currentItem[3]);
                intent.putExtra("endDay", currentItem[4]);
                intent.putExtra("startTime", currentItem[5]);
                intent.putExtra("endTime", currentItem[6]);
                intent.putExtra("gmaps", currentItem[7]);

                startActivity(intent);
                Objects.requireNonNull(getActivity()).finish();
            }
        });
    }

    private void createDropDownList(View root) {
        Spinner dropDown = root.findViewById(R.id.dropdown);
        // add a list of items to it for mock
        String[] mock_items = new String[]{"All", "Delhi", "Mumbai", "Jakarta", "New York"};
        // basic adapter to display items
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, mock_items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDown.setAdapter(adapter);
    }
}
