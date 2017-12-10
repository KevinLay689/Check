package com.example.kevinlay.check;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
                        createFragment("nav_home");
                        break;
                    case R.id.nav_profile:
                        createFragment("nav_profile");
                        break;
                    case R.id.nav_preferences:
                        createFragment("nav_preferences");
                        break;
                    case R.id.nav_logout:
                        createFragment("nav_logout");
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

        switch (fragmentName) {
            case "nav_home":
                fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new HomeFragment(), fragmentTag);
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();
                break;
            case "nav_profile":
                fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new MyProfileFragment(), fragmentTag);
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();
                break;
            case "nav_preferences":
                fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new PreferencesFragment(), fragmentTag);
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commit();
                break;
            case "nav_logout":
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
