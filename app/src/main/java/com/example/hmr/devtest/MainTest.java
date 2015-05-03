package com.example.hmr.devtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.os.Bundle;
import android.view.View;

import com.example.hmr.devtest.camera.CameraManager;
import com.example.hmr.devtest.camera.CameraTest;
import com.example.hmr.devtest.keys.KeysTest;
import com.example.hmr.devtest.sensor.OrientationTest;
import com.example.hmr.devtest.sensor.ProximityTest;
import com.example.hmr.devtest.touch.TouchTest;


public class MainTest extends Activity {
    private static final String LOGTAG ="Device Test";
    private static final int TEST_SOUND1 = R.raw.acdc_sample;
    private static final int TEST_SOUND2 = R.raw.acdc_sample;
    private static final long VIBRA_TIME = 2000;
    private int testSound;
    private MediaPlayer mediaPlayer = null;
    private AudioManager audioManager;
    private CameraManager cameraManager;
    private Vibrator vibra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.STREAM_MUSIC);
        vibra = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        cameraManager = new CameraManager();
    }

    @Override
    protected void onStop(){
        super.onStop();
        cameraManager.disableCamera();
        stopSound();
        audioManager.setMode(AudioManager.MODE_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        cameraManager.disableCamera();
        stopSound();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    //TODO: restore audio profile
    public void playSound(View view) {
        if(mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, testSound);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.seekTo(0);
                }
            });
        }
        if(!mediaPlayer.isPlaying())
            mediaPlayer.start();
        else {
          stopSound();
        }
    }

    private void stopSound(){
        if(mediaPlayer == null) return;
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void testLs(View view) {
        startActivity(new Intent(this, ProximityTest.class));
    }

    public void testKeys(View view) {
        startActivity(new Intent(this, KeysTest.class));
    }

    public void playSoundOnReceiver(View view) {
        testSound = TEST_SOUND2;
        audioManager.setSpeakerphoneOn(false);
        playSound(view);
    }

    public void playSoundOnSpeaker(View view) {
        testSound = TEST_SOUND1;
        audioManager.setSpeakerphoneOn(true);
        playSound(view);
    }

    public void accelerometerTest(View view) {
        startActivity(new Intent(this, OrientationTest.class));
    }

    public void touchTest(View view) {
        startActivity(new Intent(this, TouchTest.class));
    }

    public void vibraTest(View view) {
        if(vibra.hasVibrator())
            vibra.vibrate(VIBRA_TIME);
    }

    public void showInfo(View view) {
       startActivity(new Intent(this, DisplayInfo.class));
    }


    public void frontCameraTest(View view) {
        cameraTest(CameraManager.FRONT_CAMERA);

    }

    public void backCameraTest(View view) {
        cameraTest(CameraManager.BACK_CAMERA);
    }

    public void flashLedTest(View view) {
         cameraManager.toggleFlashLed();
    }

    private void cameraTest(int cam){
        Intent intent = new Intent(this, CameraTest.class);
        Bundle b = new Bundle();
        b.putInt("camera",cam);
        intent.putExtras(b);
        startActivity(intent);
    }
}
