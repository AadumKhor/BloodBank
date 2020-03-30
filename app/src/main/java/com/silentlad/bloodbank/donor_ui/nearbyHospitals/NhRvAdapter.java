package com.silentlad.bloodbank.donor_ui.nearbyHospitals;

import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silentlad.bloodbank.R;
import com.silentlad.bloodbank.data.HospitalContract;


public class NhRvAdapter extends RecyclerView.Adapter<NhRvAdapter.NhRvAdapterHolder> {
    private OnItemClickListener onItemClickListener;

    private Cursor mCursor;

    public NhRvAdapter(Cursor cursor) {
        mCursor = cursor;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    static class NhRvAdapterHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mText1;
        private TextView mText2;

        private NhRvAdapterHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mText1 = itemView.findViewById(R.id.textView_card);
            mText2 = itemView.findViewById(R.id.textView2_card);

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
    public NhRvAdapter.NhRvAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_card, parent, false);
        return new NhRvAdapterHolder(v, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NhRvAdapterHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String hospitalName = mCursor.getString(mCursor.getColumnIndex(HospitalContract.HospitalEntry.COLUMN_NAME));
        String city = mCursor.getString(mCursor.getColumnIndex(HospitalContract.HospitalEntry.COLUMN_CITY));
        String id = mCursor.getString(mCursor.getColumnIndex(HospitalContract.HospitalEntry.COLUMN_ID));
//        Log.println(Log.DEBUG, "id", "Your id = "+id);
//        HospitalCard currentItem = cardItemArrayList.get(position);

        holder.mImageView.setImageResource(R.drawable.ic_local_hospital_black_24dp);
        holder.mText1.setText(hospitalName);
        holder.mText2.setText(city);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

//    public void swapCursor(Cursor newCursor) {
//        if (mCursor != null) {
//            mCursor.close();
//        }
//
//        mCursor = newCursor;
//
//        if (newCursor != null) {
//            notifyDataSetChanged();
//        }
//    }

}
