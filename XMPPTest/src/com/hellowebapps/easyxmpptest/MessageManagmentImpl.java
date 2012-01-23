package com.hellowebapps.easyxmpptest;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;

import com.hellowebapps.easyxmpptest.messages.FileReceivedListener;
import com.hellowebapps.easyxmpptest.messages.MessageManagement;
import com.hellowebapps.easyxmpptest.messages.MessageReceivedListener;
import com.hellowebapps.easyxmpptest.usermanagement.User;

public class MessageManagmentImpl implements MessageManagement{

	XMPPConnection connection;
	
	MessageManagmentImpl(XMPPConnection connection){
		this.connection = connection;
	}
	
	public String sendMessage(User to, String message) {
		Message mes = new Message(message);
		connection.sendPacket(mes);
		return mes.getPacketID();
	}

	public String sendFile(User to, String fileName, byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}

	public void subscrubeMessageReceived(MessageReceivedListener listener) {
		// TODO Auto-generated method stub
		
	}

	public void unsubscrubeMessageReceived(MessageReceivedListener listener) {
		// TODO Auto-generated method stub
		
	}

	public void subscrubeFileReceived(FileReceivedListener listener) {
		// TODO Auto-generated method stub
		
	}

	public void unsubscrubeFileReceived(FileReceivedListener listener) {
		// TODO Auto-generated method stub
		
	}
	
}
