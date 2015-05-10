package com.example.hmr.devtest.keys;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.example.hmr.devtest.R;

import java.util.HashMap;


public class KeysTest extends Activity implements OnTouchListener {
    private static final int [] keyCodes = {KeyEvent.KEYCODE_VOLUME_UP,KeyEvent.KEYCODE_VOLUME_DOWN,
                                    KeyEvent.KEYCODE_BACK,KeyEvent.KEYCODE_MENU,KeyEvent.KEYCODE_POWER};

    private static final int [] keyViews = {R.id.key_up,R.id.key_dn,R.id.key_back,R.id.key_menu,R.id.key_pwr};

    private static final int [] labels = {R.string.test_keys_vol_up,R.string.test_keys_vol_dn,R.string.test_keys_back,
                                    R.string.test_keys_menu,R.string.test_keys_pwr};

    private static final int MAX_PRESSES = 3;
    private static final int PASS_COLOR = Color.GREEN ;

    HashMap<Integer,KeyMap> keys = new HashMap<Integer, KeyMap>(keyCodes.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keys_test);
        for(int i = 0; i < keyCodes.length; i++)
            keys.put(keyCodes[i],new KeyMap((TextView)findViewById(keyViews[i]), getString(labels[i])));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean res = false;
        try {
            res = keys.get(keyCode).count();
            if(!res)
                res = super.onKeyDown(keyCode,event);
        }catch (Exception e){
            // other key.....
        }
        return res;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    private class KeyMap{
        public TextView btv;
        public int presses;
        public String label;

        public KeyMap(TextView etv, String lbl){
            btv = etv;
            presses = 0;
            label = lbl;
        }

        public boolean count(){
            if(presses >= MAX_PRESSES)
                return false; //pass handle to other receiver
            presses++;
            btv.setText(label + ": "+presses);
            if(presses == MAX_PRESSES)
                btv.setTextColor(PASS_COLOR);
            return true;
        }
    }
}
