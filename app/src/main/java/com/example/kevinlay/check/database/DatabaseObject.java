package com.example.kevinlay.check.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Database should contain these references at bare minimum
 *
 */

public class DatabaseObject {

    public static final String USER_REFERENCE = "userReference";
    public static final String TIME_START_REFERENCE = "timeStart";
    public static final String TIME_END_REFERENCE = "timeEnd";
    public static final String USER_STATE = "userState";
    public static final String NOTIFICATION_REFERENCE = "notification";
    public static final String MAJOR__PREFERENCE_REFERENCE = "majorPreference";
    public static final String PARTNER_REFERENCE = "partner";
    public static final String MAJOR_REFERENCE = "major";
    public static final String ABOUT_ME_REFERENCE = "aboutMe";
    public static final String HOMETOWN_REFERENCE = "hometown";

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    public DatabaseObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void changeData(String reference, String newData) {

    }

    public boolean searchDatabase(String reference, String search) {
        return false;
    }

    public void insertData(String reference, String data) {

    }
}
