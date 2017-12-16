package com.example.kevinlay.check.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kevinlay.check.R;
import com.example.kevinlay.check.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity{

    private static final String TAG = "EmailPassword";

    private EditText mEmailField, mPasswordField, mFirstName, mLastName, mHometown, mMajor;
    private Button mCreateAccount;
    private ProgressBar mProgressBar;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirebaseApp.initializeApp(this);


        //Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Views
        mFirstName = findViewById(R.id.loginFirstName);
        mLastName = findViewById(R.id.loginLastName);
        mHometown = findViewById(R.id.loginHometown);
        mMajor = findViewById(R.id.loginMajor);
        mEmailField = findViewById(R.id.loginUsername);
        mPasswordField = findViewById(R.id.loginPassword);
        mProgressBar = findViewById(R.id.progressBar3);

        mCreateAccount = findViewById(R.id.createAccount);

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(mFirstName.getText().toString(),
                        mLastName.getText().toString(),
                        mHometown.getText().toString(),
                        mMajor.getText().toString(),
                        mEmailField.getText().toString(),
                        mPasswordField.getText().toString());
            }
        });
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    // [END on_start_check_user]

    private void createAccount(final String firstName, final String lastName,
                               final String homeTown, final String major, final String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);

        if(mAuth == null) {
            Log.e(TAG, "createAccount: Error mauth is null" );
        }
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                            //Toast.makeText(getApplicationContext(), "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                            String uID = currentFirebaseUser.getUid();
                            insertUserIntoDatabase(firstName, lastName, homeTown, major, email, uID);
                            goToDashboard();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
        // [END create_user_with_email]
    }

    private void insertUserIntoDatabase(String firstName, String lastName, String homeTown, String major, String email, String id){
       User user = new User(firstName, lastName, homeTown, major, email);
       databaseReference.child("users").child(id).setValue(user);
    }


    private void goToDashboard() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean validateForm() {
        boolean valid = true;

        String firstName = mFirstName.getText().toString();
        if (TextUtils.isEmpty(firstName)) {
            mFirstName.setError("Required.");
            valid = false;
        } else {
            mFirstName.setError(null);
        }

        String lastName = mLastName.getText().toString();
        if (TextUtils.isEmpty(lastName)) {
            mLastName.setError("Required.");
            valid = false;
        } else {
            mLastName.setError(null);
        }

        String hometown = mHometown.getText().toString();
        if (TextUtils.isEmpty(hometown)) {
            mHometown.setError("Required.");
            valid = false;
        } else {
            mHometown.setError(null);
        }

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }
}