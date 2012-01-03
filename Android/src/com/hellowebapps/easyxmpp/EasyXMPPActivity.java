package com.hellowebapps.easyxmpp;

import com.hellowebapps.SlidingMenu;

import android.app.Activity;
import android.os.Bundle;

public class EasyXMPPActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SlidingMenu menu = (SlidingMenu)findViewById(R.id.slider);
        menu.addMenuItemByNumber(2);
        menu.useSlidingMenu();
    }
}