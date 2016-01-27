package com.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import com.client.constants.AppConstants;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;

/**
 * A member of the project
 * @author gurdi
 *
 */
public class Member {
	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------
	//Variables
	private User userLinked;
	private ArrayList<WorkScheduleEdition> workScheduleHistory;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * 
	 * @param login
	 */
	public Member(User userLinked) {
		this.userLinked = userLinked;
		this.workScheduleHistory = new ArrayList<WorkScheduleEdition>();
	}

  
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------


	/**
	 * @return the workScheduleHistory
	 */
	public Iterator<WorkScheduleEdition> getWorkScheduleHistoryIterator() {
		return workScheduleHistory.iterator();
	}

	/**
	 * Add a work schedule at the right place
	 */
	public void addWorkSchedule(WorkScheduleEdition newWorkSchedule){
		if(workScheduleHistory.size() == 0){
			workScheduleHistory.add(newWorkSchedule);
		}else{
			int cursor = workScheduleHistory.size() - 1;
			Date newScheduleDay = newWorkSchedule.getStartDay();
			while( (cursor >= 0) && (workScheduleHistory.get(cursor).getStartDay().after(newScheduleDay)) ){
				cursor --;
			}
			workScheduleHistory.add(cursor + 1, newWorkSchedule);
		}
	}
	/**
	 * @return the login
	 */
	public User getUserLinked() {
		return userLinked;
	}

	/**
	 * Get the number of worked hours, according to member schedules
	 * @return
	 */
	public int getTotalWorkedHours() {
		if(getUserLinked() != null){
			// result
			int total = 0;
			Project proj = userLinked.getProject();
			if(proj != null){
				//get the nb of days to process
				Date currentDay = (Date) proj.getStartDay().clone();
				DateTimeFormat dateToDayWeek = DateTimeFormat.getFormat("c");
				int currentDayIndex = Integer.parseInt(dateToDayWeek.format(currentDay));
				Date today = new Date();
				
				if( today.after(currentDay) || today.equals(currentDay) ){
					int nbProjDays = CalendarUtil.getDaysBetween(currentDay, today) + 1;
					int cursorSchedules = -1;
					WorkScheduleEdition currentSchedule = null;
					
					//We ll some each day nb hours worked
					for(int i = 0; i < nbProjDays; i++){
						if(cursorSchedules + 1 < workScheduleHistory.size()){
							WorkScheduleEdition nextSchedule = workScheduleHistory.get(cursorSchedules + 1);
							
							//Change schedule ?
							Date nextSchedDay = nextSchedule.getStartDay();
							if( currentDay.equals(nextSchedDay) || currentDay.after(nextSchedDay) ){
								currentSchedule = nextSchedule;
								cursorSchedules++;
							}
						}
						//Add hours worked for this day
						if(currentSchedule != null){
							total += currentSchedule.getSchedule().getNbworkedHoursFromdayIndex(currentDayIndex);
						}
						//Increment date cursor
						CalendarUtil.addDaysToDate(currentDay, 1);
						currentDayIndex = (currentDayIndex + 1) % 7;
					}
				}
			}
			return total;
		}else{
			return 0;
		}
	}


	/**
	 * @param currentTime
	 * @return true if member has workSchedule at this time
	 */
	public boolean hasWorkScheduleAt(Date timeTested) {
		Date dayTested = AppConstants.MODEL_TIME_FORMAT.parse( AppConstants.MODEL_TIME_FORMAT.format(timeTested) );
		RootPanel.get().add(new Label("dayTested : "+AppConstants.MODEL_TIME_FORMAT.format(dayTested)));
		
		boolean found = false;
		int i = 0;
		while(!found && (i < workScheduleHistory.size())){
			Date startScheduleDay = workScheduleHistory.get(i).getStartDay();
			RootPanel.get().add(new Label("startScheduleDay : "+AppConstants.MODEL_TIME_FORMAT.format(startScheduleDay)));
			
			if( dayTested.equals(startScheduleDay) ){
				found = true;
			}
			if(startScheduleDay.after(dayTested)){
				i = workScheduleHistory.size();
			}
			i++;
		}
		return found;
	}
	
	/**
	 * Delete a work schedule of a member
	 * @param scheduleToDelete
	 */
	public boolean removeWorkSchedule(WorkScheduleEdition scheduleToDelete) {
		return workScheduleHistory.remove(scheduleToDelete);
	}


	/**
	 * @param index
	 * @return schedule at index
	 */
	public WorkScheduleEdition getWorkScheduleHistory(int index) {
		if( (index >= 0) && (index < workScheduleHistory.size()) ){
			return workScheduleHistory.get(index);
		}
		return null;
	}

	/**
	 * schedules size
	 * @return
	 */
	public int getWorkScheduleHistoryCount() {
		return workScheduleHistory.size();
	}
	
	/**
	 * Sort the schedules list
	 */
	public void sortSchedules() {
		Collections.sort(workScheduleHistory, new SchedulesComparator());
	}
	
	
	/**
	 * Small comparator
	 * @author Thomas Luce
	 *
	 */
	private class SchedulesComparator implements Comparator<WorkScheduleEdition>{

		@Override
		public int compare(WorkScheduleEdition o1, WorkScheduleEdition o2) {
			if (o1 == null)
				return -1;
			if (o2 == null)
				return 1;
			
			return o1.getStartDay().compareTo(o2.getStartDay());
		}


	}
  
	//---------------------------------------- PUBLIC ---------------------------------------------------
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((userLinked == null) ? 0 : userLinked.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (userLinked == null) {
			if (other.userLinked != null)
				return false;
		} else if (!userLinked.equals(other.userLinked))
			return false;
		return true;
	}








	
	
	
	
	
	



}
