package com.client.screens;

import com.client.ShareLootor;
import com.client.model.Properties;
import com.client.model.User;
import com.client.screens.loginscreen.LoginScreen;
import com.client.widgets.loadingmanager.LoadingManager;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Manager in charge of app structure and screen switching
 * @author gurdi
 *
 */
public class ScreenManager {

	//---------------------------------------- VARIABLES ------------------------------------------------
	//variables
	private Properties properties;
	private static LoadingManager loadingManager = new LoadingManager();
	
	//HTML elems
	private ShareLootorScreen currentScreen;
	private HTMLPanel mainPanel;





	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * @param shareLootor 
	 * 
	 */
	public ScreenManager(ShareLootor shareLootor){
		this.properties = new Properties();

		
		RootPanel.get().addStyleName("ShareLootor-rootPanel");
		this.mainPanel = new HTMLPanel("");
		mainPanel.addStyleName("SL-appPanel");
		RootPanel.get().add(mainPanel);
		
		
		setStyles();

	}
	//---------------------------------------- PRIVATE --------------------------------------------------
	/**
	 * build
	 */
	private void setStyles() {
        Window.enableScrolling(false);
        Window.setMargin("0px");

		
		RootPanel.get().setSize("100%", "100%");
		RootPanel.get().getElement().getStyle().setPosition(Position.ABSOLUTE);
		RootPanel.get().getElement().getStyle().setLeft(0, Unit.PX);
		RootPanel.get().getElement().getStyle().setTop(0, Unit.PX);
		RootPanel.get().getElement().getStyle().setOverflow(Overflow.HIDDEN);

		mainPanel.setSize("100%", "100%");
	}

	//---------------------------------------- GETTER SETTER---------------------------------------------
	/**
	 * @return the properties
	 */
	public Properties getProps() {
		return properties;
	}
	/**
	 * @return the current user
	 */
	public User getUser() {
		return properties.getUser();
	}

	//---------------------------------------- PUBLIC ---------------------------------------------------
	/**
	 * Init app with login screen
	 */
	public void init(){
		this.currentScreen = new LoginScreen(this);
		mainPanel.add(currentScreen);
	}
	
	/**
	 * Set a new screen
	 * @param newScreen
	 * @return the old one, can be null
	 */
	public ShareLootorScreen switchScreens(ShareLootorScreen newScreen){
		ShareLootorScreen oldOne = currentScreen;
		this.currentScreen = newScreen;
		mainPanel.clear();
		mainPanel.add(newScreen);
		
		return oldOne;
	}

	/**
	 * Show a loading panel during an action
	 */
	public static void showLoadingPanel(){
		loadingManager.showLoadingPanel();
	}
	/**
	 * hide the loading panel
	 */
	public static void hideLoadingPanel(){
		loadingManager.hideLoadingPanel();
	}


}
