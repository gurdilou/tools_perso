package com.client.screens;

import com.google.gwt.user.client.ui.HTMLPanel;


/**
 * Mother classes of screens
 * @author gurdi
 *
 */
public class ShareLootorScreen extends HTMLPanel{
	//---------------------------------------- VARIABLES ------------------------------------------------
	
	private ScreenManager app;


	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	public ShareLootorScreen(ScreenManager app) {
		super("");
		this.app = app;
		
		this.setSize("100%", "100%");
		this.addStyleName("SL-ShareLootorScreen");
	}

	
	//---------------------------------------- PRIVATE --------------------------------------------------
	
	//---------------------------------------- PUBLIC ---------------------------------------------------

	/**
	 * @return the app
	 */
	public ScreenManager getApp() {
		return app;
	}
}
