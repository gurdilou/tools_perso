package com.client.screens.predictionscreen;

import com.client.screens.ScreenManager;
import com.client.screens.ShareLootorScreen;
import com.client.screens.predictionscreen.adminprojectpart.AdminProjectScreenPart;
import com.client.screens.predictionscreen.editmemberspart.EditMembersScreenPart;
import com.client.screens.predictionscreen.predictchartpart.PredictChartScreenPart;
import com.client.screens.predictionscreen.translation.PredictionScreenTranslationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;

/**
 * Screen wich will show the estimation of profit per person
 * @author gurdi
 *
 */
public class PredictionScreen extends ShareLootorScreen {
	//---------------------------------------- CONSTANTS ------------------------------------------------
	//translation
	private static PredictionScreenTranslationConstants constants = GWT.create(PredictionScreenTranslationConstants.class);

	//---------------------------------------- VARIABLES ------------------------------------------------
	//Variables

	//HTML elems
	private AdminProjectScreenPart panelAdminProject;
	private EditMembersScreenPart panelEditUsers;
	private PredictChartScreenPart predictPanel;
	private DisclosurePanel panelAdminProjectCtnr;
	private DisclosurePanel panelEditUsersCtnr;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * constructor
	 * @param app
	 */
	public PredictionScreen(ScreenManager app) {
		super(app);
		
		//Screen parts
		//Initialisation according to user rights
		this.panelAdminProjectCtnr = new DisclosurePanel(constants.projectAdministration());
		this.panelAdminProject = new AdminProjectScreenPart(this);
		this.panelEditUsersCtnr = new DisclosurePanel(constants.membersAdministration());
		this.panelEditUsers = new EditMembersScreenPart(this);
		//Always here
		this.predictPanel = new PredictChartScreenPart(this);
		
		//other elems
		buildScreen();
		setStylesScreen();
		addEventsScreen();
		
		//Load chart
		predictPanel.onDataReceived();
	}



	//---------------------------------------- PRIVATE --------------------------------------------------
	/**
	 * structure
	 */
	private void buildScreen() {
		panelAdminProjectCtnr.add(panelAdminProject);
		this.add(panelAdminProjectCtnr);
		panelEditUsersCtnr.add(panelEditUsers);
		this.add(panelEditUsersCtnr);			
		this.add(predictPanel);
	}

	/**
	 * CSS
	 */
	private void setStylesScreen() {
		addStyleName("SL-PredictionScreen");
		panelAdminProjectCtnr.addStyleName("PS-panelAdminProjectCtnr");
		panelAdminProjectCtnr.setWidth("100%");
		panelAdminProjectCtnr.setAnimationEnabled(true);
		panelEditUsersCtnr.addStyleName("PS-panelEditUsersCtnr");
		panelEditUsersCtnr.setWidth("100%");
		panelEditUsersCtnr.setAnimationEnabled(true);
	}
	
	/**
	 * Add listeners
	 */
	private void addEventsScreen() {
		panelAdminProjectCtnr.addOpenHandler(new OpenHandler<DisclosurePanel>() {
			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				panelAdminProject.onShow();
				predictPanel.refreshChart();
			}
		});
		panelAdminProjectCtnr.addCloseHandler(new CloseHandler<DisclosurePanel>() {
			@Override
			public void onClose(CloseEvent<DisclosurePanel> event) {
				predictPanel.refreshChart();
			}
		});
		panelEditUsersCtnr.addOpenHandler(new OpenHandler<DisclosurePanel>() {
			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				panelEditUsers.onShow();
				predictPanel.refreshChart();
			}
		});
		panelEditUsersCtnr.addCloseHandler(new CloseHandler<DisclosurePanel>() {
			@Override
			public void onClose(CloseEvent<DisclosurePanel> event) {
				predictPanel.refreshChart();
			}
		});
	}
	
	/**
	 * @return heights panels
	 */
	public int getPanelsHeight() {
		return panelEditUsersCtnr.getOffsetHeight() + panelAdminProjectCtnr.getOffsetHeight();
	}

	/**
	 * When a data has been updated, refresh graphics
	 */
	public void onDataChange() {
		this.predictPanel.onDataReceived();
	}

	//---------------------------------------- GETTER SETTER---------------------------------------------


	//---------------------------------------- PUBLIC ---------------------------------------------------




}
