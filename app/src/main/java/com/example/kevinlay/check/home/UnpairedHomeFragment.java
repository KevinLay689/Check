package com.example.kevinlay.check.home;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kevinlay.check.R;
import com.example.kevinlay.check.database.DatabaseObject;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kevinlay on 12/12/17.
 */

public class UnpairedHomeFragment extends Fragment {

    private static final String TAG = "UnpairedHomeFragment";
    private final int ANIMATION_DURATION = 2000;
    private final float SCALE_ANIMATION_END = 3.5f;
    private final long START_DELAY = 1000;

    private Button mSelectStartTime, mSelectEndTime, mFindPartner, mCancelSearch;
    private TextView mTimeStartLabel, mTimeEndLabel, mTimeEnd, mTimeStart;
    private ImageView mUserProfilePicture ,mRingImage1 ,mRingImage2;
    private AnimatorSet animatorSet, animatorSet2;

    private int timeHour, timeMinute;
    private String amPm;

    private CircleImageView mUserProfileImage;

    private DatabaseObject databaseObject;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_unpaired_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseObject = DatabaseObject.getInstance();

        mSelectStartTime = (Button) view.findViewById(R.id.selectStartTime);
        mSelectEndTime = (Button) view.findViewById(R.id.selectEndTime);
        mFindPartner = (Button) view.findViewById(R.id.findPartner);
        mCancelSearch = (Button) view.findViewById(R.id.cancelPartner);

        mTimeStartLabel = (TextView) view.findViewById(R.id.timeStartLabel);
        mTimeEndLabel = (TextView) view.findViewById(R.id.timeEndLabel);

        mTimeStart = (TextView) view.findViewById(R.id.timeStart);
        mTimeEnd = (TextView) view.findViewById(R.id.timeEnd);

        mRingImage1 = (ImageView) view.findViewById(R.id.ringImage1);
        mRingImage2 = (ImageView) view.findViewById(R.id.ringImage2);

        mUserProfileImage = view.findViewById(R.id.userProfileImage);

        databaseObject.getProfilePic(mUserProfileImage);

        setupOnClickListeners();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        // then you use
        String major = prefs.getString("major", "any");
        Boolean isAcceptingNotifications = prefs.getBoolean("notifications", true);


    }

    private void showTimePickerDialog(final TextView textView, final boolean isTimeStart) {
        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                int format;
                String serverHour, serverMinute;

                if(selectedHour > 11) {
                    amPm = "PM";
                    format = selectedHour - 12;
                } else {
                    format = selectedHour;
                    amPm = "AM";
                }

                if(format == 0) {
                    format = 12;
                }
                timeHour = format;
                timeMinute = selectedMinute;

                if(timeMinute < 10) {
                    textView.setText(timeHour + ":0" + timeMinute +" " + amPm);
                } else {
                    textView.setText(timeHour + ":" + timeMinute + " " + amPm);
                }

                if(timeMinute < 10) {
                    serverMinute = "0" + timeMinute;
                    if (timeMinute == 0) {
                        serverMinute = "00";
                    }
                } else {
                    serverMinute = selectedMinute + "";
                }

                if(selectedHour < 1 || selectedHour < 10 ) {
                    serverHour = "0" + selectedHour + ":";
                } else {
                    serverHour = selectedHour + ":";
                }

                if(isTimeStart) {
                    databaseObject.changeData(DatabaseObject.TIME_START_REFERENCE, serverHour + serverMinute);
                } else {
                    databaseObject.changeData(DatabaseObject.TIME_END_REFERENCE, serverHour + serverMinute);
                }
            }
        }, hour, minute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void setupOnClickListeners() {
        mSelectStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectStartTime.setVisibility(View.INVISIBLE);
                mSelectEndTime.setVisibility(View.VISIBLE);
//                mTimeStart.setVisibility(View.VISIBLE);
                showTimePickerDialog(mTimeStart, true);
            }
        });

        mSelectEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectEndTime.setVisibility(View.INVISIBLE);
                mFindPartner.setVisibility(View.VISIBLE);
                mCancelSearch.setVisibility(View.VISIBLE);
//                mTimeEnd.setVisibility(View.VISIBLE);
                showTimePickerDialog(mTimeEnd, false);
            }
        });

        mFindPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimations();
                mFindPartner.setEnabled(false);
                mFindPartner.setText(R.string.searching_partner);
                databaseObject.changeData(DatabaseObject.USER_STATE, "Searching");
                databaseObject.beginPartnerSearch();
            }
        });

        mCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectStartTime.setVisibility(View.VISIBLE);
                mFindPartner.setVisibility(View.INVISIBLE);
                mFindPartner.setText(R.string.find_partner);
                mFindPartner.setEnabled(true);
                mCancelSearch.setVisibility(View.INVISIBLE);
//                mTimeStart.setVisibility(View.INVISIBLE);
//                mTimeEnd.setVisibility(View.INVISIBLE);
                databaseObject.changeData(DatabaseObject.USER_STATE, "Idle");
                stopAnimations();
            }
        });
    }

    private void stopAnimations() {
        if(animatorSet2 != null && animatorSet != null) {
            animatorSet.end();
            animatorSet2.end();
            animatorSet.cancel();
            animatorSet2.cancel();
            mRingImage1.clearAnimation();
            mRingImage2.clearAnimation();
            animatorSet = null;
            animatorSet2 = null;
        }
    }

    // This method is in charge of starting the ring animation
    private void startAnimations() {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mRingImage1, View.ALPHA, .5f, 0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mRingImage1, View.SCALE_X, 1f, SCALE_ANIMATION_END);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mRingImage1, View.SCALE_Y, 1f, SCALE_ANIMATION_END);
        alpha.setDuration(ANIMATION_DURATION);
        scaleX.setDuration(ANIMATION_DURATION);
        scaleY.setDuration(ANIMATION_DURATION);
        alpha.setRepeatCount(Animation.INFINITE);
        scaleX.setRepeatCount(Animation.INFINITE);
        scaleY.setRepeatCount(Animation.INFINITE);

        animatorSet = new AnimatorSet();
        animatorSet.playTogether(alpha, scaleX, scaleY);
        animatorSet.start();

        ObjectAnimator alpha2 = ObjectAnimator.ofFloat(mRingImage2, View.ALPHA, .5f, 0f);
        ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(mRingImage2, View.SCALE_X, 1f, SCALE_ANIMATION_END);
        ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(mRingImage2, View.SCALE_Y, 1f, SCALE_ANIMATION_END);
        alpha2.setDuration(ANIMATION_DURATION);
        scaleX2.setDuration(ANIMATION_DURATION);
        scaleY2.setDuration(ANIMATION_DURATION);

        alpha2.setRepeatCount(Animation.INFINITE);
        scaleX2.setRepeatCount(Animation.INFINITE);
        scaleY2.setRepeatCount(Animation.INFINITE);

        animatorSet2 = new AnimatorSet();
        animatorSet2.setStartDelay(START_DELAY);
        animatorSet2.playTogether(alpha2, scaleX2, scaleY2);

        animatorSet2.start();
    }
}
