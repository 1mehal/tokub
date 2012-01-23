package com.hellowebapps.easyxmpptest;

import java.util.List;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.hellowebapps.easyxmpptest.connection.ConnectionState;
import com.hellowebapps.easyxmpptest.connection.ConnectionStateChangedListener;
import com.hellowebapps.easyxmpptest.connection.ConnectionStateController;
import com.hellowebapps.easyxmpptest.connection.ConnectionStateManegment;

public class ConnectionStateManegmentImpl implements ConnectionStateManegment, ConnectionStateController {

	private XMPPConnection connection;
	private ConnectionState connectionState = ConnectionState.OFFLINE;
	private List <ConnectionStateChangedListener> connectionStateListeners;
	private boolean autoReconnect = false;
	
	private void fireConnectionStateChanged(){
		for (ConnectionStateChangedListener listener : connectionStateListeners){
			listener.onStateChanged(getConnectionState());
		}
	}	
	
	private void setConnectionState(ConnectionState connectionState) {
		this.connectionState = connectionState;
		fireConnectionStateChanged();
		if ((connectionState == ConnectionState.OFFLINE)&&(this.autoReconnect)){
			this.connect(this.autoReconnect);
		}
	}

	void ConectionStateMenegmentImpl(XMPPConnection connection){
		this.connection = connection;
		this.connection.addConnectionListener(new ConnectionListener() {
			public void reconnectionSuccessful() {
				setConnectionState(ConnectionState.ONLINE);
			}
			
			public void reconnectionFailed(Exception arg0) {
				setConnectionState(ConnectionState.OFFLINE);
			}
			
			public void reconnectingIn(int arg0) {
				setConnectionState(ConnectionState.CONNECTING);
			}
			
			public void connectionClosedOnError(Exception arg0) {
				setConnectionState(ConnectionState.OFFLINE);
			}
			
			public void connectionClosed() {
				setConnectionState(ConnectionState.OFFLINE);
			}
		});
	}
	

	public ConnectionState getConnectionState() {
		return this.connectionState;
	}

	public void subscribeConnectionStateChanged(ConnectionStateChangedListener listener) {
		if (!(connectionStateListeners.contains(listener))){
			connectionStateListeners.add(listener);
		}
	}

	public void unsubscribeConnectionStateChanged(
			ConnectionStateChangedListener listener) {
			connectionStateListeners.remove(listener);
	}

	public void connect(boolean autoReconnect) {
		this.autoReconnect = autoReconnect;
		try {
			this.connection.connect();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void disconnect() {
		this.connection.disconnect();
	}
}
