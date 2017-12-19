package com.example.kevinlay.check.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinlay.check.R;
import com.example.kevinlay.check.database.DatabaseObject;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kevinlay on 12/9/17.
 */

public class MyProfileFragment extends Fragment implements EditProfileFragment.EditDialogListener {

    private TextView mTextMajor, mTextAboutMe, mTextHometown, mProfileUsername;
    private CircleImageView mUserProfileImage;
    private FloatingActionButton mFloatingActionButton;
    private DatabaseObject databaseObject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseObject = DatabaseObject.getInstance();

        mTextMajor = (TextView) view.findViewById(R.id.profileUserMajor);
        mTextAboutMe = (TextView) view.findViewById(R.id.profileUserAboutMe);
        mTextHometown = (TextView) view.findViewById(R.id.profileUserHometown);
        mProfileUsername = (TextView) view.findViewById(R.id.profileUsername);

        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.editProfileActionButton);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
            }
        });

        mUserProfileImage = view.findViewById(R.id.editProfileImage);

        mUserProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Image> images = new ArrayList<>();
                ImagePicker.with(getActivity())                         //  Initialize ImagePicker with activity or fragment context
                        .setToolbarColor("#212121")         //  Toolbar color
                        .setStatusBarColor("#000000")       //  StatusBar color (works with SDK >= 21  )
                        .setToolbarTextColor("#FFFFFF")     //  Toolbar text color (Title and Done button)
                        .setToolbarIconColor("#FFFFFF")     //  Toolbar icon color (Back and Camera button)
                        .setProgressBarColor("#4CAF50")     //  ProgressBar color
                        .setBackgroundColor("#212121")      //  Background color
                        .setCameraOnly(false)               //  Camera mode
                        .setMultipleMode(true)              //  Select multiple images or single image
                        .setFolderMode(true)                //  Folder mode
                        .setShowCamera(true)                //  Show camera button
                        .setFolderTitle("Albums")           //  Folder title (works with FolderMode = true)
                        .setImageTitle("Galleries")         //  Image title (works with FolderMode = false)
                        .setDoneTitle("Done")               //  Done button title
                        .setLimitMessage("You have reached selection limit")    // Selection limit message
                        .setMaxSize(1)                     //  Max images can be selected
                        .setSavePath("ImagePicker")         //  Image capture folder name
                        .setSelectedImages(images)          //  Selected images
                        .setKeepScreenOn(true)              //  Keep screen on when selecting images
                        .start();
            }
        });

        setUserData();

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

    private void setUserData() {
        databaseObject.setProfileUserData(mTextMajor, mTextAboutMe, mTextHometown);
        databaseObject.getProfilePic(mUserProfileImage);
        databaseObject.setUserData(mProfileUsername, DatabaseObject.FIRST_NAME_REFERENCE);
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
    public void onFinishEditDialog(String majorText, String aboutMeText, String hometownText) {
        Toast.makeText(getActivity(), "Data updated", Toast.LENGTH_SHORT).show();
        databaseObject.changeData(DatabaseObject.MAJOR_REFERENCE, majorText);
        databaseObject.changeData(DatabaseObject.ABOUT_ME_REFERENCE, aboutMeText);
        databaseObject.changeData(DatabaseObject.HOMETOWN_REFERENCE, hometownText);

        setUserData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images2 = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            //addImageButton.setText("Image Added: " + images2.get(0).getName());

            Bitmap bm = BitmapFactory.decodeFile(images2.get(0).getPath());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();

            Toast.makeText(getActivity(),"Image added to database", Toast.LENGTH_SHORT).show();

            String encodedPicture = Base64.encodeToString(b, Base64.DEFAULT);

            databaseObject.setProfilePic(encodedPicture);
            databaseObject.getProfilePic(mUserProfileImage);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
