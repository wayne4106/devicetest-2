package com.example.hmr.devtest;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;


public class OrientationTest extends Activity implements SensorEventListener{

    private static final float POSITIVE_OFFSET = 7;
    private static final float NEGATIVE_OFFSET = -7;
    private TextView tv;
    SensorManager mSensorManager;
    private Sensor mSensor;
    private float []values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation_test);
        tv = (TextView)findViewById(R.id.orientationLabel);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        values = new float[4];
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(values[0] > POSITIVE_OFFSET)
            tv.setText("ORIENTATION RIGHT LANDSCAPE");
        if(values[0]< NEGATIVE_OFFSET)
            tv.setText("ORIENTATION LEFT LANDSCAPE");
        if(values[1] > POSITIVE_OFFSET)
            tv.setText("ORIENTATION NORMAL PORTRAIT");
        if(values[1] < NEGATIVE_OFFSET)
            tv.setText("ORIENTATION REVERSE PORTRAIT");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        values[0]=event.values[0];
        values[1]=event.values[1];
        values[2]=event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
