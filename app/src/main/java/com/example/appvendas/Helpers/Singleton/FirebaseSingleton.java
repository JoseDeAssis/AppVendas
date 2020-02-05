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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FirebaseSingleton {

    private static FirebaseSingleton INSTANCE;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    private ValueEventListener mUserValueEventListener;
    private ChildEventListener mChildEventListener;
    private EventSingleton eventSingleton;
    private User user;

    private FirebaseSingleton() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
        eventSingleton = EventSingleton.getInstance();
        mFirebaseAuth.signOut();
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

    public void signIn(final String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    eventSingleton.emitterLogInFailed();
                    System.out.println(task.getException() + "");
                } else {
                    getUserData();
                }
            }
        });
    }

    public void getUserData() {
        Query query = mFirebaseDatabase.getReference("Users").child(getCurrentUser().getUid());
        createUserValueEventListener();
        query.addValueEventListener(mUserValueEventListener);
    }

    public void signUp(final String email, final String password, final String fullName, final String gender,
                       final String cpf, final String phoneNumber, final String birthday) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fullName, password, email);
                            if (cpf != null) {
                                user.setUserCPF(cpf);
                            } else {
                                user.setUserCPF("");
                            }

                            if (phoneNumber != null) {
                                user.setUserPhoneNumber(phoneNumber);
                            } else {
                                user.setUserPhoneNumber("");
                            }

                            if (birthday != null) {
                                user.setUserBirthday(birthday);
                            } else {
                                user.setUserBirthday("");
                            }

                            mFirebaseDatabase.getReference("Users").child(getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    eventSingleton.emitterSignUpDone(task.isSuccessful(), task.getException() + "");
                                }
                            });
                        } else {
                            eventSingleton.emitterSignUpDone(task.isSuccessful(), task.getException() + "");
                        }
                    }
                });
    }

    private void createUserValueEventListener() {

        if (mUserValueEventListener == null) {
            mUserValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    saveCurrentUser(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
        }
    }


    private void saveCurrentUser(DataSnapshot dataSnapshot) {
        user = new User(
                dataSnapshot.getValue(User.class).getUserName(),
                dataSnapshot.getValue(User.class).getUserPassword(),
                dataSnapshot.getValue(User.class).getUserEmail()
        );

        if (dataSnapshot.getValue(User.class).getUserCPF() != null) {
            user.setUserCPF(dataSnapshot.getValue(User.class).getUserCPF());
        }
        if (dataSnapshot.getValue(User.class).getUserPhoneNumber() != null) {
            user.setUserPhoneNumber(dataSnapshot.getValue(User.class).getUserPhoneNumber());
        }
        if (dataSnapshot.getValue(User.class).getUserBirthday() != null) {
            user.setUserBirthday(dataSnapshot.getValue(User.class).getUserBirthday());
        }
        if (dataSnapshot.getValue(User.class).getUserGender() != null) {
            user.setUserGender(dataSnapshot.getValue(User.class).getUserGender());
        }

        eventSingleton.emitterLogInDone();
    }

    public String getCurrentUserName() {
        return user.getUserName();
    }

    public FirebaseUser getCurrentUser() {
        return mFirebaseAuth.getCurrentUser();
    }

}
