package com.example.hmr.devtest.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by hmr on 03-05-2015.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final int DEFAULT_ORIENTATION = 90;
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mCamera = camera;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            mCamera.setDisplayOrientation(DEFAULT_ORIENTATION);
        } catch (IOException e) {
            Log.e(VIEW_LOG_TAG, "Error accessing camera: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            mCamera.stopPreview();
            mCamera.release();
        }catch (Exception e){
            //
        }
    }
}
