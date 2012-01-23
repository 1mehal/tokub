package com.hellowebapps.easyxmpptest.connection;

public interface ConnectionStateManegment {
	ConnectionState getConnectionState();
	void subscribeConnectionStateChanged(ConnectionStateChangedListener listener);
	void unsubscribeConnectionStateChanged(ConnectionStateChangedListener listener);
}
