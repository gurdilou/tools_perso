package com.client.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Object containing project properties
 * @author gurdi
 *
 */
public class Project {
	//---------------------------------------- CONSTANTS ------------------------------------------------
	private static final float DEFAULT_INCOME = 100000;
	
	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private String name;
	private Date startDay;
	private WorkScheduleEdition defaultWorkSchedule;
	private float estimatedIncome;
	private String currencySymbol;
	private ArrayList<Member> listMembers;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * Project
	 */
	public Project(){
		this.name 					= "";
		this.startDay				= new Date();
		this.estimatedIncome		= DEFAULT_INCOME;
		this.currencySymbol			= "";
		this.listMembers 			= new ArrayList<Member>();
		this.defaultWorkSchedule 	= new WorkScheduleEdition(startDay, new WorkSchedule());
	}

	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

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
		defaultWorkSchedule.setStartDay(startDay);
	}

	/**
	 * @return the defaultWorkSchedule
	 */
	public WorkScheduleEdition getDefaultWorkSchedule() {
		return defaultWorkSchedule;
	}

	/**
	 * @param defaultWorkSchedule the defaultWorkSchedule to set
	 */
	public void setDefaultWorkSchedule(WorkScheduleEdition defaultWorkSchedule) {
		this.defaultWorkSchedule = defaultWorkSchedule;
	}

	/**
	 * @return the estimatedIncome
	 */
	public float getEstimatedIncome() {
		return estimatedIncome;
	}

	/**
	 * @param estimatedIncome the estimatedIncome to set
	 */
	public void setEstimatedIncome(float estimatedIncome) {
		this.estimatedIncome = estimatedIncome;
	}

	/**
	 * @return the currencySymbol
	 */
	public String getCurrencySymbol() {
		return currencySymbol;
	}

	/**
	 * @param currencySymbol the currencySymbol to set
	 */
	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	/**
	 * Add a member to the team
	 * @param member
	 */
	public void addMember(Member member) {
		this.listMembers.add(member);
	}

	/**
	 * @return the listMembers
	 */
	public ArrayList<Member> getListMembers() {
		return listMembers;
	}

	
	
	
	//---------------------------------------- PUBLIC ---------------------------------------------------



}
