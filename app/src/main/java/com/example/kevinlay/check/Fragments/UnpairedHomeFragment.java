package com.example.kevinlay.check.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kevinlay.check.R;

/**
 * Created by kevinlay on 12/12/17.
 */

public class UnpairedHomeFragment extends Fragment {

    private static final String TAG = "UnpairedHomeFragment";
    private final int ANIMATION_DURATION = 2000;
    private final float SCALE_ANIMATION_END = 3f;

    private Button mSelectStartTime, mSelectEndTime, mFindPartner, mCancelSearch;
    private TextView mTimeStart, mTimeEnd;
    private ImageView mUserProfilePicture ,mRingImage1 ,mRingImage2;
    private AnimatorSet animatorSet, animatorSet2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_unpaired_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSelectStartTime = (Button) view.findViewById(R.id.selectStartTime);
        mSelectEndTime = (Button) view.findViewById(R.id.selectEndTime);
        mFindPartner = (Button) view.findViewById(R.id.findPartner);
        mCancelSearch = (Button) view.findViewById(R.id.cancelPartner);

        mTimeStart = (TextView) view.findViewById(R.id.timeStartLabel);
        mTimeEnd = (TextView) view.findViewById(R.id.timeEndLabel);

        mUserProfilePicture = (ImageView) view.findViewById(R.id.userProfileImage);
        mRingImage1 = (ImageView) view.findViewById(R.id.ringImage1);
        mRingImage2 = (ImageView) view.findViewById(R.id.ringImage2);

        setupOnClickListeners();
    }

    private void setupOnClickListeners() {
        mSelectStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectStartTime.setVisibility(View.INVISIBLE);
                mSelectEndTime.setVisibility(View.VISIBLE);
            }
        });
        mSelectEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectEndTime.setVisibility(View.INVISIBLE);
                mFindPartner.setVisibility(View.VISIBLE);
                mCancelSearch.setVisibility(View.VISIBLE);
            }
        });
        mFindPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimations();
                mFindPartner.setEnabled(false);
            }
        });
        mCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectStartTime.setVisibility(View.VISIBLE);
                mFindPartner.setVisibility(View.INVISIBLE);
                mFindPartner.setEnabled(true);
                mCancelSearch.setVisibility(View.INVISIBLE);
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

    private void startAnimations() {

        ObjectAnimator alpha = ObjectAnimator.ofFloat(mRingImage1, View.ALPHA, .5f, 0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mRingImage1, View.SCALE_X, SCALE_ANIMATION_END);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mRingImage1, View.SCALE_Y, SCALE_ANIMATION_END);
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
        ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(mRingImage2, View.SCALE_X, SCALE_ANIMATION_END);
        ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(mRingImage2, View.SCALE_Y, SCALE_ANIMATION_END);
        alpha2.setDuration(ANIMATION_DURATION);
        scaleX2.setDuration(ANIMATION_DURATION);
        scaleY2.setDuration(ANIMATION_DURATION);

        alpha2.setRepeatCount(Animation.INFINITE);
        scaleX2.setRepeatCount(Animation.INFINITE);
        scaleY2.setRepeatCount(Animation.INFINITE);

        animatorSet2 = new AnimatorSet();
        animatorSet2.setStartDelay(ANIMATION_DURATION);
        animatorSet2.playTogether(alpha2, scaleX2, scaleY2);

        animatorSet2.start();
    }
}
