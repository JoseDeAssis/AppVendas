package com.example.appvendas.Helpers.Interface;

public interface FirebaseEventListener {
    void signUpDone(boolean isSuccessfull, String signUpException);
    void logInDone();
    void logInFailed();
}
