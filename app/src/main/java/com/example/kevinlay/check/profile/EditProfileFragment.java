package com.example.kevinlay.check.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.kevinlay.check.R;
import com.example.kevinlay.check.database.DatabaseObject;

/**
 * Created by kevinlay on 12/11/17.
 */

public class EditProfileFragment extends DialogFragment {

    private EditText mMajor, mAboutMe, mHometown;
    private Button mSaveProfile;
    private DatabaseObject databaseObject;

    public EditProfileFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseObject = DatabaseObject.getInstance();

        mMajor = (EditText) view.findViewById(R.id.majorEdit);
        mAboutMe = (EditText) view.findViewById(R.id.aboutMeEdit);
        mHometown = (EditText) view.findViewById(R.id.hometownEdit);

        mSaveProfile = (Button) view.findViewById(R.id.saveProfile);
        mSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBackResult();
            }
        });

        databaseObject.setProfileUserData(mMajor, mAboutMe, mHometown);
    }

    public static EditProfileFragment newInstance(String title) {
        EditProfileFragment frag = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    public interface EditDialogListener {
        void onFinishEditDialog(String majorText, String aboutMeText, String hometownText);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackResult() {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        EditDialogListener listener = (EditDialogListener) getTargetFragment();
        listener.onFinishEditDialog(mMajor.getText().toString(), mAboutMe.getText().toString(), mHometown.getText().toString());
        dismiss();
    }
}
