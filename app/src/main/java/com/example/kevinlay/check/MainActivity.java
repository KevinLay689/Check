package com.example.kevinlay.check;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.kevinlay.check.database.DatabaseObject;
import com.example.kevinlay.check.login.LoginActivity;
import com.example.kevinlay.check.profile.MyProfileFragment;
import com.example.kevinlay.check.preferences.PreferencesFragment;
import com.example.kevinlay.check.home.UnpairedHomeFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final String NAV_HOME = "nav_home";
    private static final String NAV_PROFILE = "nav_profile";
    private static final String NAV_PREFERENCES = "nav_preferences";
    private static final String NAV_LOGOUT = "nav_logout";
    private static final String FRAGMENT_TAG = "fragmentTag";

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private FragmentManager fragmentManager;

    private MyProfileFragment myProfileFragment;
    DatabaseObject databaseObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseObject = DatabaseObject.getInstance();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mNavigationView.setItemIconTintList(null);

        // Setup navigation bar and toolbar
        setSupportActionBar(mToolbar);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_menu, R.string.close_menu);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new UnpairedHomeFragment(), FRAGMENT_TAG);
        fragmentTransaction.commit();
        setupNavigationView();


    }

    private void setupNavigationView() {

        View headerView =  mNavigationView.getHeaderView(0);
//      ImageView navigationUserImage = (ImageView) headerView.findViewById(R.id.navigation_header_image);
        TextView navigationUsername = (TextView)headerView.findViewById(R.id.navigation_header_text);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        createFragment(NAV_HOME);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_profile:
                        createFragment(NAV_PROFILE);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_preferences:
                        createFragment(NAV_PREFERENCES);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_logout:
                        createFragment(NAV_LOGOUT);
                        mDrawerLayout.closeDrawers();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        databaseObject.setUserData(navigationUsername, DatabaseObject.FIRST_NAME_REFERENCE);

    }

    private void createFragment(String fragmentName) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        switch (fragmentName) {
            case NAV_HOME:
                if(fragment == null) {
                    fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new UnpairedHomeFragment(), FRAGMENT_TAG);
                } else {
                    fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, new UnpairedHomeFragment(), FRAGMENT_TAG);
                }

                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();
                break;

            case NAV_PROFILE:
                myProfileFragment = new MyProfileFragment();
                if(fragment == null) {
                    fragmentTransaction.add(R.id.frameLayoutPlaceHolder, myProfileFragment, FRAGMENT_TAG);
                } else {
                    fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, myProfileFragment, FRAGMENT_TAG);
                }

                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();
                break;

            case NAV_PREFERENCES:
                if(fragment == null) {
                    fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new PreferencesFragment(), FRAGMENT_TAG);
                } else {
                    fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, new PreferencesFragment(), FRAGMENT_TAG);
                }
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();
                break;

            case NAV_LOGOUT:

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);

//                if(fragment == null) {
//                    fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new HomeFragment(), FRAGMENT_TAG);
//                } else {
//                    fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, new HomeFragment(), FRAGMENT_TAG);
//                }
//                fragmentTransaction.addToBackStack("");
//                fragmentTransaction.commit();
//                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (myProfileFragment != null ) {
            myProfileFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
