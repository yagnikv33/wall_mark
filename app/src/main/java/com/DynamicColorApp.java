package com;

import android.app.Application;
import android.widget.Toast;

import com.google.android.material.color.DynamicColors;

public class DynamicColorApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if(DynamicColors.isDynamicColorAvailable()){
            DynamicColors.applyToActivitiesIfAvailable(this);
        }else{
            Toast.makeText(this,"Dynamic color is Not Supported",Toast.LENGTH_SHORT).show();
        }
       // DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
