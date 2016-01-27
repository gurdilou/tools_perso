package com.client.widgets.loadingmanager;

/**
 * Manage loading request, and parallel requests
 * @author gurdi
 *
 */
public class LoadingManager {
	//---------------------------------------- CONSTANTS ------------------------------------------------


	//---------------------------------------- VARIABLES ------------------------------------------------
	private int currentNbLoadings;
	private LoadingDialogBox loadingWidget;

	
	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * 
	 */
	public LoadingManager(){
		this.currentNbLoadings = 0;

		this.loadingWidget = new LoadingDialogBox();
	}

	//---------------------------------------- PRIVATE --------------------------------------------------
	/**
	 * Show the loading widget
	 */
	private void showLoadingPanelWidget() {
		loadingWidget.show();
	}	
	/**
	 * Hide the loading widget
	 */
	private void hideLoadingPanelWidget() {
		loadingWidget.hide();
	}

	//---------------------------------------- GETTER SETTER---------------------------------------------


	//---------------------------------------- PUBLIC ---------------------------------------------------
	/**
	 * Block the interface with a loading panel
	 */
	public void showLoadingPanel(){
		this.currentNbLoadings++;

		if(currentNbLoadings == 1){
			showLoadingPanelWidget();
		}
	}
	
	/**
	 * If no other requests, re enable interface
	 */
	public void hideLoadingPanel(){
		this.currentNbLoadings = Math.max(0, currentNbLoadings - 1);
	
		if(currentNbLoadings == 0){
			hideLoadingPanelWidget();
		}
	}


}
