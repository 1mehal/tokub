package com.hellowebapps.easyxmpptest;

import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.os.Bundle;

import com.hellowebapps.easyxmpp.SlidingMenu;

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
        
//        XMPPConnection connection;
        
        JabberSmackAPI xmpp = new JabberSmackAPI();
        
//    	ConnectionConfiguration config = new ConnectionConfiguration("hellowebapps.com", 15222, "qolabora.com");
//    	connection = new XMPPConnection(config);
    	
    		try {
    			xmpp.login("9014-5207", "adb28ca725164fcf8446bc362615ff12");
    			xmpp.sendMessage("Hello!", "1830-6671@qolabora.com");
			} catch (XMPPException e) {
				e.printStackTrace();
			}
    	
    	xmpp.disconnect();
    }
}