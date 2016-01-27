package com.client.constants;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.regexp.shared.RegExp;

public class AppConstants {
	//---------------------------------------- CONSTANTS ------------------------------------------------
	public static DateTimeFormat MODEL_TIME_FORMAT = DateTimeFormat.getFormat("yyyy-MM-dd");
	public static DateTimeFormat VIEW_TIME_FORMAT = DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM);
	
	public static final String CSS_ERROR_FIELD = "errorLabel";
	public static final String CSS_ERROR_BOX = "errorBox";
	
	public static RegExp EMAIL_REGEXP = RegExp.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
	
	//---------------------------------------- VARIABLES ------------------------------------------------


	//---------------------------------------- CONSTRUCTOR ----------------------------------------------

  
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------



}
