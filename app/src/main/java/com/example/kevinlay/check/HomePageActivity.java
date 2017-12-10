package com.example.kevinlay.check;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinlay.check.Fragments.HomeFragment;
import com.example.kevinlay.check.Fragments.MyProfileFragment;
import com.example.kevinlay.check.Fragments.PreferencesFragment;

public class HomePageActivity extends AppCompatActivity {

    private static final String NAV_HOME = "nav_home";
    private static final String NAV_PROFILE = "nav_profile";
    private static final String NAV_PREREFENCES = "nav_preferences";
    private static final String NAV_LOGOUT = "nav_logout";

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);

        // Setup navigation bar and toolbar
        setSupportActionBar(mToolbar);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_menu, R.string.close_menu);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentManager = getSupportFragmentManager();

        // closes nav drawer every time there is a click

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
                        createFragment(NAV_PREREFENCES);
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
        navigationUsername.setText("Kevin L.");
    }

    private void createFragment(String fragmentName) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String fragmentTag = "fragmentTag";
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        switch (fragmentName) {
            case NAV_HOME:
                if(fragment == null) {
                    fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new HomeFragment(), fragmentTag);
                } else {
                    fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, new HomeFragment(), fragmentTag);
                }

                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();
                break;

            case NAV_PROFILE:
                if(fragment == null) {
                    fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new MyProfileFragment(), fragmentTag);
                } else {
                    fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, new MyProfileFragment(), fragmentTag);
                }

                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();
                break;

            case NAV_PREREFENCES:
                if(fragment == null) {
                    fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new PreferencesFragment(), fragmentTag);
                } else {
                    fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, new PreferencesFragment(), fragmentTag);
                }
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();
                break;

            case NAV_LOGOUT:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
