package com.example.hmr.devtest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainTest extends ActionBarActivity {
    private static final int TEST_SOUND1 = R.raw.acdc_sample;
    private static final int TEST_SOUND2 = R.raw.acdc_sample;
    private static final long VIBRA_TIME = 2000;
    private int testSound;
    private MediaPlayer mp = null;
    private AudioManager am;
    private Vibrator vibra;
    private Camera camera = null;
    private Camera.Parameters camParameters=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        am.setMode(AudioManager.STREAM_MUSIC);
        vibra = (Vibrator)getSystemService(VIBRATOR_SERVICE);

        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            camera = Camera.open();
            if(camera != null) {
                camParameters = camera.getParameters();
                ledOff();
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        ledOff();
    }

    @Override
    protected void onStop(){
        super.onStop();
        ledOff();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void testLs(View view) {
        Intent intent = new Intent(this, SensorTest.class);
        startActivity(intent);
    }

    public void playSound(View view) {
        if(mp == null) {
            mp = MediaPlayer.create(this, testSound);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                   mp.seekTo(0);
                }
            });
        }
        if(!mp.isPlaying())
            mp.start();
        else {
            mp.reset();
            mp.release();
            mp = null;
        }
    }

    public void playSoundOnSpeaker(View view) {
        testSound = TEST_SOUND1;
        am.setSpeakerphoneOn(true);
        playSound(view);
    }

    public void playSoundOnReceiver(View view) {
        testSound = TEST_SOUND2;
        am.setSpeakerphoneOn(false);
        playSound(view);
    }

    public void vibraTest(View view) {
        if(vibra.hasVibrator())
            vibra.vibrate(VIBRA_TIME);
    }

    public void showInfo(View view) {
        Intent intent = new Intent(this, DisplayInfo.class);
        this.startActivity(intent);

    }

    public void flashLedTest(View view) {
        if(camParameters == null || camera == null)
            return;
        if( camParameters.getFlashMode().equals("off")) {
            camParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(camParameters);
            camera.startPreview();
        }
        else{
            ledOff();
        }
    }

    public void touchTest(View view) {
        Intent intent = new Intent(this, TouchTest.class);
        this.startActivity(intent);
    }

    private void ledOff(){
        camParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(camParameters);
        camera.stopPreview();
    }

    public void accelerometerTest(View view) {
        Intent intent = new Intent(this, OrientationTest.class);
        this.startActivity(intent);
    }
}
