package com.client.parsers;

import java.util.Date;

import com.client.constants.AppConstants;
import com.client.constants.JSONConstants;
import com.client.model.WorkSchedule;
import com.client.model.WorkScheduleEdition;
import com.google.gwt.json.client.JSONObject;

public class JSONWorkScheduleParser {


	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------


	//---------------------------------------- CONSTRUCTOR ----------------------------------------------

  
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------
	/**
	 * Parse a work schedule
	 * @param scheduleJSON
	 * @return
	 */
	public WorkScheduleEdition parseWorkSchedule(JSONObject scheduleJSON) {
		Date startDay = AppConstants.MODEL_TIME_FORMAT.parse(scheduleJSON.get(JSONConstants.KEY_START_DAY).isString().stringValue());
		WorkSchedule schedule = new WorkSchedule();
		schedule.getMonday().setNbHoursWorked( new Double(scheduleJSON.get(JSONConstants.KEY_MONDAY).isNumber().doubleValue()).intValue() );
		schedule.getTuesday().setNbHoursWorked( new Double(scheduleJSON.get(JSONConstants.KEY_TUESDAY).isNumber().doubleValue()).intValue() );
		schedule.getWednesday().setNbHoursWorked( new Double(scheduleJSON.get(JSONConstants.KEY_WEDNESDAY).isNumber().doubleValue()).intValue() );
		schedule.getThursday().setNbHoursWorked( new Double(scheduleJSON.get(JSONConstants.KEY_THURSDAY).isNumber().doubleValue()).intValue() );
		schedule.getFriday().setNbHoursWorked( new Double(scheduleJSON.get(JSONConstants.KEY_FRIDAY).isNumber().doubleValue()).intValue() );
		schedule.getSaturday().setNbHoursWorked( new Double(scheduleJSON.get(JSONConstants.KEY_SATURDAY).isNumber().doubleValue()).intValue() );
		schedule.getSunday().setNbHoursWorked( new Double(scheduleJSON.get(JSONConstants.KEY_SUNDAY).isNumber().doubleValue()).intValue() );
		
		WorkScheduleEdition newWorkSchedule = new WorkScheduleEdition(startDay, schedule);
		return newWorkSchedule;
	}


}
