package com.client.requests.errconstants;

import com.google.gwt.core.client.GWT;

public class RequestsErrorTranslator {
	//---------------------------------------- CONSTANTS ------------------------------------------------
	
	//translation
	private static RequestsErrorConstants constants = GWT.create(RequestsErrorConstants.class);
	
	//---------------------------------------- VARIABLES ------------------------------------------------


	//---------------------------------------- CONSTRUCTOR ----------------------------------------------

  
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------
	public static String translateErrorCode(String errorCode, String defaultValue){
		if(errorCode.equals("ERR_PROJECT_ALREADY_EXISTS")){
			return constants.errProjectAlreadyExists();
		}
		if(errorCode.equals("ERR_PROJECT_NOT_EXIST")){
			return constants.errProjectDoesntExist();
		}
		if(errorCode.equals("ERR_WRONG_LOGIN_PWD")){
			return constants.errWrongLoginPassword();
		}
		return defaultValue;
	}



}
