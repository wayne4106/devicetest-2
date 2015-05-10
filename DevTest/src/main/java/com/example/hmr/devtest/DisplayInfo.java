package com.example.hmr.devtest;

import android.app.Activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.widget.TextView;


/**
 * Created by hmr on 24-04-2015.
 */
public class DisplayInfo extends Activity{
    private TelephonyManager phoneManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView info = (TextView)findViewById(R.id.textView);
        phoneManager  = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        info.setText("S/N: " + Build.SERIAL + "\nIMEI1: " + phoneManager.getDeviceId()+"\nModelo: "+getDeviceName());
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }
    private String capitalize(String s) {
        if (s == null || s.length() == 0) return "";
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
