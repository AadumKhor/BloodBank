package com.silentlad.bloodbank.donor_ui.appointments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.silentlad.bloodbank.AppointmentCard;
import com.silentlad.bloodbank.R;
import com.silentlad.bloodbank.data.AppointmentContract;
import com.silentlad.bloodbank.data.Result;
import com.silentlad.bloodbank.data.databasehelper.DatabaseHelper_Appointments;
import com.silentlad.bloodbank.data.databasehelper.DatabaseHelper_Hospitals;
import com.silentlad.bloodbank.data.model.DonorLoggedInUser;
import com.silentlad.bloodbank.donor_ui.donorLogin.DonorLoginViewModel;
import com.silentlad.bloodbank.donor_ui.donorLogin.DonorLoginViewModelFactory;

import java.util.ArrayList;
import java.util.Objects;

public class AppointmentsFragment extends Fragment {
    // list related stuff
    private ArrayList<AppointmentCard> appointmentList = new ArrayList<>();
    private RecyclerView mRecyclerView;

    // helper class db
    private DatabaseHelper_Appointments db_app;
    private DatabaseHelper_Hospitals db_hos;

    private String userId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // viewModel to use
        DonorLoginViewModel donorLoginViewModel = ViewModelProviders.of(this,
                new DonorLoginViewModelFactory()).get(DonorLoginViewModel.class);

        DonorLoggedInUser loggedInUser = donorLoginViewModel.getLoggedInUser();
        userId = loggedInUser.getDonorId();
        View root = inflater.inflate(R.layout.d_fragment_appointments, container, false);
        db_app = new DatabaseHelper_Appointments(getContext());
        db_hos = new DatabaseHelper_Hospitals(getContext());

        RVAdapter mAdapter = new RVAdapter(db_app.getData(userId), db_hos, db_app);

        buildRecyclerView(root, mAdapter);
        createList();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                db_app.removeItem(viewHolder.itemView.getTag().toString());
            }
        }).attachToRecyclerView(mRecyclerView);
        return root;
    }

    private void buildRecyclerView(View root, RVAdapter mAdapter) {
        mRecyclerView = root.findViewById(R.id.appointments_list);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String id = (String) Objects.requireNonNull(mRecyclerView.
                        findViewHolderForAdapterPosition(position)).itemView.getTag();
                AppointmentCard currentCard = appointmentList.get(position);
                Intent intent = new Intent(getContext(), ChangeAppointmentDetails.class);
                Result result = db_hos.getHospitalDetailsAppointments(currentCard.getmHospitalId());
                if (result instanceof Result.Success) {
                    String[] details = (String[]) ((Result.Success) result).getData();
                    intent.putExtra("id", id);
                    intent.putExtra("name", details[0]);
                    intent.putExtra("city", details[1]);
                    intent.putExtra("gmap", details[2]);
                    intent.putExtra("date", currentCard.getmDate());
                    intent.putExtra("time", currentCard.getmStartingTime());
                    intent.putExtra("image", R.drawable.ic_local_hospital_black_24dp);
                    startActivity(intent);
                }
            }
        });
    }

    private void createList() {
        Cursor cursor = db_app.getData(userId);

        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No appointments", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(AppointmentContract.AppointmentEntry.COLUMN_USER_ID)).equals(userId)) {
                    appointmentList.add(new AppointmentCard(
                            cursor.getString(cursor.getColumnIndex(AppointmentContract.AppointmentEntry.COLUMN_ID)),
                            R.drawable.ic_dashboard_black_24dp,
                            cursor.getString(cursor.getColumnIndex(AppointmentContract.AppointmentEntry.COLUMN_HOSPITAL_ID)),
                            cursor.getString(cursor.getColumnIndex(AppointmentContract.AppointmentEntry.COLUMN_TIME)),
                            cursor.getString(cursor.getColumnIndex(AppointmentContract.AppointmentEntry.COLUMN_DATE))

                    ));
                }
            }
        }
    }
}
