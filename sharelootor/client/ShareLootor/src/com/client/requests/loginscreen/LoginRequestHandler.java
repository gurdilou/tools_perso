package com.client.requests.loginscreen;


import com.client.constants.JSONConstants;
import com.client.model.Project;
import com.client.model.User;
import com.client.parsers.JSONMemberParser;
import com.client.parsers.JSONProjectParser;
import com.client.parsers.JSONUserParser;
import com.client.requests.ShareLootorRequestCallback;
import com.client.screens.loginscreen.LoginScreen;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;


/**
 * Handle and manage login request
 * @author gurdi
 *
 */
public class LoginRequestHandler extends ShareLootorRequestCallback {
	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private LoginScreen parentScreen;
	private String project;
	private String login;
	private String pwd;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * login handler
	 * @param parentScreen
	 */
	public LoginRequestHandler(LoginScreen parentScreen, String project, String login, String pwd) {
		super(parentScreen.getApp().getProps());
		this.parentScreen = parentScreen;
		
		this.project = project;
		this.login = login;
		this.pwd = pwd;
	}	
  
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------

	@Override
	protected void onResponseReceived(boolean succeed, JSONObject jsonResponse, Request request, Response response) {
		if(succeed){
			//Parsing project
			Project proj = getProps().getProject();
			proj.setName(project);
			JSONProjectParser projParser = new JSONProjectParser();
			JSONObject projectJSON = jsonResponse.get(JSONConstants.KEY_PROJECT).isObject();
			projParser.fillProject(proj, projectJSON);
			
			//Parsing current user
			User user = getProps().getUser();
			user.setLogin(login);
			user.setPwd(pwd);
			JSONObject userJSON = jsonResponse.get(JSONConstants.KEY_USER).isObject();
			JSONUserParser userParser = new JSONUserParser();
			userParser.fillUser(user, userJSON);
			
			
			//parsing of the project members
			JSONMemberParser.parseListMembers(jsonResponse, proj);
			
			parentScreen.onValidLogin();
		}else{
			parentScreen.onWrongLogin();
		}
	}

}
