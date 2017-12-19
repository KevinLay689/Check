package com.example.kevinlay.check.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinlay.check.R;
import com.example.kevinlay.check.database.DatabaseObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kevinlay on 12/9/17.
 */

public class HomeFragment extends Fragment {

    private TextView mMatchText;

    private CircleImageView mUserProfile, mOtherProfile;
    private TextView mLocation, mMeetTime, mFlakeRating;

    private DatabaseObject databaseObject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "blacklist.ttf");
        mMatchText = (TextView) view.findViewById(R.id.matchText);
        mMatchText.setTypeface(typeface);

        mUserProfile = (CircleImageView) view.findViewById(R.id.userProfile);
        mOtherProfile = (CircleImageView) view.findViewById(R.id.otherProfile);
        mLocation = (TextView) view.findViewById(R.id.locationText);
        mMeetTime = (TextView) view.findViewById(R.id.timeText);
        mMatchText = (TextView) view.findViewById(R.id.ratingText);

        databaseObject = DatabaseObject.getInstance();

        databaseObject.setUserData(mMeetTime, DatabaseObject.LUNCH_TIME_REFERENCE);
        databaseObject.getOtherProfilePic(mOtherProfile);
        databaseObject.getProfilePic(mUserProfile);
    }

    @Override
    public void onStop() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Check");
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
    }
}
