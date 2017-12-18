package com.example.kevinlay.check.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Database should contain these references at bare minimum
 *
 */

public class DatabaseObject {

    private static final String TAG = "DatabaseObject";

    public static final String USERS_REFERENCE = "users";
    public static final String USER_REFERENCE = "userReference";
    public static final String FIRST_NAME_REFERENCE = "firstName";
    public static final String LAST_NAME_REFERENCE = "lastName";
    public static final String TIME_START_REFERENCE = "timeStart";
    public static final String TIME_END_REFERENCE = "timeEnd";
    public static final String USER_STATE = "userState";
    public static final String NOTIFICATION_REFERENCE = "notification";
    public static final String MAJOR__PREFERENCE_REFERENCE = "majorPreference";
    public static final String PARTNER_REFERENCE = "partner";
    public static final String MAJOR_REFERENCE = "major";
    public static final String PROFILE_PIC_REFERENCE = "profilePic";
    public static final String ABOUT_ME_REFERENCE = "aboutMe";
    public static final String HOMETOWN_REFERENCE = "hometown";
    public static final String LUNCH_TIME_REFERENCE = "lunchTime";

    private static DatabaseObject obj;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private List<User> yourInfo = new ArrayList<>();
    private List<User> otherProfiles = new ArrayList<>();

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

    public String searchDatabase(String reference, String search) {
        return null;
    }

    public void setProfileUserData(final TextView major, final TextView aboutMe, final TextView hometown) {
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

    public void setUserData(final TextView textView, final String reference) {
        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                switch (reference) {
                    case USER_STATE:
                        break;
                    case HOMETOWN_REFERENCE:
                        break;
                    case MAJOR_REFERENCE:
                        break;
                    case MAJOR__PREFERENCE_REFERENCE:
                        break;
                    case TIME_START_REFERENCE:
                        break;
                    case TIME_END_REFERENCE:
                        break;
                    case ABOUT_ME_REFERENCE:
                        break;
                    case FIRST_NAME_REFERENCE:
                        textView.setText(user.getFirstName());
                        break;
                    case LAST_NAME_REFERENCE:
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void beginPartnerSearch() {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                yourInfo.clear();
                otherProfiles.clear();

                for(DataSnapshot snapshot : dataSnapshot.child(USERS_REFERENCE).getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if(snapshot.getKey().equals(mAuth.getUid())) {
                        if(user.getTimeStart().length() > 1 && user.getTimeEnd().length() > 1) {
                            yourInfo.add(user);
                        }
                    } else {
                        otherProfiles.add(user);
                        Log.i(TAG, "size" + otherProfiles.size());
                    }
                }

                String yourTimeStart = "";
                String yourTimeEnd = "";

                yourTimeStart = yourInfo.get(0).getTimeStart();
                yourTimeEnd = yourInfo.get(0).getTimeEnd();

                for(int i = 0; i < otherProfiles.size(); i++) {

                    if(otherProfiles.get(i).getUserState().equals("Searching")) {
                        String otherUserTimeStart, otherUserTimeEnd;

                        int eatTime = 20;

                        otherUserTimeStart = otherProfiles.get(i).getTimeStart();
                        otherUserTimeEnd = otherProfiles.get(i).getTimeEnd();

                        int yourTotalMinute, otherTotalMinute;

                        yourTotalMinute = minuteConversion(yourTimeEnd) - minuteConversion(yourTimeStart);

                        otherTotalMinute = minuteConversion(otherUserTimeEnd) - minuteConversion(otherUserTimeStart);

                        if( (yourTotalMinute + minuteConversion(yourTimeStart)) <= (minuteConversion(otherUserTimeStart)+19)) {
                            Log.i(TAG, "beginPartnerSearch:  there not a free window ");
                            break;
                        }
                        else {
                            Log.i(TAG, "beginPartnerSearch:  there is a free window");

                            if(minuteConversion(yourTimeStart) < minuteConversion(otherUserTimeStart)) {
                                String time = otherProfiles.get(i).getTimeStart();
                                databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(LUNCH_TIME_REFERENCE).setValue(time);
                                databaseReference.child(USERS_REFERENCE).child(otherProfiles.get(i).getId()).child(LUNCH_TIME_REFERENCE).setValue(time);
                            } else {
                                String time = yourInfo.get(0).getTimeStart();
                                databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(LUNCH_TIME_REFERENCE).setValue(time);
                                databaseReference.child(USERS_REFERENCE).child(otherProfiles.get(i).getId()).child(LUNCH_TIME_REFERENCE).setValue(time);
                            }

                            databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(PARTNER_REFERENCE).setValue(otherProfiles.get(i).getId());
                            databaseReference.child(USERS_REFERENCE).child(otherProfiles.get(i).getId()).child(PARTNER_REFERENCE).setValue(mAuth.getUid());
                            databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(USER_STATE).setValue("Paired");
                            databaseReference.child(USERS_REFERENCE).child(otherProfiles.get(i).getId()).child(USER_STATE).setValue("Paired");
                            break;
                        }

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private int minuteConversion(String hours) {

        return (Integer.parseInt((hours.charAt(0) +"")) * 10 * 60) +
                (Integer.parseInt((hours.charAt(1) +"")) * 60) +
                (Integer.parseInt((hours.substring(3))));

    }

    public static DatabaseObject getInstance(){
        if(obj == null){
            obj = new DatabaseObject();
        }
        return obj;
    }

    public void getProfilePic(final CircleImageView circleImageView) {

        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                // Check to see if the profile pic has been uploaded yet
                if(user.getProfilePic().length() > 1) {
                    byte[] decodedString = Base64.decode(user.getProfilePic(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    Bitmap.createScaledBitmap(decodedByte, 100, 100, false);
                    circleImageView.setImageBitmap(decodedByte);
                    decodedByte = null;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setProfilePic(String encoded) {
        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(PROFILE_PIC_REFERENCE).setValue(encoded);
    }
}
