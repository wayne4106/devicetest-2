package com.example.hmr.devtest.sound;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioRecord.OnRecordPositionUpdateListener;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.hmr.devtest.R;


public class LoopBack extends Activity {
    private static final int SAMPLE_RATE = 44100;
    private static final int ECHO_DURATION = 2;

    private static final String TAG = "Audio Loop";
    private AudioRecord audioRecord;
    private AudioTrack audioTrack;
    private int bufferSize, trackSize,trackStart;
    private short []buffer; //16BIT/SAMPLE
    private boolean looping;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loop_back);
        configLoopBack();
        looping = false;
    }

    @Override
    protected void onStop(){
        super.onStop();
        audioRecord.stop();
        audioRecord.release();
        try {
            audioTrack.stop();
            audioTrack.release();
        }catch(Exception e){
            Log.e(TAG,e.getMessage());
        }
    }
    //TODO: get OnPlaybackPositionUpdateListener() working to avoid extreme feedback
    public void configLoopBack() {
        bufferSize = ECHO_DURATION * AudioRecord.getMinBufferSize(
                SAMPLE_RATE,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        buffer = new short[bufferSize];
        trackSize = bufferSize / 2; //half buffer
        trackStart = 0;

        try {
            audioRecord = new AudioRecord(
                    MediaRecorder.AudioSource.MIC,
                    SAMPLE_RATE,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, bufferSize);

            //audioRecord.setNotificationMarkerPosition(trackSize/2);
            audioRecord.setPositionNotificationPeriod(trackSize);
            audioRecord.setRecordPositionUpdateListener(new OnRecordPositionUpdateListener() {

                @Override
                public void onMarkerReached(AudioRecord audioRecord) {

                }
                @Override
                public void onPeriodicNotification(AudioRecord audioRecord) {
                    //audioRecord.stop();
                    audioTrack.write(buffer,trackStart,trackSize);
                    //audioTrack.play();

                    trackStart = (trackStart+trackSize) % bufferSize;
                    audioRecord.read(buffer,trackStart,trackSize);
                }
            });

            audioTrack = new AudioTrack(
                    AudioManager.STREAM_MUSIC,
                    SAMPLE_RATE, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, bufferSize,
                    AudioTrack.MODE_STREAM);

            //audioTrack.setPlaybackRate(SAMPLE_RATE);
            audioTrack.setNotificationMarkerPosition(trackSize);
            audioTrack.setPlaybackPositionUpdateListener(new AudioTrack.OnPlaybackPositionUpdateListener() {
                @Override
                public void onMarkerReached(AudioTrack audioTrack) {
                    Log.i(TAG,"Playback marker");
                }

                @Override
                public void onPeriodicNotification(AudioTrack audioTrack) {
                    //audioTrack.stop();
                    //trackStart = (trackStart+trackSize) % bufferSize;
                    //audioRecord.startRecording();
                    //audioRecord.read(buffer,trackStart,trackSize);
                }
            });
            audioTrack.play();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            this.finish();
        }
    }

    public void doLoop(View view) {
        if (looping) {
            audioRecord.stop();
        } else {
            if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
                audioRecord.startRecording();
                audioRecord.read(buffer, trackStart, trackSize);
            }
        }
        looping = !looping;
    }
}
