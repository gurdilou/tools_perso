package com.client.parsers;

import com.client.model.User;
import com.google.gwt.json.client.JSONObject;

/**
 * User parser
 * @author gurdi
 *
 */
public class JSONUserParser {
	//---------------------------------------- CONSTANTS ------------------------------------------------
	private static final String KEY_RIGHT_ADMIN = "rightAdmin";
	private static final String KEY_RIGHT_EDIT_USERS = "rightEditUsers";
	private static final String KEY_LOGIN = "login";


	//---------------------------------------- VARIABLES ------------------------------------------------


	//---------------------------------------- CONSTRUCTOR ----------------------------------------------


	//---------------------------------------- PRIVATE --------------------------------------------------


	//---------------------------------------- GETTER SETTER---------------------------------------------


	//---------------------------------------- PUBLIC ---------------------------------------------------
	/**
	 * fill user properties
	 * @param user
	 * @param jsonResponse
	 */
	public void fillUser(User user, JSONObject userJSON) {

		if(userJSON.containsKey(KEY_LOGIN)){
			user.setLogin(userJSON.get(KEY_LOGIN).isString().stringValue());
		}

		if(userJSON.containsKey(KEY_RIGHT_ADMIN)){
			user.setHasRightAdmin(userJSON.get(KEY_RIGHT_ADMIN).isBoolean().booleanValue());
		}

		if(userJSON.containsKey(KEY_RIGHT_EDIT_USERS)){
			user.setHasRightEditUsers(userJSON.get(KEY_RIGHT_EDIT_USERS).isBoolean().booleanValue());
		}
	}

}
