package com.client.model;

import java.util.Date;

/**
 * A work schedule created at a time
 * @author gurdi
 *
 */
public class WorkScheduleEdition {
	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private Date startDay;
	private WorkSchedule schedule;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * 
	 * @param creationDay
	 * @param schedule
	 */
	public WorkScheduleEdition(Date creationDay, WorkSchedule schedule){
		this.startDay = creationDay;
		this.schedule = schedule;
		
		this.schedule.linkEdition(this);
	}

	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
	/**
	 * @return the creationDay
	 */
	public Date getStartDay() {
		return startDay;
	}

	/**
	 * @param creationDay the creationDay to set
	 */
	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}

	/**
	 * @return the schedule
	 */
	public WorkSchedule getSchedule() {
		return schedule;
	}

	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(WorkSchedule schedule) {
		this.schedule = schedule;
		this.schedule.linkEdition(this);
	}
    
  
	//---------------------------------------- PUBLIC ---------------------------------------------------



}
