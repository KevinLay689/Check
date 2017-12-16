package com.example.kevinlay.check.database;

import android.util.Log;
import android.widget.TextView;

import com.example.kevinlay.check.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Database should contain these references at bare minimum
 *
 */

public class DatabaseObject {

    private static final String TAG = "DatabaseObject";

    public static final String USERS_REFERENCE = "users";
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

    private static DatabaseObject obj;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private DatabaseObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                //Log.i(TAG, "onDataChange: " + user.getMajor());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot snapshot : dataSnapshot.child(USERS_REFERENCE).getChildren()) {
//                    User user = snapshot.getValue(User.class);
//                    Log.i(TAG, "onDataChange: "+ user.getEmail());
//                }
////                User user = dataSnapshot.child(USER_REFERENCE).child(mAuth.getUid()).getValue(User.class);
////                Log.i(TAG, "onDataChange: "+ user.getEmail());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    public void changeData(String reference, String newData) {

        switch (reference) {
            case USER_STATE:
                databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(USER_STATE).setValue(newData);
                break;
            case HOMETOWN_REFERENCE:
                databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(HOMETOWN_REFERENCE).setValue(newData);
                break;
            case MAJOR_REFERENCE:
                databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(MAJOR_REFERENCE).setValue(newData);
                break;
            case MAJOR__PREFERENCE_REFERENCE:
                databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(MAJOR__PREFERENCE_REFERENCE).setValue(newData);
                break;
            case TIME_START_REFERENCE:
                databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(TIME_START_REFERENCE).setValue(newData);
                break;
            case TIME_END_REFERENCE:
                databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(TIME_END_REFERENCE).setValue(newData);
                break;
            case ABOUT_ME_REFERENCE:
                databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(ABOUT_ME_REFERENCE).setValue(newData);
                break;
        }
    }

//    public String getUserData(String reference) {
//
//        User user = getUser();
//
//        switch (reference) {
//            case USER_STATE:
//               return user.getUserState();
//            case HOMETOWN_REFERENCE:
//               return user.getHometown();
//            case MAJOR_REFERENCE:
//                return user.getMajor();
//            case TIME_START_REFERENCE:
//                return user.getTimeStart();
//            case TIME_END_REFERENCE:
//                return user.getTimeEnd();
//            default:
//                return "";
//        }
//    }
//
//    private void getUser() {
//        final ArrayList<User> cachedUser = new ArrayList<>();
//
//        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                cachedUser.clear();
//                User user = dataSnapshot.getValue(User.class);
//                cachedUser.add(user);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        Log.i(TAG, "onDataChange: " + cachedUser.get(0).getMajor());
//        return cachedUser.get(0);
//    }

    public String searchDatabase(String reference, String search) {
        return null;
    }

    public void setProfileUserData(final TextView major,
                                   final TextView aboutMe,
                                   final TextView hometown) {
        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                major.setText(user.getMajor());
                aboutMe.setText(user.getAboutMe());
                hometown.setText(user.getHometown());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    public void setProfileUserData(String reference, final TextView textView, final String data) {
//        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//                sw
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    public static DatabaseObject getInstance(){
        if(obj == null){
            obj = new DatabaseObject();
        }
        return obj;
    }
}
