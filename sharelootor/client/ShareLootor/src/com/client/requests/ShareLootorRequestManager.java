package com.client.requests;

import com.client.model.User;

/**
 * Own request manager
 * @author gurdi
 *
 */
public class ShareLootorRequestManager {
	//---------------------------------------- VARIABLES ------------------------------------------------
	
	  
	private User user;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	public ShareLootorRequestManager(User user){
		this.user = user;
	}	
  
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------
	/**
	 * Add basics args
	 * @return
	 */
	protected StringBuilder createBaseRequest(){
		StringBuilder data = new StringBuilder();
		if(user != null){
			user.fillRequest(data);
		}
		return data;
	}
  

	
}
