package com.client.model;

/**
 * A member typical working day
 * @author Thomas LUCE
 *
 */
public class WorkingDay {
	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private WorkSchedule parentSchedule;
	private int nbHoursWorked;
	private String code;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	public WorkingDay(WorkSchedule parentSchedule, String code, int nbHoursWorked) {
		this.parentSchedule = parentSchedule;
		this.nbHoursWorked = nbHoursWorked;
		this.code = code;
	}
  
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
	/**
	 * @return the nbHoursWorked
	 */
	public int getNbHoursWorked() {
		return nbHoursWorked;
	}
	/**
	 * @param nbHoursWorked the nbHoursWorked to set
	 */
	public void setNbHoursWorked(int nbHoursWorked) {
		this.nbHoursWorked = nbHoursWorked;
	}
	/**
	 * @return the parentSchedule
	 */
	public WorkSchedule getParentSchedule() {
		return parentSchedule;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

  
	//---------------------------------------- PUBLIC ---------------------------------------------------



}
