package com.client.requests.predictionscreen.editmembers.handlers;

import com.client.requests.ShareLootorRequestCallback;
import com.client.screens.ScreenManager;
import com.client.screens.predictionscreen.editmemberspart.EditMembersScreenPart;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;

public class ChangeMemberPasswordHandler extends ShareLootorRequestCallback{
	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private EditMembersScreenPart parentScreen;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * Constructor
	 * @param parentScreen
	 */
	public ChangeMemberPasswordHandler(EditMembersScreenPart parentScreen) {
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
			parentScreen.responseChangeMemberPasswordSucceed();
		}
	}


}
