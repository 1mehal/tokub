package com.hellowebapps.easyxmpptest.messages;

import com.hellowebapps.easyxmpptest.usermanagement.User;

public interface FileReceivedListener {
	void fileRecevied(User from, String filename, byte[] data);
}
