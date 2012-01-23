package com.hellowebapps.easyxmpptest.connection;

public interface ConnectionStateController {
	void connect(boolean autoreconnect);
	void disconnect();
}
