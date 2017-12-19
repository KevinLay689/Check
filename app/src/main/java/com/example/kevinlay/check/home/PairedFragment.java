package com.example.kevinlay.check.home;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kevinlay.check.R;
import com.example.kevinlay.check.database.DatabaseObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kevinlay on 12/9/17.
 */

public class PairedFragment extends Fragment {

    private TextView mMatchText, mLocation, mMeetTime, mFlakeRating;;
    private Button mAccept, mDecline;
    private CircleImageView mUserProfile, mOtherProfile;

    private DatabaseObject databaseObject;
    private PairedFragmentCallback pairedFragmentCallback;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paired, container, false);
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
        mFlakeRating = (TextView) view.findViewById(R.id.ratingText);

        mAccept = (Button) view.findViewById(R.id.acceptButton);
        mDecline = (Button) view.findViewById(R.id.declineButton);

        mDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //databaseObject.changeData(DatabaseObject.USER_STATE, DatabaseObject.IDLE_STATE);
                pairedFragmentCallback.updateUI(DatabaseObject.IDLE_STATE, false);
            }
        });

        mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //databaseObject.changeData(DatabaseObject.USER_STATE, DatabaseObject.ACCEPTED_STATE);
                pairedFragmentCallback.updateUI(DatabaseObject.ACCEPTED_STATE, true);
                mAccept.setText("Awaiting other Response...");
                mAccept.setEnabled(false);
            }
        });

        databaseObject = DatabaseObject.getInstance();

        databaseObject.setUserData(mLocation, DatabaseObject.LOCATION_REFERENCE, mLocation.getText().toString());
        databaseObject.setUserData(mMeetTime, DatabaseObject.LUNCH_TIME_REFERENCE, mMeetTime.getText().toString());
        databaseObject.setUserData(mFlakeRating, DatabaseObject.FLAKE_RATING_REFERENCE, mFlakeRating.getText().toString());
        databaseObject.getOtherProfilePic(mOtherProfile);
        databaseObject.getProfilePic(mUserProfile);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PairedFragmentCallback) {
            pairedFragmentCallback = (PairedFragmentCallback) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
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
    
    public interface PairedFragmentCallback {
        void updateUI(String userState, boolean updateOtherUser);
    }
}
