package com.example.kevinlay.check.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.kevinlay.check.R;
import com.example.kevinlay.check.database.DatabaseObject;
import com.example.kevinlay.check.profile.MyProfileFragment;

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

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paired, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bundle bundle = getArguments();
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "blacklist.ttf");

        mMatchText = (TextView) view.findViewById(R.id.matchText);
        mMatchText.setTypeface(typeface);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        mUserProfile = (CircleImageView) view.findViewById(R.id.userProfile);
        mOtherProfile = (CircleImageView) view.findViewById(R.id.otherProfile);
        mLocation = (TextView) view.findViewById(R.id.locationText);
        mMeetTime = (TextView) view.findViewById(R.id.timeText);
        //mFlakeRating = (TextView) view.findViewById(R.id.ratingText);

        mAccept = (Button) view.findViewById(R.id.acceptButton);
        mDecline = (Button) view.findViewById(R.id.declineButton);

        if(bundle != null) {
            mAccept.setEnabled(false);
            mAccept.setText(R.string.confirm_lunch);
            mDecline.setText(R.string.flake);
        }

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
                mAccept.setText(R.string.awaiting_response);
                mAccept.setEnabled(false);
            }
        });

        databaseObject = DatabaseObject.getInstance();

        databaseObject.setUserData(mLocation, DatabaseObject.LOCATION_REFERENCE, mLocation.getText().toString());
        databaseObject.setUserData(mMeetTime, DatabaseObject.LUNCH_TIME_REFERENCE, mMeetTime.getText().toString());
        //databaseObject.setUserData(mFlakeRating, DatabaseObject.FLAKE_RATING_REFERENCE, mFlakeRating.getText().toString());
        databaseObject.getOtherProfilePic(mOtherProfile);
        databaseObject.getProfilePic(mUserProfile, progressDialog);

        mOtherProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), OtherProfileActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PairedFragmentCallback) {
            pairedFragmentCallback = (PairedFragmentCallback) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement PairedFragment.Listener");
        }
    }

    @Override
    public void onStop() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
    }
    
    public interface PairedFragmentCallback {
        void updateUI(String userState, boolean acceptClicked);
    }
}
