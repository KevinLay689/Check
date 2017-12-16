package com.example.kevinlay.check.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

/**
 * Created by kevinlay on 12/9/17.
 */

public class MyProfileFragment extends Fragment implements EditProfileFragment.EditDialogListener {

    private TextView mTextMajor, mTextAboutMe, mTextHometown;
    private ImageView mImageProfilePic;
    private FloatingActionButton mFloatingActionButton;
    private DatabaseObject object;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        object = DatabaseObject.getInstance();

        mImageProfilePic = (ImageView) view.findViewById(R.id.profileUserImage);

        mTextMajor = (TextView) view.findViewById(R.id.profileUserMajor);
        mTextAboutMe = (TextView) view.findViewById(R.id.profileUserAboutMe);
        mTextHometown = (TextView) view.findViewById(R.id.profileUserHometown);

        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.editProfileActionButton);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
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

    // This allows a callback to the dialog fragment
    private void showEditDialog() {
        FragmentManager fm = getFragmentManager();
        EditProfileFragment editNameDialogFragment = EditProfileFragment.newInstance("Some Title");
        // SETS the target fragment for use later when sending results
        editNameDialogFragment.setTargetFragment(MyProfileFragment.this, 300);
        editNameDialogFragment.show(fm, "fragment_edit_name");
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

    // This will eventually just update the UI page
    // Data will be saved in the edit profile dialog fragment,
    // then this will pull the new data from firebase
    @Override
    public void onFinishEditDialog(String inputText) {
        Toast.makeText(getActivity(), inputText, Toast.LENGTH_SHORT).show();
        object.changeData(DatabaseObject.MAJOR_REFERENCE, inputText);
    }
}
