package com.example.appvendas.Helpers.Singleton;

import com.example.appvendas.Helpers.Interface.EventListener;

public class EventSingleton {
    public static EventSingleton instance;
    private EventListener eventListener;

    public static EventSingleton getInstance(){
        if(instance == null){
            instance = new EventSingleton();
        }
        return instance;
    }

    public void registerEvent(EventListener eventListener){
        this.eventListener = eventListener;
    }

    public void unregisterEvent(){
        this.eventListener = null;
    }

    public void emitterDone(Long aLong){
        this.eventListener.done(aLong);
    }

}
