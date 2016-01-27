package com.client.requests.predictionscreen.editmembers.handlers;

import com.client.model.Member;
import com.client.requests.ShareLootorRequestCallback;
import com.client.screens.ScreenManager;
import com.client.screens.predictionscreen.editmemberspart.EditMembersScreenPart;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;

public class DeleteMemberHandler extends ShareLootorRequestCallback{


	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private EditMembersScreenPart parentScreen;
	private Member memberToDelete;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * Constructor
	 * @param parentScreen
	 */
	public DeleteMemberHandler(EditMembersScreenPart parentScreen, Member memberToDelete) {
		super(parentScreen.getProps());
	
		this.parentScreen = parentScreen;
		this.memberToDelete = memberToDelete;
	}

	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------
	@Override
	protected void onResponseReceived(boolean succeed, JSONObject jsonResponse, Request request, Response response) {
		ScreenManager.hideLoadingPanel();
		if(succeed){
			parentScreen.responseDeleteMemberSucceed(memberToDelete);
		}
	}
  



}
