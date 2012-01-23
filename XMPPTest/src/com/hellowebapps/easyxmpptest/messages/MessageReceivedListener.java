package com.hellowebapps.easyxmpptest.messages;

import com.hellowebapps.easyxmpptest.usermanagement.User;

public interface MessageReceivedListener {
		void messageRecevied(User from, String message);
}
