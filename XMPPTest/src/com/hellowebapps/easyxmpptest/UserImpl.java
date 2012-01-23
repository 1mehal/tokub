package com.hellowebapps.easyxmpptest;

import com.hellowebapps.easyxmpptest.usermanagement.User;

public class UserImpl implements User{

	private String id;
	private String name;
	private String status;
	
	UserImpl(String id, String name, String status) {
		setId(id);
		setName(name);
		setStatus(status);
	}
	
	private void setId (String id){
		this.id = id;
	}

	private void setName (String name){
		this.name = name;
	}

	public void setStatus (String status){
		this.status = status;
	}
	
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public String getStatus() {
		// TODO Auto-generated method stub	
		return this.status;
	}
}
