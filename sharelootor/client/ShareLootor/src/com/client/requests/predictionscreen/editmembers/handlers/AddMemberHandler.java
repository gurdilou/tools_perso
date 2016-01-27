package com.client.requests.predictionscreen.editmembers.handlers;

import com.client.model.Member;
import com.client.parsers.JSONMemberParser;
import com.client.requests.ShareLootorRequestCallback;
import com.client.screens.ScreenManager;
import com.client.screens.predictionscreen.editmemberspart.EditMembersScreenPart;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;

public class AddMemberHandler extends ShareLootorRequestCallback {


	//---------------------------------------- CONSTANTS ------------------------------------------------
	private static final String KEY_MEMBER = "member";
	
	
	//---------------------------------------- VARIABLES ------------------------------------------------

	private EditMembersScreenPart parentScreen;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * Constructor
	 * @param parentScreen
	 */
	public AddMemberHandler(EditMembersScreenPart parentScreen) {
		super(parentScreen.getProps());
		
		this.parentScreen = parentScreen;
	}
  
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------

	@Override
	protected void onResponseReceived(boolean succeed, JSONObject jsonResponse, Request request, Response response) {
		ScreenManager.hideLoadingPanel();
		if(succeed){
			//parsing of the project members
			JSONMemberParser memberParser = new JSONMemberParser();
			Member newMember = memberParser.parseMember(jsonResponse.get(KEY_MEMBER).isObject(), getProps().getProject());
			
			parentScreen.responseAddMemberSucceed(newMember);
		}
	}



}
