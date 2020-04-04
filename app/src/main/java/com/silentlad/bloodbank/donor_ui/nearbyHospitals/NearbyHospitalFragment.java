package com.silentlad.bloodbank.donor_ui.nearbyHospitals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silentlad.bloodbank.R;
import com.silentlad.bloodbank.admin_ui.ChangeHospitalInfo;
import com.silentlad.bloodbank.data.databasehelper.DatabaseHelper_Hospitals;
import com.silentlad.bloodbank.donor_ui.FixAppointment;

import java.util.Objects;

public class NearbyHospitalFragment extends Fragment {
    private DatabaseHelper_Hospitals db;
    private Spinner dropdown;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.d_fragment_hospitals, container, false);
        dropdown = root.findViewById(R.id.dropdown);
        db = new DatabaseHelper_Hospitals(getContext());
        NhRvAdapter mAdapter = new NhRvAdapter(db.viewData());
        createDropDownList();
        buildRecyclerView(root, mAdapter);
        return root;
    }

    private void buildRecyclerView(View root, NhRvAdapter mAdapter) {
        final RecyclerView mRecyclerView = root.findViewById(R.id.nearby_hospitals_list);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new NhRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), FixAppointment.class);
                String id = Objects.requireNonNull(mRecyclerView.findViewHolderForAdapterPosition(position)).itemView.getTag().toString();
                String[] currentItem = db.getDetailsAdmin(id);
                intent.putExtra("id", currentItem[0]);
                intent.putExtra("name", currentItem[1]);
                intent.putExtra("city", currentItem[2]);
                intent.putExtra("startTime", currentItem[5]);
                intent.putExtra("endTime", currentItem[6]);
                intent.putExtra("gmap", currentItem[7]);
                startActivity(intent);
            }
        });
    }

    private void createDropDownList() {
        String[] mock_items = new String[]{"All", "Delhi", "Mumbai", "Jakarta", "New York"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item, mock_items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
    }
}
