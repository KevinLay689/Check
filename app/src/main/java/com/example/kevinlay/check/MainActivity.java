package com.example.kevinlay.check;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.kevinlay.check.browse.BrowseFragment;
import com.example.kevinlay.check.database.DatabaseObject;
import com.example.kevinlay.check.database.DatabaseState;
import com.example.kevinlay.check.home.PairedFragment;
import com.example.kevinlay.check.login.LoginActivity;
import com.example.kevinlay.check.profile.MyProfileFragment;
import com.example.kevinlay.check.preferences.PreferencesFragment;
import com.example.kevinlay.check.home.UnpairedHomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements DatabaseState.DatabaseCallback, PairedFragment.PairedFragmentCallback{

    private static final String TAG = "MainActivity";

    private static final String NAV_HOME = "nav_home";
    private static final String NAV_BROWSE = "nav_browse";
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
    private DatabaseObject databaseObject;
    private DatabaseState databaseState;

    private PairedFragment pairedFragment;

    private SharedPreferences prefs;
    private String major;
    private Boolean isAcceptingNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pairedFragment = new PairedFragment();

        databaseObject = DatabaseObject.getInstance();
        databaseState = new DatabaseState(this);

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

        createFragment(NAV_HOME);
        setupNavigationView();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        major = prefs.getString("major", "any");

    }

    private void setupNavigationView() {

        View headerView =  mNavigationView.getHeaderView(0);
        TextView navigationUsername = (TextView)headerView.findViewById(R.id.navigation_header_text);
//        CustomHeaderView customHeaderView = (CustomHeaderView) headerView.findViewById(R.id.navigation_header_image);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        createFragment(NAV_HOME);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_browse:
                        createFragment(NAV_BROWSE);
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
                        invalidateOptionsMenu();
                        mDrawerLayout.closeDrawers();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        
        databaseObject.setUserData(navigationUsername, DatabaseObject.FIRST_NAME_REFERENCE, "");
//        databaseObject.setViewData(customHeaderView, DatabaseObject.HEADER_IMAGE_REFERENCE, "");
    }

    private void displayUserStateFragment(final FragmentTransaction fragmentTransaction, final Fragment fragment) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(DatabaseObject.USERS_REFERENCE).child(mAuth.getUid()).child(DatabaseObject.USER_STATE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String state = dataSnapshot.getValue(String.class);

                switch (state) {
                    case DatabaseObject.PAIRED_STATE:
                        if (fragment == null) {
                            fragmentTransaction.add(R.id.frameLayoutPlaceHolder, pairedFragment, FRAGMENT_TAG);
                        } else {
                            fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, pairedFragment, FRAGMENT_TAG);
                        }

                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commitAllowingStateLoss();
                        
                        break;

                    case DatabaseObject.IDLE_STATE:
                        if (fragment == null) {
                            fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new UnpairedHomeFragment(), FRAGMENT_TAG);
                        } else {
                            fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, new UnpairedHomeFragment(), FRAGMENT_TAG);
                        }

                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commitAllowingStateLoss();
                        break;

                    case DatabaseObject.ACCEPTED_STATE:
                        if (fragment == null) {
                            fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new UnpairedHomeFragment(), FRAGMENT_TAG);
                        } else {
                            fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, new UnpairedHomeFragment(), FRAGMENT_TAG);
                        }

                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commitAllowingStateLoss();
                        break;

                    case DatabaseObject.SEARCHING_STATE:
                        UnpairedHomeFragment unpairedHomeFragment = new UnpairedHomeFragment();
                        Bundle b = new Bundle();
                        b.putBoolean("matched", true);
                        unpairedHomeFragment.setArguments(b);

                        if (fragment == null) {
                            fragmentTransaction.add(R.id.frameLayoutPlaceHolder, unpairedHomeFragment, FRAGMENT_TAG);
                        } else {
                            fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, unpairedHomeFragment, FRAGMENT_TAG);
                        }

                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commitAllowingStateLoss();
                        break;

                    case DatabaseObject.MATCHED_STATE:
                        PairedFragment pairedFragment = new PairedFragment();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("matched", true);
                        pairedFragment.setArguments(bundle);


                        if (fragment == null) {
                            fragmentTransaction.add(R.id.frameLayoutPlaceHolder, pairedFragment, FRAGMENT_TAG);
                        } else {
                            fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, pairedFragment, FRAGMENT_TAG);
                        }

                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commitAllowingStateLoss();

                        break;

                    default:
                        if (fragment == null) {
                            fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new UnpairedHomeFragment(), FRAGMENT_TAG);
                        } else {
                            fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, new UnpairedHomeFragment(), FRAGMENT_TAG);
                        }

                        fragmentTransaction.addToBackStack("");
                        fragmentTransaction.commitAllowingStateLoss();
                        break;
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createFragment(String fragmentName) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        switch (fragmentName) {
            
            case NAV_HOME:
                displayUserStateFragment(fragmentTransaction, fragment);
                break;

            case NAV_BROWSE:
                if(fragment == null) {
                    fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new BrowseFragment(), FRAGMENT_TAG);
                } else {
                    fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, new BrowseFragment(), FRAGMENT_TAG);
                }

                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commitAllowingStateLoss();
                break;


            case NAV_PROFILE:
                myProfileFragment = new MyProfileFragment();
                if(fragment == null) {
                    fragmentTransaction.add(R.id.frameLayoutPlaceHolder, myProfileFragment, FRAGMENT_TAG);
                } else {
                    fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, myProfileFragment, FRAGMENT_TAG);
                }

                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commitAllowingStateLoss();
                break;

            case NAV_PREFERENCES:
                if(fragment == null) {
                    fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new PreferencesFragment(), FRAGMENT_TAG);
                } else {
                    fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, new PreferencesFragment(), FRAGMENT_TAG);
                }
                fragmentTransaction.addToBackStack("");
                fragmentTransaction.commitAllowingStateLoss();
                break;

            case NAV_LOGOUT:
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (myProfileFragment != null ) {
            myProfileFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void updateUserStateFragment(String updateType) {
        Log.i(TAG, "updateUserStateFragment: ");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG);

        switch (updateType) {
            case DatabaseObject.IDLE_STATE:
                if (fragment == null) {
                    fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new UnpairedHomeFragment(), FRAGMENT_TAG);
                } else {
                    fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, new UnpairedHomeFragment(), FRAGMENT_TAG);
                }
                break;

            case DatabaseObject.MATCHED_STATE:
                PairedFragment pairedFragment = new PairedFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("matched", true);
                pairedFragment.setArguments(bundle);

                if (fragment == null) {
                    fragmentTransaction.add(R.id.frameLayoutPlaceHolder, pairedFragment, FRAGMENT_TAG);
                } else {
                    fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, pairedFragment, FRAGMENT_TAG);
                }
                createNotification("The other user has accepted!", "Tap to view confirmation");
                break;

            case DatabaseObject.SEARCHING_STATE:
                UnpairedHomeFragment unpairedHomeFragment = new UnpairedHomeFragment();
                Bundle b = new Bundle();
                b.putBoolean("matched", true);
                unpairedHomeFragment.setArguments(b);

                if (fragment == null) {
                    fragmentTransaction.add(R.id.frameLayoutPlaceHolder, unpairedHomeFragment, FRAGMENT_TAG);
                } else {
                    fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, unpairedHomeFragment, FRAGMENT_TAG);
                }
                break;


            case DatabaseObject.PAIRED_STATE:
                if (fragment == null) {
                    fragmentTransaction.add(R.id.frameLayoutPlaceHolder, new PairedFragment(), FRAGMENT_TAG);
                } else {
                    fragmentTransaction.replace(R.id.frameLayoutPlaceHolder, new PairedFragment(), FRAGMENT_TAG);
                }
                createNotification("You've been paired up!", "Tap to view match");
                break;
        }
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void updateUI(String userState, boolean acceptClicked) {
        updateUserStateFragment(userState);
        databaseObject.updateStates(DatabaseObject.USER_STATE, userState, acceptClicked);
    }

    private void createNotification(String title, String notification) {

        final int NOTIFICATION_O_ID = 1;
        final int NOTIFICATION_PRE_O_ID = 0;

        isAcceptingNotifications = prefs.getBoolean("notifications", true);

        if(isAcceptingNotifications) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                Intent intent = new Intent(this, MainActivity.class);
                int requestID = (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId
                int flags = PendingIntent.FLAG_CANCEL_CURRENT; // cancel old intent and create new one
                PendingIntent pIntent = PendingIntent.getActivity(this, requestID, intent, flags);

                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("myChannelId", "My Channel", importance);
                channel.setDescription("Reminders");
                // Register the channel with the notifications manager
                NotificationManager mNotificationManager =
                        (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.createNotificationChannel(channel);

                NotificationCompat.Builder mBuilder =
                        // Builder class for devices targeting API 26+ requires a channel ID
                        new NotificationCompat.Builder(this, "myChannelId")
                                .setContentTitle(title)
                                .setSmallIcon(R.drawable.ic_local_dining_black_96dp)
                                .setContentText(notification)
                                .setContentIntent(pIntent)
                                .setAutoCancel(true);

                mNotificationManager.notify(NOTIFICATION_O_ID, mBuilder.build());

            } else {

                Intent intent = new Intent(this, MainActivity.class);
                int requestID = (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId
                int flags = PendingIntent.FLAG_CANCEL_CURRENT; // cancel old intent and create new one
                PendingIntent pIntent = PendingIntent.getActivity(this, requestID, intent, flags);

                NotificationCompat.Builder mBuilder =
                        // this Builder class is deprecated
                        new NotificationCompat.Builder(this)
                                .setContentTitle(title)
                                .setSmallIcon(R.drawable.ic_local_dining_black_96dp)
                                .setContentText(notification)
                                .setContentIntent(pIntent)
                                .setAutoCancel(true);

                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(NOTIFICATION_PRE_O_ID, mBuilder.build());
            }
        }
    }

    // Left empty on purpose
    @Override
    public void onSaveInstanceState(Bundle outState) {

    }
}
