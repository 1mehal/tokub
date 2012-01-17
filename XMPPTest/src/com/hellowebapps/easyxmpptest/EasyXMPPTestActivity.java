package com.hellowebapps.easyxmpptest;

import android.app.Activity;
import android.os.Bundle;
import com.hellowebapps.easyxmpp.SlidingMenu;;

public class EasyXMPPTestActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SlidingMenu menu = (SlidingMenu) findViewById(R.id.menu);
        menu.addItemsToMenu(4);
        menu.setMenuPosition(menu.LEFT_BOTTOM);
        menu.showSlidingMenu();
    }
}