package com.example.hmr.devtest.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.FrameLayout;
import com.example.hmr.devtest.R;

public class CameraTest extends Activity{
    private Camera mCamera;
    private CameraPreview mPreview;

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
        FrameLayout previewLayout = (FrameLayout) findViewById(R.id.camera_preview);
        previewLayout.addView(mPreview);
    }
}
