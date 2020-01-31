package com.example.appvendas.Helpers.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appvendas.Entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseSingleton {

    private static FirebaseSingleton INSTANCE;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ValueEventListener mValueEventListener;
    private ChildEventListener mChildEventListener;
    private User user;

    private FirebaseSingleton() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        attatchFirebaseAuthStateListener();
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

    public static FirebaseSingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (FirebaseSingleton.class) {
                if (INSTANCE == null)
                    INSTANCE = new FirebaseSingleton();
            }
        }
        return INSTANCE;
    }

    public void signIn(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    EventSingleton eventSingleton = EventSingleton.getInstance();
                    eventSingleton.emitterDone(task.isSuccessful());
                }
            }
        });
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void attatchDatabaseReferenceReadListener() {
        if (mValueEventListener == null) {
            mValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mDatabaseReference.addValueEventListener(mValueEventListener);
        }
    }

    public void attatchFirebaseAuthStateListener() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null) {
                    signedInInitialize();
                }
            }
        };
    }

    public void initializeAuthStateListener() {
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void signedInInitialize() {

        if (mValueEventListener == null) {
            mValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    saveCurrentUser(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mDatabaseReference.addValueEventListener(mValueEventListener);
        }
    }

    public void dettatchDatabaseValueEventListener() {
        if (mValueEventListener != null) {
            mDatabaseReference.removeEventListener(mValueEventListener);
            mValueEventListener = null;
        }
    }

    private void saveCurrentUser(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            user = new User(
                    ds.child(mFirebaseAuth.getCurrentUser().getUid()).getValue(User.class).getUserName(),
                    ds.child(mFirebaseAuth.getCurrentUser().getUid()).getValue(User.class).getUserPassword(),
                    ds.child(mFirebaseAuth.getCurrentUser().getUid()).getValue(User.class).getUserEmail()
            );

            if (ds.child(mFirebaseAuth.getCurrentUser().getUid()).getValue(User.class).getUserCPF() != null) {
                user.setUserCPF(ds.child(mFirebaseAuth.getCurrentUser().getUid()).getValue(User.class).getUserCPF());
            }
            if (ds.child(mFirebaseAuth.getCurrentUser().getUid()).getValue(User.class).getUserPhoneNumber() != null) {
                user.setUserPhoneNumber(ds.child(mFirebaseAuth.getCurrentUser().getUid()).getValue(User.class).getUserPhoneNumber());
            }
            if (ds.child(mFirebaseAuth.getCurrentUser().getUid()).getValue(User.class).getUserBirthday() != null) {
                user.setUserBirthday(ds.child(mFirebaseAuth.getCurrentUser().getUid()).getValue(User.class).getUserBirthday());
            }
            if (ds.child(mFirebaseAuth.getCurrentUser().getUid()).getValue(User.class).getUserGender() != null) {
                user.setUserGender(ds.child(mFirebaseAuth.getCurrentUser().getUid()).getValue(User.class).getUserGender());
            }
        }
        EventSingleton eventSingleton = EventSingleton.getInstance();
        eventSingleton.emitterDone(true);
    }

    private void onSignedOutCleanup() {
        user = null;
        dettatchDatabaseValueEventListener();
    }

    public String getCurrentUserName() {
        return user.getUserName();
    }

    public User getCurrentUser() {
        return user;
    }

}
