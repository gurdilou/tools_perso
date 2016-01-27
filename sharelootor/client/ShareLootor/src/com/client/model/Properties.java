package com.client.model;




/**
 * Central node of the application model
 * @author gurdi
 *
 */
public class Properties {
	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private User user;
	private Project project;


	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	public Properties(){
		this.project = new Project();
		this.user = new User(project);
	}
  
  
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
	/**
	 * Add a member to project
	 * @param member
	 */
	public void addMember(Member member) {
		this.project.addMember(member);
	}	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}
	
  
	//---------------------------------------- PUBLIC ---------------------------------------------------

  
}
