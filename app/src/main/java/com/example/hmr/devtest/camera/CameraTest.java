package com.example.hmr.devtest.camera;

import android.app.Activity;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import com.example.hmr.devtest.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;

public class CameraTest extends Activity{
    private static final String PICTURE_FILE_PREFIX = "PICTURE";
    private static final String TAG = "Camera Test";
    private static final String FILE_EXTENSION = ".jpg";
    private static final String MEDIA_DIR = "CameraTest";
    private Camera mCamera;
    private CameraPreview mPreview;
    private PictureCallback mPicture;
    FrameLayout previewLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        CameraManager cm = new CameraManager();

        mCamera = cm.enableCamera(b.getInt("camera"));
        if(mCamera == null){
            this.finish();
            return;
        }

        setContentView(R.layout.activity_camera_test);
        mPreview = new CameraPreview(this,mCamera);
        previewLayout = (FrameLayout) findViewById(R.id.camera_preview);
        previewLayout.addView(mPreview);

        //TODO: finish this crap
        mPicture = new PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                int picNumber = 0;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES), MEDIA_DIR );

                    if (! mediaStorageDir.exists()){
                        if (! mediaStorageDir.mkdirs())
                            Log.e(TAG, "failed to create directory");
                    }

                    File pictureFile = new File(mediaStorageDir.getPath() + File.separator +
                            PICTURE_FILE_PREFIX + picNumber + FILE_EXTENSION);

                    if (pictureFile == null)
                        Log.e(TAG, "Error creating media file!");
                    else {
                        //pictureFile.deleteOnExit();
                        try {
                            FileOutputStream fos = new FileOutputStream(pictureFile);
                            fos.write(data);
                            fos.close();
                        } catch (FileNotFoundException e) {
                            Log.d(TAG, "File not found: " + e.getMessage());
                        } catch (IOException e) {
                            Log.d(TAG, "Error accessing file: " + e.getMessage());
                        }
                    }
                }
                //mPreview.surfaceDestroyed(mPreview.getHolder());
                //  previewLayout.removeAllViews();

            }

        };
    }


    public void takePicture(View view) {
        mCamera.takePicture(null,null,mPicture);
    }
}
