package com.cerkerli.taglayout;

import android.app.Application;

import com.cerkerli.library.utils.Library;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Library.init(getApplicationContext());
    }
}
