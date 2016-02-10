package com.client.requests.predictionscreen.editmembers.handlers;

import java.util.Date;

import com.client.model.Member;
import com.client.model.WorkScheduleEdition;
import com.client.requests.ShareLootorRequestCallback;
import com.client.screens.ScreenManager;
import com.client.screens.predictionscreen.editmemberspart.EditMembersScreenPart;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;

public class ChangeMemberScheduleDateHandler extends ShareLootorRequestCallback{
	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private EditMembersScreenPart parentScreen;
	private Member memberEdited;
	private Date oldValue;
	private WorkScheduleEdition scheduleEdited;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * 
	 * @param properties
	 */
	public ChangeMemberScheduleDateHandler(EditMembersScreenPart parentScreen, Member memberEdited, Date oldValue, WorkScheduleEdition scheduleEdited) {
		super(parentScreen.getProps());
		
		this.parentScreen = parentScreen;
		this.memberEdited = memberEdited;
		this.oldValue = oldValue;
		this.scheduleEdited = scheduleEdited;

	}

	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------

	@Override
	protected void onResponseReceived(boolean succeed, JSONObject jsonResponse, Request request, Response response) {
		ScreenManager.hideLoadingPanel();
		if(succeed){
			memberEdited.sortSchedules();
		}else{
			scheduleEdited.setStartDay(oldValue);
		}
		parentScreen.responseChangeScheduleDate();
	}
}