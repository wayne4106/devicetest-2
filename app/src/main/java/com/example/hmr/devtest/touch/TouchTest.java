package com.example.hmr.devtest.touch;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;


public class TouchTest extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new DrawView(this,null));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return false;
    }
}
