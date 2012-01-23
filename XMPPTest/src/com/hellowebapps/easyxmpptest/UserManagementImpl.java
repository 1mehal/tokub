package com.hellowebapps.easyxmpptest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;

import com.hellowebapps.easyxmpptest.usermanagement.User;
import com.hellowebapps.easyxmpptest.usermanagement.UserEvent;
import com.hellowebapps.easyxmpptest.usermanagement.UserEventsListener;
import com.hellowebapps.easyxmpptest.usermanagement.UserManagement;
import com.hellowebapps.easyxmpptest.usermanagement.UserType;

public class UserManagementImpl implements
		UserManagement {
	
	private XMPPConnection connection;
	private List <UserEventsListener> userEventListeners = new ArrayList <UserEventsListener>();

	UserManagementImpl (XMPPConnection connection){
		this.connection = connection;
		this.connection.getRoster().addRosterListener(new RosterListener() {
			public void presenceChanged(Presence presence) {
				// TODO Auto-generated method stub
				for (UserEventsListener listener : userEventListeners){
//					listener.onUserEvent();
				}
			}
			
			public void entriesUpdated(Collection<String> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void entriesDeleted(Collection<String> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void entriesAdded(Collection<String> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private String getUserStatus (String id){
		Presence presence = connection.getRoster().getPresence(id);
		if (presence != null){
			return "ONLINE";  
		}
		return "OFLINE";
	}
	
	public void addUser(String id){
		Roster roster = connection.getRoster();
		try {
			roster.createEntry(id, id, null);
			Presence presence = new Presence(Type.subscribe);
			presence.setTo(id);
			connection.sendPacket(presence);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void confirmUser(User user){
		Presence presence = new Presence(Type.subscribe);
		presence.setTo(user.getId());
		connection.sendPacket(presence);
	}
	
	public void rejectUser(User user) {
		Presence presence = new Presence(Type.unsubscribe);
		presence.setTo(user.getId());
		connection.sendPacket(presence);
	}

	public Iterable <User> getUsers() {
		Collection <User> userCollection = new ArrayList <User> ();
		Roster roster = connection.getRoster();
		for (RosterEntry entry : roster.getEntries()){
			User user = new UserImpl(entry.getUser(), entry.getName(), getUserStatus(entry.getUser()));
			userCollection.add(user);
		}
		return userCollection;
	}

	public Iterable <User> getUsers (UserType type) {
		Iterable<User> allUserCollection = getUsers();
		Collection <User> neededUserCollection = new ArrayList <User>();
		for (User user : allUserCollection){
			if (getUserType(user) == type){
				neededUserCollection.add(user);
			}
		}
		return neededUserCollection;
	}

	public UserType getUserType (User user) {
		Roster roster = this.connection.getRoster();
		RosterEntry entry = roster.getEntry(user.getId());
		switch (entry.getType()){
			case both: return UserType.FRIEND;
			case from: return UserType.FRIENDSHIP_FROM_ME;
			case to: return UserType.FRIENDSHIP_TO_ME;
			case none: 
			case remove: return UserType.UNKNOWN;
		}
		return UserType.UNKNOWN;
	}

	public void subscribeUserEvents(UserEventsListener listener){
		if (!(this.userEventListeners.contains(listener))){
			this.userEventListeners.add(listener);
		}
	}
	
	public void unsubscribeUserEvents(UserEventsListener listener) {
		// TODO Auto-generated method stub
		this.userEventListeners.remove(listener);
	}
}
