package com.example.appvendas.Helpers.Singleton;

import com.example.appvendas.Helpers.Interface.EventListener;
import com.example.appvendas.Helpers.Interface.FirebaseEventListener;

public class EventSingleton {
    public static EventSingleton instance;
    private EventListener eventListener;
    private FirebaseEventListener firebaseEventListener;

    public static EventSingleton getInstance(){
        if(instance == null){
            instance = new EventSingleton();
        }
        return instance;
    }

    public void registerEvent(EventListener eventListener){
        this.eventListener = eventListener;
    }

    public void registerFirebaseEvent(FirebaseEventListener firebaseEventListener) {
        this.firebaseEventListener = firebaseEventListener;
    }

    public void unregisterEvent(){
        this.eventListener = null;
    }

    public void emitterDone(Long aLong){
        this.eventListener.done(aLong);
    }

    public void emitterLogInDone(){
        this.firebaseEventListener.logInDone();
    }

    public void emitterLogInFailed() {
        this.firebaseEventListener.logInFailed();
    }

    public void emitterSignUpDone(boolean isSuccessfull, String signUpException) {
        this.firebaseEventListener.signUpDone(isSuccessfull, signUpException);
    }

}
