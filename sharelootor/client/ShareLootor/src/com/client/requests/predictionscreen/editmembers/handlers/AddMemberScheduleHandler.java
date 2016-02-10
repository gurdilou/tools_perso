package com.client.requests.predictionscreen.editmembers.handlers;

import com.client.model.Member;
import com.client.model.WorkScheduleEdition;
import com.client.parsers.JSONWorkScheduleParser;
import com.client.requests.ShareLootorRequestCallback;
import com.client.screens.ScreenManager;
import com.client.screens.predictionscreen.editmemberspart.EditMembersScreenPart;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;

public class AddMemberScheduleHandler extends ShareLootorRequestCallback{


	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private static final String KEY_WORK_SCHEDULE = "workSchedule";
	private EditMembersScreenPart parentScreen;
	private Member memberSelected;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * AddMemberScheduleHandler
	 * @param properties
	 */
	public AddMemberScheduleHandler(EditMembersScreenPart parentScreen, Member memberSelected) {
		super(parentScreen.getProps());
		
		this.parentScreen = parentScreen;
		this.memberSelected = memberSelected;
	}

	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------
	
	@Override
	protected void onResponseReceived(boolean succeed, JSONObject jsonResponse, Request request, Response response) {
		ScreenManager.hideLoadingPanel();
		if(succeed){
			if(jsonResponse.containsKey(KEY_WORK_SCHEDULE)){
				JSONObject workScheduleJSON = jsonResponse.get(KEY_WORK_SCHEDULE).isObject();
				JSONWorkScheduleParser scheduleParser = new JSONWorkScheduleParser();
				
				WorkScheduleEdition newWorkSchedule = scheduleParser.parseWorkSchedule(workScheduleJSON);
				memberSelected.addWorkSchedule(newWorkSchedule);
			
				parentScreen.responseAddMemberScheduleSucceed();
			}
		}
	}
  



}
