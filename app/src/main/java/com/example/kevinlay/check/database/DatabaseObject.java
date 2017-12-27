package com.example.kevinlay.check.database;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.kevinlay.check.CustomHeaderView;
import com.example.kevinlay.check.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
    public static final String FLAKE_RATING_REFERENCE = "flakeRating";
    public static final String LOCATION_REFERENCE = "location";
    public static final String HEADER_IMAGE_REFERENCE = "header";
    public static final String PREFERRED_MAJOR_REFERENCE = "preferredMajor";

    public static final String SEARCHING_STATE = "Searching";
    public static final String IDLE_STATE = "Idle";
    public static final String PAIRED_STATE = "Paired";
    public static final String ACCEPTED_STATE = "Accepted";
    public static final String MATCHED_STATE = "Matched";


    private static DatabaseObject obj;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private List<User> yourInfo = new ArrayList<>();
    private List<User> otherProfiles = new ArrayList<>();

    private String major;

    private DatabaseObject() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
//        databaseReference.child(USERS_REFERENCE).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//                if(user.getId().equals(mAuth.getUid())) {
//                    // Make interface call here
//                }
//                //Log.i(TAG, "onDataChange: " + user.getMajor());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

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
            case LOCATION_REFERENCE:
                databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(LOCATION_REFERENCE).setValue(newData);
                break;
            case PREFERRED_MAJOR_REFERENCE:
                databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(PREFERRED_MAJOR_REFERENCE).setValue(newData);
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

    public void setBothPartnersEmpty() {
        databaseReference.child(USERS_REFERENCE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if (user.getPartner().equals(mAuth.getUid())) {
                        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(PARTNER_REFERENCE).setValue("");
                        databaseReference.child(USERS_REFERENCE).child(user.getId()).child(PARTNER_REFERENCE).setValue("");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getOtherProfile(final CircleImageView circleImageView, final TextView major, final TextView aboutMe, final TextView hometown, final TextView username, final ProgressDialog progressDialog) {

        databaseReference.child(USERS_REFERENCE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if(user.getPartner().equals(mAuth.getUid())) {
                        if (user.getProfilePic().length() > 1) {
//                            byte[] decodedString = Base64.decode(user.getProfilePic(), Base64.DEFAULT);
//                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                            Bitmap.createScaledBitmap(decodedByte, 100, 100, false);
//                            circleImageView.setImageBitmap(decodedByte);
//                            decodedByte = null;

                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 8;
                            options.inPreferredConfig = Bitmap.Config.RGB_565;
                            byte[] decodedString = Base64.decode(user.getProfilePic(), Base64.DEFAULT);
                            //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            InputStream is = new ByteArrayInputStream(decodedString);
                            Bitmap decodedByte = BitmapFactory.decodeStream(is, null, options);
                            Bitmap.createScaledBitmap(decodedByte, 100, 100, false);
                            circleImageView.setImageBitmap(decodedByte);
                            decodedByte = null;

                        }
                        major.setText(user.getMajor());
                        aboutMe.setText(user.getAboutMe());
                        hometown.setText(user.getHometown());
                        username.setText(user.getFirstName());
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setViewData(final View view, final String reference, final String extraText) {
        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                switch (reference) {
                    case HEADER_IMAGE_REFERENCE:
                        ((CustomHeaderView) view).setLetter(user.getFirstName().charAt(0)+"");
                        break;

                    case TIME_START_REFERENCE:
                        ((TextView)view).setText(clockConversion(user.getTimeStart()));
                        break;

                    case TIME_END_REFERENCE:
                        ((TextView)view).setText(clockConversion(user.getTimeEnd()));
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setUserData(final TextView textView, final String reference, final String extraText) {
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
                    case LOCATION_REFERENCE:
                        textView.setText(extraText + " " + user.getLocation());
                        break;
                    case LUNCH_TIME_REFERENCE:
                        textView.setText(extraText + " " + user.getLunchTime());
                        break;
                    case FLAKE_RATING_REFERENCE:
                        textView.setText(extraText + " " + user.getFlakeRating());
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void beginPartnerSearch(final String major) {

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
                    }
                }

                String yourTimeStart = "";
                String yourTimeEnd = "";

                yourTimeStart = yourInfo.get(0).getTimeStart();
                yourTimeEnd = yourInfo.get(0).getTimeEnd();

                boolean runAgain = true;

                for(int i = 0; i < otherProfiles.size(); i++) {

                    if(otherProfiles.get(i).getUserState().equals(DatabaseObject.SEARCHING_STATE)) {
                        String otherUserTimeStart, otherUserTimeEnd;

                        otherUserTimeStart = otherProfiles.get(i).getTimeStart();
                        otherUserTimeEnd = otherProfiles.get(i).getTimeEnd();

                        Log.i(TAG, "onDataChange: your time end: " + minuteConversion(yourTimeEnd));
                        Log.i(TAG, "onDataChange: other time start is " + minuteConversion(otherUserTimeStart));
                        int yourTotalMinute;

                        yourTotalMinute = minuteConversion(yourTimeEnd) - minuteConversion(yourTimeStart);
                        int yourTotalTimeStart = yourTotalMinute + minuteConversion(yourTimeStart);

                        if( minuteConversion(yourTimeEnd) <= (minuteConversion(otherUserTimeStart)+19) && !major.equals(otherProfiles.get(i).getMajor())) {
                            Log.i(TAG, "beginPartnerSearch:  theres not a free window ");
                        } else if(minuteConversion(yourTimeStart) > minuteConversion(otherUserTimeEnd) && !major.equals(otherProfiles.get(i).getMajor())) {
                            Log.i(TAG, "beginPartnerSearch:  theres not a free window ");
                        } else {
                            Log.i(TAG, "beginPartnerSearch:  there is a free window");
                            Log.i(TAG, "onDataChange: size of otherProfiles is" + otherProfiles.size());
                            if(minuteConversion(yourTimeStart) < minuteConversion(otherUserTimeStart)) {
                                String time = otherProfiles.get(i).getTimeStart();
                                String finalTime = clockConversion(time);
                                databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(LUNCH_TIME_REFERENCE).setValue(finalTime);
                                databaseReference.child(USERS_REFERENCE).child(otherProfiles.get(i).getId()).child(LUNCH_TIME_REFERENCE).setValue(finalTime);
                            } else {
                                String time = yourInfo.get(0).getTimeStart();
                                String finalTime =  clockConversion(time);
                                databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(LUNCH_TIME_REFERENCE).setValue(finalTime);
                                databaseReference.child(USERS_REFERENCE).child(otherProfiles.get(i).getId()).child(LUNCH_TIME_REFERENCE).setValue(finalTime);
                            }

                            runAgain = false;

                            databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(PARTNER_REFERENCE).setValue(otherProfiles.get(i).getId());
                            databaseReference.child(USERS_REFERENCE).child(otherProfiles.get(i).getId()).child(PARTNER_REFERENCE).setValue(mAuth.getUid());
                            databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(USER_STATE).setValue(DatabaseObject.PAIRED_STATE);
                            databaseReference.child(USERS_REFERENCE).child(otherProfiles.get(i).getId()).child(USER_STATE).setValue(DatabaseObject.PAIRED_STATE);
                            break;
                        }
                    }
                }

                if(runAgain) {
                    for(int i = 0; i < otherProfiles.size(); i++) {

                        if(otherProfiles.get(i).getUserState().equals(DatabaseObject.SEARCHING_STATE)) {
                            String otherUserTimeStart, otherUserTimeEnd;

                            otherUserTimeStart = otherProfiles.get(i).getTimeStart();
                            otherUserTimeEnd = otherProfiles.get(i).getTimeEnd();

                            Log.i(TAG, "onDataChange: your time end: " + minuteConversion(yourTimeEnd));
                            Log.i(TAG, "onDataChange: other time start is " + minuteConversion(otherUserTimeStart));
                            int yourTotalMinute;

                            yourTotalMinute = minuteConversion(yourTimeEnd) - minuteConversion(yourTimeStart);
                            int yourTotalTimeStart = yourTotalMinute + minuteConversion(yourTimeStart);

                            if( minuteConversion(yourTimeEnd) <= (minuteConversion(otherUserTimeStart)+19)) {
                                Log.i(TAG, "beginPartnerSearch:  theres not a free window ");
                            } else if(minuteConversion(yourTimeStart) > minuteConversion(otherUserTimeEnd)) {
                                Log.i(TAG, "beginPartnerSearch:  theres not a free window ");
                            } else {
                                Log.i(TAG, "beginPartnerSearch:  there is a free window");
                                Log.i(TAG, "onDataChange: size of otherProfiles is" + otherProfiles.size());
                                if(minuteConversion(yourTimeStart) < minuteConversion(otherUserTimeStart)) {
                                    String time = otherProfiles.get(i).getTimeStart();
                                    String finalTime = clockConversion(time);
                                    databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(LUNCH_TIME_REFERENCE).setValue(finalTime);
                                    databaseReference.child(USERS_REFERENCE).child(otherProfiles.get(i).getId()).child(LUNCH_TIME_REFERENCE).setValue(finalTime);
                                } else {
                                    String time = yourInfo.get(0).getTimeStart();
                                    String finalTime =  clockConversion(time);
                                    databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(LUNCH_TIME_REFERENCE).setValue(finalTime);
                                    databaseReference.child(USERS_REFERENCE).child(otherProfiles.get(i).getId()).child(LUNCH_TIME_REFERENCE).setValue(finalTime);
                                }

                                databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(PARTNER_REFERENCE).setValue(otherProfiles.get(i).getId());
                                databaseReference.child(USERS_REFERENCE).child(otherProfiles.get(i).getId()).child(PARTNER_REFERENCE).setValue(mAuth.getUid());
                                databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(USER_STATE).setValue(DatabaseObject.PAIRED_STATE);
                                databaseReference.child(USERS_REFERENCE).child(otherProfiles.get(i).getId()).child(USER_STATE).setValue(DatabaseObject.PAIRED_STATE);
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static String clockConversion(String hours) {

        int first = (Integer.parseInt((hours.charAt(0) + "")));
        int second = (Integer.parseInt((hours.charAt(1) + "")));
        int third = (Integer.parseInt((hours.charAt(3) + "")));
        int fourth = (Integer.parseInt((hours.charAt(4) + "")));

        String totalHour = first+""+second+""+third+""+fourth;

        String amPM = " AM";

        String hourTimeAsString = first+""+second;

        int hourTime = Integer.parseInt(hourTimeAsString);

        if(hourTime >= 12) {
            amPM = " PM";
            hourTime = hourTime - 12;
        }

        if(hourTime == 0) {
            hourTime = 12;
        }

        //Log.i(TAG, "clockConversion: "+ hourTime+""+third+""+fourth);

        return hourTime+":"+third+""+fourth+amPM;

    }

    private int minuteConversion(String hours) {

        return (Integer.parseInt((hours.charAt(0) + "")) * 10 * 60) +
                (Integer.parseInt((hours.charAt(1) + "")) * 60) +
                (Integer.parseInt((hours.substring(3))));

    }

    public static DatabaseObject getInstance(){
        if(obj == null){
            obj = new DatabaseObject();
        }
        return obj;
    }

    public void getProfilePic(final CircleImageView circleImageView, final ProgressDialog progressDialog) {

        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                // Check to see if the profile pic has been uploaded yet
                if(user.getProfilePic().length() > 1) {
//                    byte[] decodedString = Base64.decode(user.getProfilePic(), Base64.DEFAULT);
//                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                    Bitmap.createScaledBitmap(decodedByte, 100, 100, false);
//                    circleImageView.setImageBitmap(decodedByte);
//                    decodedByte = null;

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    byte[] decodedString = Base64.decode(user.getProfilePic(), Base64.DEFAULT);
                    //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    InputStream is = new ByteArrayInputStream(decodedString);
                    Bitmap decodedByte = BitmapFactory.decodeStream(is, null, options);
                    Bitmap.createScaledBitmap(decodedByte, 100, 100, false);
                    circleImageView.setImageBitmap(decodedByte);
                    decodedByte = null;

                    if(progressDialog != null ) {
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getOtherProfilePic(final CircleImageView circleImageView) {

        databaseReference.child(USERS_REFERENCE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    // Check to see if the profile pic has been uploaded yet
                    if(user.getProfilePic().length() > 1 && user.getPartner().equals(mAuth.getUid())) {

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 8;
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        byte[] decodedString = Base64.decode(user.getProfilePic(), Base64.DEFAULT);
                        //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        InputStream is = new ByteArrayInputStream(decodedString);
                        Bitmap decodedByte = BitmapFactory.decodeStream(is, null, options);
                        Bitmap.createScaledBitmap(decodedByte, 100, 100, false);
                        circleImageView.setImageBitmap(decodedByte);
                        decodedByte = null;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void sendRequestWithId(String otherId, String timeStart) {
        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(PARTNER_REFERENCE).setValue(otherId);
        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(USER_STATE).setValue(PAIRED_STATE);
        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(TIME_START_REFERENCE).setValue(timeStart);
        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(TIME_END_REFERENCE).setValue(timeStart);
        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(LUNCH_TIME_REFERENCE).setValue(clockConversion(timeStart));
        databaseReference.child(USERS_REFERENCE).child(otherId).child(LUNCH_TIME_REFERENCE).setValue(clockConversion(timeStart));
        databaseReference.child(USERS_REFERENCE).child(otherId).child(PARTNER_REFERENCE).setValue(mAuth.getUid());
        databaseReference.child(USERS_REFERENCE).child(otherId).child(USER_STATE).setValue(PAIRED_STATE);

    }

    public void setProfilePic(String encoded) {
        databaseReference.child(USERS_REFERENCE).child(mAuth.getUid()).child(PROFILE_PIC_REFERENCE).setValue(encoded);
    }

    public void updateStates(final String reference, final String value, final boolean acceptClicked) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                yourInfo.clear();
                otherProfiles.clear();

                for(DataSnapshot snapshot : dataSnapshot.child(USERS_REFERENCE).getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if(snapshot.getKey().equals(mAuth.getUid())) {
                        yourInfo.add(user);
                    } else {
                        otherProfiles.add(user);
                    }
                }

               for(int i = 0; i < otherProfiles.size(); i++) {
                    if(yourInfo.get(0).getPartner().equals(otherProfiles.get(i).getId())) {
                        databaseReference.child(USERS_REFERENCE)
                                .child(yourInfo.get(0).getId())
                                .child(reference)
                                .setValue(value);
                        if(!acceptClicked) {
                            databaseReference.child(USERS_REFERENCE)
                                    .child(otherProfiles.get(i).getId())
                                    .child(reference)
                                    .setValue(DatabaseObject.SEARCHING_STATE);
                            setBothPartnersEmpty();
                        } else {
                            if(otherProfiles.get(i).getUserState().equals(DatabaseObject.ACCEPTED_STATE)) {
                                databaseReference.child(USERS_REFERENCE)
                                        .child(yourInfo.get(0).getId())
                                        .child(reference)
                                        .setValue(DatabaseObject.MATCHED_STATE);

                                databaseReference.child(USERS_REFERENCE)
                                        .child(otherProfiles.get(i).getId())
                                        .child(reference)
                                        .setValue(DatabaseObject.MATCHED_STATE);
                            } else {
                                databaseReference.child(USERS_REFERENCE)
                                        .child(otherProfiles.get(i).getId())
                                        .child(reference)
                                        .setValue(DatabaseObject.PAIRED_STATE);
                            }
                        }
                    }
               }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
