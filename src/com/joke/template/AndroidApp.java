package com.joke.template;

import android.app.Application;

public class AndroidApp extends Application {
	 public static Application app = null;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}