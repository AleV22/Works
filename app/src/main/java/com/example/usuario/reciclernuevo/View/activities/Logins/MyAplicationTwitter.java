package com.example.usuario.reciclernuevo.View.activities.Logins;

import android.app.Application;

import com.twitter.sdk.android.core.Twitter;

public class MyAplicationTwitter extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Twitter.initialize(this);
    }
}
