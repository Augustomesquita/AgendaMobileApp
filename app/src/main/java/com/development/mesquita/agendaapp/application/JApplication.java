package com.development.mesquita.agendaapp.application;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by augusto on 29/08/17.
 */

public class JApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
