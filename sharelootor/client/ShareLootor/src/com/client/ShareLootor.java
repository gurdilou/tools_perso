package com.client;

import com.client.screens.ScreenManager;
import com.client.widgets.MessageFrame;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ShareLootor implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		//Handling uncaught exceptions
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable e) {
				GWT.log(e.getMessage(), e);
				MessageFrame errorFrame = new MessageFrame("Error");
				errorFrame.addOneButton(new Button("OK :("));
				
				StringBuilder stackStr = new StringBuilder("Error stack");
				for(int i = 0; i < e.getStackTrace().length; i++){
					stackStr.append( e.getStackTrace()[i].toString() );
				}
				
				errorFrame.setMessage(e.getMessage()+"<br/><br/>"+stackStr.toString(), "errorLabel");
				errorFrame.center();
			}
		});
		
		ScreenManager manager = new ScreenManager(this);
		manager.init();
	}
}
