package com.client.requests.loginscreen;

import java.util.Date;

import com.client.constants.AppConstants;
import com.client.model.ServerUtils;
import com.client.requests.ShareLootorRequestBuilder;
import com.client.requests.ShareLootorRequestManager;
import com.client.screens.loginscreen.LoginScreen;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;

public class LoginScreenRequester extends ShareLootorRequestManager{
	//---------------------------------------- VARIABLES ------------------------------------------------
	
	
	private LoginScreen parentScreen;


	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	public LoginScreenRequester(LoginScreen parentScreen){
		super(null);
		
		this.parentScreen = parentScreen;
	}


	//---------------------------------------- PRIVATE --------------------------------------------------
	
	
	//---------------------------------------- PUBLIC ---------------------------------------------------
	/**
	 * Try to log user on server
	 * @param project
	 * @param id
	 * @param pwd
	 * @throws RequestException 
	 */
	public void doLogin(String project, String id, String pwd) {
		StringBuilder data = new StringBuilder();
		data.append(ServerUtils.KEY_PROJECT+"="+project);
		data.append("&"+ServerUtils.KEY_ID+"="+id);
		data.append("&"+ServerUtils.KEY_PWD+"="+pwd);
		
		
		ShareLootorRequestBuilder builder = new ShareLootorRequestBuilder("login");
		builder.sendRequest(URL.encode(data.toString()), new LoginRequestHandler(parentScreen, project, id, pwd));
	}

	
	/**
	 * Try to create a project on server
	 * @param project
	 * @param adminId
	 * @param adminPwd
	 * @param startDay
	 * @param email 
	 */
	public void doCreateProject(String project, String adminId, String adminPwd, Date startDay, String email) {
		StringBuilder data = new StringBuilder();
		data.append(ServerUtils.KEY_PROJECT+"="+project);
		data.append("&"+ServerUtils.KEY_ID+"="+adminId);
		data.append("&"+ServerUtils.KEY_PWD+"="+adminPwd);
		data.append("&startDay="+AppConstants.MODEL_TIME_FORMAT.format(startDay));
		data.append("&email="+email);
		
		
		ShareLootorRequestBuilder builder = new ShareLootorRequestBuilder("createProject");
		builder.sendRequest(URL.encode(data.toString()), new LoginRequestHandler(parentScreen, project, adminId, adminPwd));
	}
}
