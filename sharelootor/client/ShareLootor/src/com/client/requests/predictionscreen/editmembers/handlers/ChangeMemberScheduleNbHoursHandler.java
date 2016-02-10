package com.client.requests.predictionscreen.editmembers.handlers;

import com.client.model.WorkingDay;
import com.client.requests.ShareLootorRequestCallback;
import com.client.screens.ScreenManager;
import com.client.screens.predictionscreen.editmemberspart.EditMembersScreenPart;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;

public class ChangeMemberScheduleNbHoursHandler extends ShareLootorRequestCallback{
	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private EditMembersScreenPart parentScreen;
	private int oldValue;
	private WorkingDay dayEdited;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * 
	 * @param properties
	 */
	public ChangeMemberScheduleNbHoursHandler(EditMembersScreenPart parentScreen,int oldValue, WorkingDay dayEdited) {
		super(parentScreen.getProps());
		
		this.parentScreen = parentScreen;
		this.oldValue = oldValue;
		this.dayEdited = dayEdited;

	}

	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------

	@Override
	protected void onResponseReceived(boolean succeed, JSONObject jsonResponse, Request request, Response response) {
		ScreenManager.hideLoadingPanel();
		if(!succeed){
			dayEdited.setNbHoursWorked(oldValue);
		}
		parentScreen.responseChangeScheduleNbHours(succeed);
	}
}