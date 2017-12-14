package com.example.kevinlay.check.preferences;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.kevinlay.check.R;

/**
 * Created by kevinlay on 12/9/17.
 */

public class PreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }
}
