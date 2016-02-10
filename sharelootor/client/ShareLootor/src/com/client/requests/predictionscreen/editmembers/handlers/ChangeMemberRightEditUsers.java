package com.client.requests.predictionscreen.editmembers.handlers;


import com.client.model.Member;
import com.client.requests.ShareLootorRequestCallback;
import com.client.requests.predictionscreen.editmembers.EditMembersRequester;
import com.client.screens.predictionscreen.editmemberspart.EditMembersScreenPart;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;

public class ChangeMemberRightEditUsers extends ShareLootorRequestCallback{
	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private EditMembersScreenPart parentScreen;
	private Member memberEdited;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * 
	 * @param properties
	 */
	public ChangeMemberRightEditUsers(EditMembersScreenPart parentScreen, Member memberEdited) {
		super(parentScreen.getProps());
		
		this.parentScreen = parentScreen;
		this.memberEdited = memberEdited;

	}

	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------

	@Override
	protected void onResponseReceived(boolean succeed, JSONObject jsonResponse, Request request, Response response) {
		if(succeed){
			if(parentScreen.getProps().getUser().hasRightAdmin()){
				boolean newRight = jsonResponse.get(EditMembersRequester.KEY_RIGHT_EDIT_USERS).isBoolean().booleanValue();
				memberEdited.getUserLinked().setHasRightEditUsers(newRight);
				if(getProps().getUser().equals(memberEdited.getUserLinked())){
					getProps().getUser().setHasRightEditUsers(newRight);
				}
			}
		}
		parentScreen.responseRightChange(memberEdited);
	}

  


}