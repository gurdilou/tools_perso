package com.client.widgets.loadingmanager;

import com.google.gwt.user.client.ui.DialogBox;

/**
 * The loading widget, wich block interface
 * @author gurdi
 *
 */
public class LoadingDialogBox implements LoadingWidgetInterface{
	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------


	private DialogBox popup;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	public LoadingDialogBox(){
		this.popup = new DialogBox();
		
		popup.setText("Loading...");

		popup.setAnimationEnabled(true);
		popup.setGlassEnabled(true);
	}
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------

	@Override
	public void show() {
		popup.center();
	}

	@Override
	public void hide() {
		popup.hide();
	}

  


}
