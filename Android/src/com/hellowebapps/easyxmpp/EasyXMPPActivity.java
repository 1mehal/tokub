package com.hellowebapps.easyxmpp;

import com.hellowebapps.easyxmpp.SlidingMenu;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class EasyXMPPActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SlidingMenu menu = (SlidingMenu)findViewById(R.id.slider);
        //menu.setAcceleration(0.0004f);
        menu.addItemsToMenu(6);
        menu.setMenuPosition(menu.LEFT_BOTTOM);
        for (int i = 0; i < 6; i++){
        	menu.setOnClickListener(i, new OnClickListener() {
				public void onClick(View arg0) {
					Toast.makeText(getApplicationContext(), "button pressed" + arg0.getId(), 4000).show();
				}
			});
        }

        menu.showSlidingMenu();
    }
}