package com.hellowebapps.easyxmpptest.usermanagement;

import java.util.Iterator;

public interface UserManagement {
	void addUser(String id);
	void confirmUser(User user);
	void rejectUser(User user);
	Iterable <User> getUsers();
	Iterable <User> getUsers(UserType type);
	UserType getUserType(User user);
	void subscribeUserEvents(UserEventsListener listener);
	void unsubscribeUserEvents(UserEventsListener listener);
}

