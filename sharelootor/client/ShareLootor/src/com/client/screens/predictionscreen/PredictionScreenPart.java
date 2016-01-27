package com.client.screens.predictionscreen;

import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * General screen part parent widget
 * @author gurdi
 *
 */
public abstract class PredictionScreenPart extends HTMLPanel {

	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------


	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	public PredictionScreenPart() {
		super("");
	}
  
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------
	/**
	 * When the screen part is displayed
	 */
	protected abstract void onShow();

}
