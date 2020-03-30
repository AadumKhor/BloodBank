package com.silentlad.bloodbank.donor_ui.appointments;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silentlad.bloodbank.R;
import com.silentlad.bloodbank.data.AppointmentContract;
import com.silentlad.bloodbank.data.Result;
import com.silentlad.bloodbank.data.databasehelper.DatabaseHelper_Appointments;
import com.silentlad.bloodbank.data.databasehelper.DatabaseHelper_Hospitals;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewholder> {
    private OnItemClickListener mListener;
    private Cursor mCursorApp;
    private DatabaseHelper_Hospitals mDb_hos;
    private DatabaseHelper_Appointments mDb_app;

    // transfer all data from external list to our list
    public RVAdapter(Cursor cursor, DatabaseHelper_Hospitals db_hos, DatabaseHelper_Appointments db_app) {
        mCursorApp = cursor;
        mDb_hos = db_hos;
        mDb_app = db_app;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // change appointment details
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    // below class is used to bind the adapter to the viewholder
    static class RVViewholder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mHospitalName;
        TextView mStartingTime;
        TextView mDate;
        TextView mCity;
        Button mButton;

        private RVViewholder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view_appointment);
            mHospitalName = itemView.findViewById(R.id.textView_appointment_card);
            mStartingTime = itemView.findViewById(R.id.textView4_appointment_card);
            mDate = itemView.findViewById(R.id.textView3_appointment_card);
            mCity = itemView.findViewById(R.id.textView2_appointment_card);
            mButton = itemView.findViewById(R.id.app_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RVViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_card, parent, false);
        return new RVViewholder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewholder holder, final int position) {
        if (!mCursorApp.moveToPosition(position)) {
            return;
        }
        final String id = mCursorApp.getString(mCursorApp.getColumnIndex(AppointmentContract.AppointmentEntry.COLUMN_ID));
        final String hospitalId = mCursorApp.getString(mCursorApp.getColumnIndex(AppointmentContract.AppointmentEntry.COLUMN_HOSPITAL_ID));
        final Result result = mDb_hos.getHospitalDetailsAppointments(hospitalId);

        if (result instanceof Result.Success) {
            String[] details = (String[]) ((Result.Success) result).getData();
            final String time = mCursorApp.getString(mCursorApp.getColumnIndex(AppointmentContract.AppointmentEntry.COLUMN_TIME));
            final String date = mCursorApp.getString(mCursorApp.getColumnIndex(AppointmentContract.AppointmentEntry.COLUMN_DATE));

            holder.mHospitalName.setText(details[0]);
            holder.mCity.setText(details[1]);
            holder.mDate.setText(date);
            holder.mStartingTime.setText(time);
            holder.mImageView.setImageResource(R.drawable.ic_local_hospital_black_24dp);
            holder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDb_app.removeItem(id);
                }
            });
            // set id as tag for the whole view
            holder.itemView.setTag(id);
        } else {
            mDb_app.removeItem(id);
        }

    }

    @Override
    public int getItemCount() {
        return mCursorApp.getCount();
    }
}
