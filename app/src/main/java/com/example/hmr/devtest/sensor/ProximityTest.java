package com.example.hmr.devtest.sensor;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hmr.devtest.R;


/**
 * Created by hmr on 24-04-2015.
 */
public class ProximityTest extends Activity implements SensorEventListener{
    private static final String PASS_MSG = "PASS";
    private static final int BACK_COLOR = Color.rgb(32,32,32);
    private static final int FRONT_COLOR = Color.GREEN;
    private SensorManager mSensorManager;
    private android.hardware.Sensor mSensor;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_PROXIMITY);
        tv = (TextView)findViewById(R.id.sensor_result);
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
    public void onSensorChanged(SensorEvent event) {
        if(event.values[0] == 0) {
            tv.setText(PASS_MSG);
            tv.setTextColor(BACK_COLOR);
            tv.setBackgroundColor(FRONT_COLOR);
        }
        else{
            tv.setTextColor(FRONT_COLOR);
            tv.setBackgroundColor(BACK_COLOR);
        }
    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {

    }
}
