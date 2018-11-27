package com.sso.service;

import com.sso.user.UserInfo;

public class AuthenUserManager {
	public UserInfo authenUser(String username,String password) {
		UserInfo user =null;
		
	try {
		if(!username.isEmpty()&&!password.isEmpty()) {
			user = new UserInfo();
			user.setName("Cuong");
			user.setSurname("Nguyen");
			user.setDob("28/12/199x");
			user.setSex("male");
			user.setUsername(username);
			user.setPassword(password);
			
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
		
		return user;
	}
	
	public String generateTokenServer(UserInfo user ) {
		String tokenServer=null;
		
		tokenServer=AuthenticationToken.generateToken(user);
		
		return tokenServer;
	}
	public UserInfo verifyToken(String tokenServer) {
		return AuthenticationToken.verifyToken(tokenServer);
	}
}
