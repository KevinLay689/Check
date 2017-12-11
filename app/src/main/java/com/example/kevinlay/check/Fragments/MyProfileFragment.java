package com.example.kevinlay.check.Fragments;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinlay.check.R;

/**
 * Created by kevinlay on 12/9/17.
 */

public class MyProfileFragment extends Fragment implements EditProfileFragment.EditDialogListener{

    private TextView mTextMajor, mTextAboutMe, mTextHometown;
    private ImageView mImageProfilePic;
    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mImageProfilePic = (ImageView) view.findViewById(R.id.profileUserImage);

        mTextMajor = (TextView) view.findViewById(R.id.profileUserMajor);
        mTextAboutMe = (TextView) view.findViewById(R.id.profileUserAboutMe);
        mTextHometown = (TextView) view.findViewById(R.id.profileUserHometown);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.editProfileActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        setInitialData();

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        boolean strUserName = sharedPreferences.getBoolean("notifications", false);
//        String downloadType = sharedPreferences.getString("major","none");
//        Toast.makeText(getActivity(),
//                "Notifications :" + strUserName + " Major :" + downloadType,
//                Toast.LENGTH_SHORT).show();
    }

    private void showAlertDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        EditProfileFragment alertDialog = EditProfileFragment.newInstance("Some title");
        alertDialog.show(fm, "fragment_alert");
    }

    private void setInitialData() {
//        Uncomment these when database is setup and returns data
//        mTextMajor.setText(database.getUsername);
//        mTextAboutMe.setText(database.getAboutMe);
//        mTextHometown.setText(database.getHometown);

//        mImageProfilePic.setImageBitmap(database.getProfileImage());
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        Toast.makeText(getActivity(), "Data passed back", Toast.LENGTH_SHORT).show();
    }
}
