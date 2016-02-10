package com.client.parsers;

import com.client.model.Member;
import com.client.model.Project;
import com.client.model.User;
import com.client.model.WorkScheduleEdition;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

/**
 * Member parser
 * @author Thomas Luce
 *
 */
public class JSONMemberParser {
	//---------------------------------------- CONSTANTS ------------------------------------------------
	private static final String KEY_LIST_MEMBERS = "listMembers";
	private static final String KEY_WORK_SCHEDULES = "workSchedules";
	private static final String KEY_USER_LINKED = "userLinked";


	//---------------------------------------- VARIABLES ------------------------------------------------
	private JSONUserParser userParser;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------

  
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------
	public JSONMemberParser() {
		this.userParser = new JSONUserParser();
	}

	/**
	 * Fill from json objecy
	 * @author Thomas Luce
	 * @param memberJSON
	 * @param proj
	 * @return
	 */
	public Member parseMember(JSONObject memberJSON, Project proj) {
		User userLinked = new User(proj);
		
		if(memberJSON.containsKey(KEY_USER_LINKED)){
			JSONObject userJSON = memberJSON.get(KEY_USER_LINKED).isObject();
			userParser.fillUser(userLinked, userJSON);
		}
		Member newMember = new Member(userLinked);
		
		JSONWorkScheduleParser scheduleParser = new JSONWorkScheduleParser();
		if(memberJSON.containsKey(KEY_WORK_SCHEDULES)){
			JSONArray listSchedulesJSON = memberJSON.get(KEY_WORK_SCHEDULES).isArray();
			
			for(int i = 0; i < listSchedulesJSON.size(); i++){
				JSONObject scheduleJSON = listSchedulesJSON.get(i).isObject();
				
				WorkScheduleEdition newWorkSchedule = scheduleParser.parseWorkSchedule(scheduleJSON);
				newMember.addWorkSchedule(newWorkSchedule);
			}
		}
		
		
		return newMember;
	}

	/**
	 * fill from json array
	 * @author Thomas Luce
	 * @param jsonResponse
	 * @param proj
	 */
	public static void parseListMembers(JSONObject jsonResponse, Project proj) {
		if(jsonResponse.containsKey(KEY_LIST_MEMBERS)){
			JSONArray listMembersJSON = jsonResponse.get(KEY_LIST_MEMBERS).isArray();
			
			JSONMemberParser memberParser = new JSONMemberParser();
			
			for(int i = 0; i < listMembersJSON.size(); i++){
				JSONObject memberJSON = listMembersJSON.get(i).isObject();
				Member newMember = memberParser.parseMember(memberJSON, proj);

				proj.addMember(newMember);
			}
		}
		
	}

}
