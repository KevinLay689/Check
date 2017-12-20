package com.example.kevinlay.check.home;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.example.kevinlay.check.R;
import com.example.kevinlay.check.profile.MyProfileFragment;

public class OtherProfileActivity extends AppCompatActivity {

    private FrameLayout mFragmentContainer;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);

        mFragmentContainer = findViewById(R.id.fragmentContainer);
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        MyProfileFragment myProfileFragment = new MyProfileFragment();
        Bundle items = new Bundle();
        items.putBoolean("isLaunchedFromPairedFragment", true);
        myProfileFragment.setArguments(items);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, myProfileFragment);
        fragmentTransaction.commit();

    }
}
