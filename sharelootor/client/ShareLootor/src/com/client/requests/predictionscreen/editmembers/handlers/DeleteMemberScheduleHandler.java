package com.client.requests.predictionscreen.editmembers.handlers;

import com.client.model.Member;
import com.client.model.WorkScheduleEdition;
import com.client.requests.ShareLootorRequestCallback;
import com.client.screens.ScreenManager;
import com.client.screens.predictionscreen.editmemberspart.EditMembersScreenPart;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;

public class DeleteMemberScheduleHandler extends ShareLootorRequestCallback{


	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private EditMembersScreenPart parentScreen;
	private Member memberSelected;
	private WorkScheduleEdition scheduleToDelete;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * AddMemberScheduleHandler
	 * @param properties
	 */
	public DeleteMemberScheduleHandler(EditMembersScreenPart parentScreen, Member memberSelected, WorkScheduleEdition scheduleToDelete) {
		super(parentScreen.getProps());
		
		this.parentScreen = parentScreen;
		this.memberSelected = memberSelected;
		this.scheduleToDelete = scheduleToDelete;
	}

	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------
	
	@Override
	protected void onResponseReceived(boolean succeed, JSONObject jsonResponse, Request request, Response response) {
		ScreenManager.hideLoadingPanel();
		if(succeed){
			memberSelected.removeWorkSchedule(scheduleToDelete);
			parentScreen.responseDeleteMemberScheduleSucceed(scheduleToDelete);
		}
	}
  



}
