package com.cs380.csun.findafriend;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.content.Intent;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final Intent setup = new Intent(getApplicationContext(), InitEditProfileActivity.class);

        Thread myThread = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(3000);
                    startActivity(setup);
                    finish();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
