package com.client.model;

import com.client.constants.JSONConstants;


public class WorkSchedule {


	//---------------------------------------- CONSTANTS ------------------------------------------------
	private static final int WEEK_DAY_DEFAULT_HOURS = 2;
	private static final int WEEKEND_DAY_DEFAULT_HOURS = 4;
	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private WorkScheduleEdition editionLinked = null;
	private WorkingDay monday;
	private WorkingDay tuesday;
	private WorkingDay wednesday;
	private WorkingDay thursday;
	private WorkingDay friday;
	private WorkingDay saturday;
	private WorkingDay sunday;

	

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * Constructor
	 */
	public WorkSchedule() {
		this.monday = new WorkingDay(this, JSONConstants.KEY_MONDAY, WEEK_DAY_DEFAULT_HOURS);
		this.tuesday = new WorkingDay(this, JSONConstants.KEY_TUESDAY, WEEK_DAY_DEFAULT_HOURS);
		this.wednesday = new WorkingDay(this, JSONConstants.KEY_WEDNESDAY, WEEK_DAY_DEFAULT_HOURS);
		this.thursday = new WorkingDay(this, JSONConstants.KEY_THURSDAY, WEEK_DAY_DEFAULT_HOURS);
		this.friday = new WorkingDay(this, JSONConstants.KEY_FRIDAY, WEEK_DAY_DEFAULT_HOURS);
		this.saturday = new WorkingDay(this, JSONConstants.KEY_SATURDAY, WEEKEND_DAY_DEFAULT_HOURS);
		this.sunday = new WorkingDay(this, JSONConstants.KEY_SUNDAY, WEEKEND_DAY_DEFAULT_HOURS);
	}




	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
	/**
	 * @return the monday
	 */
	public WorkingDay getMonday() {
		return monday;
	}
	/**
	 * @return the tueday
	 */
	public WorkingDay getTuesday() {
		return tuesday;
	}
	/**
	 * @return the wednesday
	 */
	public WorkingDay getWednesday() {
		return wednesday;
	}
	/**
	 * @return the thursday
	 */
	public WorkingDay getThursday() {
		return thursday;
	}
	/**
	 * @return the friday
	 */
	public WorkingDay getFriday() {
		return friday;
	}
	/**
	 * @return the saturday
	 */
	public WorkingDay getSaturday() {
		return saturday;
	}
	/**
	 * @return the sunday
	 */
	public WorkingDay getSunday() {
		return sunday;
	}

	/**
	 * Links to an edition
	 * @param edition
	 */
	public void linkEdition(WorkScheduleEdition edition) {
		this.editionLinked = edition;
	}
	/**
	 * @return the editionLinked
	 */
	public WorkScheduleEdition getEditionLinked() {
		return editionLinked;
	}


	/**
	 * Return the number of hours worked according to the index day
	 * 0 = sunday
	 * @author Thomas Luce
	 * @param currentDayIndex
	 * @return
	 */
	protected int getNbworkedHoursFromdayIndex(int currentDayIndex) {
		int result = 0;
		switch (currentDayIndex) {
		case 0:
			result = sunday.getNbHoursWorked();
			break;
		case 1:
			result = monday.getNbHoursWorked();
			break;
		case 2:
			result = tuesday.getNbHoursWorked();
			break;
		case 3:
			result = wednesday.getNbHoursWorked();
			break;
		case 4:
			result = thursday.getNbHoursWorked();
			break;
		case 5:
			result = friday.getNbHoursWorked();
			break;
		case 6:
			result = saturday.getNbHoursWorked();
			break;

		default:
			break;
		}
		
		return result;
	}



	//---------------------------------------- PUBLIC ---------------------------------------------------



}
