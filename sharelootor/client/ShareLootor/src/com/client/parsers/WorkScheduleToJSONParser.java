package com.client.parsers;

import com.client.constants.JSONConstants;
import com.client.model.WorkSchedule;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;

public class WorkScheduleToJSONParser {
	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------


	//---------------------------------------- CONSTRUCTOR ----------------------------------------------

  
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------
	public JSONObject workScheduleToJSON(WorkSchedule schedule){
		JSONObject resultJSON = new JSONObject();
		resultJSON.put(JSONConstants.KEY_MONDAY, new JSONNumber(schedule.getMonday().getNbHoursWorked()) );
		resultJSON.put(JSONConstants.KEY_TUESDAY, new JSONNumber(schedule.getTuesday().getNbHoursWorked()) );
		resultJSON.put(JSONConstants.KEY_WEDNESDAY, new JSONNumber(schedule.getWednesday().getNbHoursWorked()) );
		resultJSON.put(JSONConstants.KEY_THURSDAY, new JSONNumber(schedule.getThursday().getNbHoursWorked()) );
		resultJSON.put(JSONConstants.KEY_FRIDAY, new JSONNumber(schedule.getFriday().getNbHoursWorked()) );
		resultJSON.put(JSONConstants.KEY_SATURDAY, new JSONNumber(schedule.getSaturday().getNbHoursWorked()) );
		resultJSON.put(JSONConstants.KEY_SUNDAY, new JSONNumber(schedule.getSunday().getNbHoursWorked()) );
		
		return resultJSON;
	}


}
