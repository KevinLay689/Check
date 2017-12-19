package com.example.kevinlay.check.database;

import android.util.Log;

import com.example.kevinlay.check.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by kevinlay on 12/18/17.
 */

public class DatabaseState {

    private static final String TAG = "DatabaseState";

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private DatabaseCallback databaseCallback;

    public DatabaseState(final DatabaseCallback databaseCallback) {

        this.databaseCallback = databaseCallback;

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        databaseReference.child(DatabaseObject.USERS_REFERENCE).child(mAuth.getUid()).child(DatabaseObject.USER_STATE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String state = dataSnapshot.getValue(String.class);

                Log.i(TAG, "onDataChange: " + state);
                databaseCallback.updateUserStateFragment(state);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void changeData(String newData) {

        databaseReference.child(DatabaseObject.USERS_REFERENCE)
                .child(mAuth.getUid())
                .child(DatabaseObject.USER_STATE)
                .setValue(newData);
    }

    public interface DatabaseCallback {
        void updateUserStateFragment(String updateType);
    }

}
