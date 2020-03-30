package com.silentlad.bloodbank.data;

import android.provider.BaseColumns;

public class AppointmentContract {
    public AppointmentContract(){}

    public static final class AppointmentEntry implements BaseColumns {
        public static final String TABLE_NAME = "appointments";
        public static final String COLUMN_ID = "appointmentId";
        public static final String COLUMN_HOSPITAL_ID = "hospitalId";
        public static final String COLUMN_USER_ID = "userId";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_DATE = "date";
    }
}
