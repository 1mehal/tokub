package com.hellowebapps.easyxmpptest.messages;

import com.hellowebapps.easyxmpptest.usermanagement.User;

public interface MessageManagement {
	String /*id*/ sendMessage(User to, String message);
	String /*id*/ sendFile(User to, String fileName, byte[] data);
	void subscrubeMessageReceived(MessageReceivedListener listener);
	void unsubscrubeMessageReceived(MessageReceivedListener listener);
	void subscrubeFileReceived(FileReceivedListener listener);
	void unsubscrubeFileReceived(FileReceivedListener listener);
}
