package com.example.kevinlay.check.database;

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

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private DatabaseCallback databaseCallback;

    public DatabaseState(final DatabaseCallback databaseCallback) {

        this.databaseCallback = databaseCallback;

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        databaseReference.child(DatabaseObject.USERS_REFERENCE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user.getId().equals(mAuth.getUid())) {
                   databaseCallback.updateUserStateFragment();
                }
                //Log.i(TAG, "onDataChange: " + user.getMajor());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public interface DatabaseCallback {
        void updateUserStateFragment();
    }

}
