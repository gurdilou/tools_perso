package com.client.requests.predictionscreen.editmembers;

import java.util.Date;

import com.client.constants.AppConstants;
import com.client.model.Member;
import com.client.model.ServerUtils;
import com.client.model.WorkScheduleEdition;
import com.client.model.WorkingDay;
import com.client.requests.ShareLootorRequestBuilder;
import com.client.requests.ShareLootorRequestManager;
import com.client.requests.predictionscreen.editmembers.handlers.AddMemberHandler;
import com.client.requests.predictionscreen.editmembers.handlers.AddMemberScheduleHandler;
import com.client.requests.predictionscreen.editmembers.handlers.ChangeMemberPasswordHandler;
import com.client.requests.predictionscreen.editmembers.handlers.ChangeMemberRightAdmin;
import com.client.requests.predictionscreen.editmembers.handlers.ChangeMemberRightEditUsers;
import com.client.requests.predictionscreen.editmembers.handlers.ChangeMemberScheduleDateHandler;
import com.client.requests.predictionscreen.editmembers.handlers.ChangeMemberScheduleNbHoursHandler;
import com.client.requests.predictionscreen.editmembers.handlers.DeleteMemberHandler;
import com.client.requests.predictionscreen.editmembers.handlers.DeleteMemberScheduleHandler;
import com.client.requests.predictionscreen.editmembers.handlers.ResetMemberPasswordHandler;
import com.client.screens.ScreenManager;
import com.client.screens.predictionscreen.editmemberspart.EditMembersScreenPart;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;

public class EditMembersRequester extends ShareLootorRequestManager {


	//---------------------------------------- CONSTANTS ------------------------------------------------
	private static final String KEY_NAME = "name";
	private static final String KEY_MAIL = "email";
	private static final String KEY_START_DAY = "startDay";
	private static final String KEY_NEW_PWD = "newPwd";
	public static final String KEY_RIGHT_ADMIN = "rightAdmin";
	public static final String KEY_RIGHT_EDIT_USERS = "rightEditUsers";
	private static final String KEY_OLD_START_DAY = "oldStartDay";
	private static final String KEY_NEW_START_DAY = "newStartDay";
	private static final String KEY_DAY_EDITED = "dayEdited";
	private static final String KEY_NB_HOURS = "nbHours";
	
	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private EditMembersScreenPart parentScreen;

	

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	public EditMembersRequester(EditMembersScreenPart parentScreen) {
		super(parentScreen.getProps().getUser());
		
		this.parentScreen = parentScreen;
		
	}
  
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------
	/**
	 * Try to add a member on server
	 * @param hasEditUserRight 
	 * @param hasRightAdmin 
	 * @param project
	 * @param id
	 * @param pwd
	 * @throws RequestException 
	 */
	public void doAddMember(Member newMember, String email, Date startDay, boolean hasRightAdmin, boolean hasEditUserRight) {
		ScreenManager.showLoadingPanel();
		StringBuilder data = createBaseRequest();
		
		data.append("&"+KEY_NAME+"="+newMember.getUserLinked().getLogin());
		data.append("&"+KEY_MAIL+"="+email);
		data.append("&"+KEY_START_DAY+"="+AppConstants.MODEL_TIME_FORMAT.format(startDay));
		data.append("&"+KEY_RIGHT_ADMIN+"="+Boolean.toString( hasRightAdmin && parentScreen.getProps().getUser().hasRightAdmin() ));
		data.append("&"+KEY_RIGHT_EDIT_USERS+"="+Boolean.toString( hasEditUserRight && parentScreen.getProps().getUser().hasRightAdmin() ) );
		
		
		
		ShareLootorRequestBuilder builder = new ShareLootorRequestBuilder("addMember");
		builder.sendRequest(URL.encode(data.toString()), new AddMemberHandler(parentScreen));
	}

	/**
	 * Reset given member password
	 * @param member
	 */
	public void doResetPassword(Member member) {
		ScreenManager.showLoadingPanel();
		
		StringBuilder data = createBaseRequest();
		data.append("&"+KEY_NAME+"="+member.getUserLinked().getLogin());
		
		
		ShareLootorRequestBuilder builder = new ShareLootorRequestBuilder("resetMemberPassword");
		builder.sendRequest(URL.encode(data.toString()), new ResetMemberPasswordHandler(parentScreen));
	}

	/**
	 * Change user password
	 * @param member
	 */
	public void doChangePassword(Member member, String currentPwd, String newPwd) {
		ScreenManager.showLoadingPanel();
		
		StringBuilder data = new StringBuilder();
		data.append(ServerUtils.KEY_PROJECT+"="+member.getUserLinked().getProject().getName());
		data.append("&"+ServerUtils.KEY_ID+"="+member.getUserLinked().getLogin());
		data.append("&"+ServerUtils.KEY_PWD+"="+currentPwd);
		data.append("&"+KEY_NEW_PWD+"="+newPwd);
		
		
		ShareLootorRequestBuilder builder = new ShareLootorRequestBuilder("changeMemberPassword");
		builder.sendRequest(URL.encode(data.toString()), new ChangeMemberPasswordHandler(parentScreen));
	}

	/**
	 * Delete member request
	 * @param memberToDelete
	 */
	public void doDeleteMember(Member memberToDelete) {
		ScreenManager.showLoadingPanel();
		
		StringBuilder data = createBaseRequest();
		data.append("&"+KEY_NAME+"="+memberToDelete.getUserLinked().getLogin());
		
		
		ShareLootorRequestBuilder builder = new ShareLootorRequestBuilder("deleteMember");
		builder.sendRequest(URL.encode(data.toString()), new DeleteMemberHandler(parentScreen, memberToDelete));
	}

	/**
	 * Change admin right value for a member
	 * @param hasAdminRight
	 */
	public void doChangeRightAdmin(Member memberEdited, boolean hasRightAdmin) {
		StringBuilder data = createBaseRequest();
		data.append("&"+KEY_NAME+"="+memberEdited.getUserLinked().getLogin());
		data.append("&"+KEY_RIGHT_ADMIN+"="+Boolean.toString( hasRightAdmin && parentScreen.getProps().getUser().hasRightAdmin() ));
		
		
		ShareLootorRequestBuilder builder = new ShareLootorRequestBuilder("changeMemberHasRightAdmin");
		builder.sendRequest(URL.encode(data.toString()), new ChangeMemberRightAdmin(parentScreen, memberEdited));
	}
	/**
	 * Change admin right value for a member
	 * @param hasAdminRight
	 */
	public void doChangeRightEditUsers(Member memberEdited, boolean hasRightEditUsers) {
		StringBuilder data = createBaseRequest();
		data.append("&"+KEY_NAME+"="+memberEdited.getUserLinked().getLogin());
		data.append("&"+KEY_RIGHT_EDIT_USERS+"="+Boolean.toString( hasRightEditUsers && parentScreen.getProps().getUser().hasRightAdmin() ));
		
		
		ShareLootorRequestBuilder builder = new ShareLootorRequestBuilder("changeMemberHasRightEditUsers");
		builder.sendRequest(URL.encode(data.toString()), new ChangeMemberRightEditUsers(parentScreen, memberEdited));
	}

	/**
	 * Add a schedule at the given time
	 * @param memberSelected
	 * @param currentTime
	 */
	public void doAddSchedule(Member memberSelected, Date currentTime) {
		ScreenManager.showLoadingPanel();
		StringBuilder data = createBaseRequest();
		data.append("&"+KEY_NAME+"="+memberSelected.getUserLinked().getLogin());
		data.append("&"+KEY_START_DAY+"="+AppConstants.MODEL_TIME_FORMAT.format(currentTime));
		
		
		ShareLootorRequestBuilder builder = new ShareLootorRequestBuilder("addMemberSchedule");
		builder.sendRequest(URL.encode(data.toString()), new AddMemberScheduleHandler(parentScreen, memberSelected));
	}

	/**
	 * Delete a member schedule
	 * @param memberSelected
	 * @param scheduleToDelete
	 */
	public void doDeleteSchedule(Member memberSelected, WorkScheduleEdition scheduleToDelete) {
		ScreenManager.showLoadingPanel();
		StringBuilder data = createBaseRequest();
		data.append("&"+KEY_NAME+"="+memberSelected.getUserLinked().getLogin());
		data.append("&"+KEY_START_DAY+"="+AppConstants.MODEL_TIME_FORMAT.format(scheduleToDelete.getStartDay()));
		
		
		ShareLootorRequestBuilder builder = new ShareLootorRequestBuilder("deleteMemberSchedule");
		builder.sendRequest(URL.encode(data.toString()), new DeleteMemberScheduleHandler(parentScreen, memberSelected, scheduleToDelete));
	}

	/**
	 * Change start date of a schedule
	 * @param member
	 * @param oldValue
	 * @param workScheduleEdited
	 */
	public void doChangeScheduleDate(Member member, Date oldValue, WorkScheduleEdition workScheduleEdited) {
		ScreenManager.showLoadingPanel();
		StringBuilder data = createBaseRequest();
		data.append("&"+KEY_NAME+"="+member.getUserLinked().getLogin());
		data.append("&"+KEY_OLD_START_DAY+"="+AppConstants.MODEL_TIME_FORMAT.format(oldValue));
		data.append("&"+KEY_NEW_START_DAY+"="+AppConstants.MODEL_TIME_FORMAT.format(workScheduleEdited.getStartDay() ));
		
		
		ShareLootorRequestBuilder builder = new ShareLootorRequestBuilder("changeScheduleStartDate");
		builder.sendRequest(URL.encode(data.toString()), new ChangeMemberScheduleDateHandler(parentScreen, member, oldValue, workScheduleEdited));
	}

	/**
	 * Change the number of jours work of a schedule
	 * @param member
	 * @param oldValue
	 * @param object 
	 * @param dayEdited.getParentSchedule()
	 */
	public void doChangeScheduleNbHours(Member member, int oldValue, WorkScheduleEdition object, WorkingDay dayEdited) {
		StringBuilder data = createBaseRequest();
		data.append("&"+KEY_NAME+"="+member.getUserLinked().getLogin());
		data.append("&"+KEY_START_DAY+"="+AppConstants.MODEL_TIME_FORMAT.format(object.getStartDay()));
		
		data.append("&"+KEY_DAY_EDITED+"="+dayEdited.getCode());
		data.append("&"+KEY_NB_HOURS+"="+dayEdited.getNbHoursWorked());
		
		
		ShareLootorRequestBuilder builder = new ShareLootorRequestBuilder("changeScheduleNbHours");
		builder.sendRequest(URL.encode(data.toString()), new ChangeMemberScheduleNbHoursHandler(parentScreen, oldValue, dayEdited ));
		
	}


}
