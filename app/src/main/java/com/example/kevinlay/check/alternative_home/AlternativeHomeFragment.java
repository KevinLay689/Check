package com.example.kevinlay.check.alternative_home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinlay.check.R;
import com.example.kevinlay.check.database.DatabaseObject;
import com.example.kevinlay.check.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevinlay on 12/15/17.
 */

public class AlternativeHomeFragment extends Fragment implements AlternativeHomeAdapter.OnItemClicked {

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private DatabaseObject databaseObject;
    private ArrayList<User> users = new ArrayList<>();
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alternative_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        recyclerView = (RecyclerView) view.findViewById(R.id.browseRecyclerView);
        textView = (TextView) view.findViewById(R.id.searchingText);

        databaseReference.child(DatabaseObject.USERS_REFERENCE).child(mAuth.getUid()).child(DatabaseObject.USER_STATE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String state = dataSnapshot.getValue(String.class);

                if(state.equals(DatabaseObject.IDLE_STATE)) {
                    populateRecyclerView();
                } else {
                    textView.setText("Cannot view profiles while looking for partners or while paired up. Stop search or end match and return to view profiles.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void populateRecyclerView() {

        databaseReference.child(DatabaseObject.USERS_REFERENCE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    if(user.getUserState().equals(DatabaseObject.SEARCHING_STATE)) {
                        users.add(user);
                    }
                }

                AlternativeHomeAdapter adapter = new AlternativeHomeAdapter(users);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter.setOnClick(AlternativeHomeFragment.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "clicked position " + position, Toast.LENGTH_SHORT).show();
    }
}
