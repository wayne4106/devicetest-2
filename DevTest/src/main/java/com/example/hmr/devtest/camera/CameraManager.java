package com.example.hmr.devtest.camera;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.util.Log;


/**
 * Created by hmr on 02-05-2015.
 */
public class CameraManager {
    public static final int BACK_CAMERA = CameraInfo.CAMERA_FACING_BACK;
    public static final int FRONT_CAMERA = CameraInfo.CAMERA_FACING_FRONT;
    private static final String CM_TAG = "Camera Manager ";

    private  Camera camera;
    private  CameraInfo [] cameraInfo;
    private  Parameters cameraParameters;
    private  int currentCameraNumber;
    private  boolean ledState;

    /**
     *
     * @param facing
     * @return
     */
    public Camera enableCamera(int facing){
        if(camera == null){
            for(int i = 0; i < cameraInfo.length; i++){
                if(cameraInfo[i].facing == facing) {
                    camera = Camera.open(i);
                    cameraParameters = camera.getParameters();
                    currentCameraNumber = i;
                    break;
                }
            }
            return camera;
        }
        if(cameraInfo[currentCameraNumber].facing != facing){
            disableCamera();
            enableCamera(facing);
        }
        return camera;
    }

    /**
     *
     */
    public void disableCamera(){
        if(camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    /**
     *
     */
    public  void toggleFlashLed(){
        camera = enableCamera(BACK_CAMERA);
        if( camera == null)
            return;
        try {
            if (ledState) {
                cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(cameraParameters);
                camera.startPreview();
            } else {
                cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(cameraParameters);
                camera.stopPreview();
            }
        }catch (Exception e){
            Log.e(CM_TAG, "No flash Led");
        }
        ledState = !ledState;
    }

    /**
     *
     */
    public CameraManager(){
        cameraInfo = new CameraInfo[Camera.getNumberOfCameras()];

        for(int i=0; i< cameraInfo.length; i++){
            cameraInfo[i] = new CameraInfo();
            Camera.getCameraInfo(i,cameraInfo[i]);
        }
        camera = null;
        currentCameraNumber = 0;
        ledState = true;
    }
}
