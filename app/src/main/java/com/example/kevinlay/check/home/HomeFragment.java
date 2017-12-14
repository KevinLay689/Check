package com.example.kevinlay.check.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinlay.check.R;

/**
 * Created by kevinlay on 12/9/17.
 */

public class HomeFragment extends Fragment {

    TextView mMatchText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "blacklist.ttf");
        mMatchText = (TextView) view.findViewById(R.id.matchText);
        mMatchText.setTypeface(typeface);
    }

    @Override
    public void onStop() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Check");
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("");
    }
}
