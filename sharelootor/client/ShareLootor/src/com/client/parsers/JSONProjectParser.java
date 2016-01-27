package com.client.parsers;

import com.client.constants.AppConstants;
import com.client.constants.JSONConstants;
import com.client.model.Project;
import com.google.gwt.json.client.JSONObject;

public class JSONProjectParser {


	//---------------------------------------- CONSTANTS ------------------------------------------------
	private static final String KEY_NAME = "name";
	private static final String KEY_CURR_SYMBOL = "currencySymbol";
	private static final String KEY_ESTIMATED_INCOME = "estimatedIncome";


	
	//---------------------------------------- VARIABLES ------------------------------------------------


	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	public JSONProjectParser(){
		
	}
  
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  


	//---------------------------------------- PUBLIC ---------------------------------------------------
	/**
	 * Parse project
	 * @author Thomas Luce
	 * @param proj
	 * @param projectJSON
	 */
	public void fillProject(Project proj, JSONObject projectJSON) {
		if(projectJSON.containsKey(KEY_NAME)){
			proj.setName( projectJSON.get(KEY_NAME).isString().stringValue() );
		}
		if(projectJSON.containsKey(KEY_CURR_SYMBOL)){
			proj.setCurrencySymbol( projectJSON.get(KEY_CURR_SYMBOL).isString().stringValue() );
		}
		if(projectJSON.containsKey(KEY_ESTIMATED_INCOME)){
			proj.setEstimatedIncome( (float) projectJSON.get(KEY_ESTIMATED_INCOME).isNumber().doubleValue() );
		}
		if(projectJSON.containsKey(JSONConstants.KEY_START_DAY)){
			proj.setStartDay( AppConstants.MODEL_TIME_FORMAT.parse(projectJSON.get(JSONConstants.KEY_START_DAY).isString().stringValue()) );
		}
		if(projectJSON.containsKey(JSONConstants.KEY_MONDAY)){
			proj.getDefaultWorkSchedule().getSchedule().getMonday().setNbHoursWorked( new Double(projectJSON.get(JSONConstants.KEY_MONDAY).isNumber().doubleValue()).intValue() );
		}
		if(projectJSON.containsKey(JSONConstants.KEY_TUESDAY)){
			proj.getDefaultWorkSchedule().getSchedule().getTuesday().setNbHoursWorked( new Double(projectJSON.get(JSONConstants.KEY_TUESDAY).isNumber().doubleValue()).intValue() );
		}
		if(projectJSON.containsKey(JSONConstants.KEY_WEDNESDAY)){
			proj.getDefaultWorkSchedule().getSchedule().getWednesday().setNbHoursWorked( new Double(projectJSON.get(JSONConstants.KEY_WEDNESDAY).isNumber().doubleValue()).intValue() );
		}
		if(projectJSON.containsKey(JSONConstants.KEY_THURSDAY)){
			proj.getDefaultWorkSchedule().getSchedule().getThursday().setNbHoursWorked( new Double(projectJSON.get(JSONConstants.KEY_THURSDAY).isNumber().doubleValue()).intValue() );
		}
		if(projectJSON.containsKey(JSONConstants.KEY_FRIDAY)){
			proj.getDefaultWorkSchedule().getSchedule().getFriday().setNbHoursWorked( new Double(projectJSON.get(JSONConstants.KEY_FRIDAY).isNumber().doubleValue()).intValue() );
		}
		if(projectJSON.containsKey(JSONConstants.KEY_SATURDAY)){
			proj.getDefaultWorkSchedule().getSchedule().getSaturday().setNbHoursWorked( new Double(projectJSON.get(JSONConstants.KEY_SATURDAY).isNumber().doubleValue()).intValue() );
		}
		if(projectJSON.containsKey(JSONConstants.KEY_SUNDAY)){
			proj.getDefaultWorkSchedule().getSchedule().getSunday().setNbHoursWorked( new Double(projectJSON.get(JSONConstants.KEY_SUNDAY).isNumber().doubleValue()).intValue() );
		}
	}


}
