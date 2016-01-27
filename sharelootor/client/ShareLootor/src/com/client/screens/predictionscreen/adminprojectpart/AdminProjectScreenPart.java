package com.client.screens.predictionscreen.adminprojectpart;


import com.client.model.Project;
import com.client.screens.predictionscreen.PredictionScreen;
import com.client.screens.predictionscreen.PredictionScreenPart;
import com.client.widgets.HomeDatePicker;
import com.client.widgets.workscheduletable.WorkScheduleTable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class AdminProjectScreenPart extends PredictionScreenPart{


	//---------------------------------------- CONSTANTS ------------------------------------------------
	private static final int TABLE_HEIGHT = 100;
	
	//---------------------------------------- VARIABLES ------------------------------------------------
	//variables
	private Project proj;
	private boolean dataFilled = false;
	
	//HTML elems
	private Label projectNameField;
	private Label projectNameValue;
	private FlexTable maintable;
	private Label startDateField;
	private HomeDatePicker startDateValue;
	private Label estimatedIncomeField;
	private TextBox estimatedIncomeValue;
	private Label defaultWorkScheduleField;
	private WorkScheduleTable defaultWorkScheduleTable;
	private Button buttSaveEdition;
	private Label currencySymbolField;
	private TextBox currencySymbolValue;


	

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * Constructor
	 * @param predictionScreen
	 */
	public AdminProjectScreenPart(PredictionScreen predictionScreen) {
		super();
		
		this.maintable = new FlexTable();
		
		this.proj = predictionScreen.getApp().getProps().getProject();
		
		this.projectNameField = new Label("Project");
		this.projectNameValue = new Label(proj.getName());
		
		
		this.startDateField = new Label("Start date");
		this.startDateValue	= new HomeDatePicker();
		startDateValue.setValue(proj.getStartDay());
		startDateValue.setEnabled(false);
		
		this.estimatedIncomeField = new Label("Estimated income");
		this.estimatedIncomeValue = new TextBox();
		estimatedIncomeValue.setText( new Float(proj.getEstimatedIncome()).toString() );
		estimatedIncomeValue.setEnabled(false);
		
		this.currencySymbolField = new Label("Currency symbol");
		this.currencySymbolValue = new TextBox();
		currencySymbolValue.setText(proj.getCurrencySymbol());
		currencySymbolValue.setEnabled(false);
		
		this.defaultWorkScheduleField = new Label("Default work schedule");
		this.defaultWorkScheduleTable = new WorkScheduleTable();
		
		this.buttSaveEdition = new Button("Save");
		buttSaveEdition.setEnabled(false);
		
		
		buildScreenPart();
		setStylesScreenPart();
		
		
	}

	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
	/**
	 * Build
	 */
	private void buildScreenPart() {
		this.add(maintable);
		maintable.setWidget(0, 0, projectNameField);
		maintable.setWidget(0, 1, projectNameValue);

		maintable.setWidget(0, 6, buttSaveEdition);
		
		maintable.setWidget(1, 0, startDateField);
		maintable.setWidget(1, 1, startDateValue);
		maintable.setWidget(1, 2, estimatedIncomeField);
		maintable.setWidget(1, 3, estimatedIncomeValue);
		maintable.setWidget(1, 4, currencySymbolField);
		maintable.setWidget(1, 5, currencySymbolValue);

		maintable.setWidget(2, 0, defaultWorkScheduleField);
		maintable.setWidget(3, 0, defaultWorkScheduleTable);


	}
	
	/**
	 * css
	 */
	private void setStylesScreenPart() {
		//csS
		this.addStyleName("PS-AdminProjectScreenPart");
		maintable.addStyleName("APS-mainTable");
		projectNameField.addStyleName("APS-projectNameField");
		projectNameValue.addStyleName("APS-projectNameValue");
		estimatedIncomeField.addStyleName("APS-estimatedIncomeField");
		estimatedIncomeValue.addStyleName("APS-estimatedIncomeValue");
		currencySymbolField.addStyleName("APS-currencySymbolField");
		currencySymbolValue.addStyleName("APS-currencySymbolValue");
		startDateField.addStyleName("APS-startDateField");
		startDateValue.addStyleName("APS-startDateValue");
		defaultWorkScheduleField.addStyleName("APS-defaultWorkScheduleField");
		defaultWorkScheduleTable.addStyleName("APS-defaultWorkScheduleTable");
		
		//Width & co
		maintable.setWidth("100%");
		
		maintable.getFlexCellFormatter().setColSpan(2, 0, 7);
		maintable.getFlexCellFormatter().setColSpan(3, 0, 7);
		defaultWorkScheduleTable.setHeight(TABLE_HEIGHT+"px");
	}
    
  
	//---------------------------------------- PUBLIC ---------------------------------------------------
	/**
	 * Fill table content
	 */
	public void onShow(){
		if(!dataFilled){
			defaultWorkScheduleTable.addLine(proj.getDefaultWorkSchedule());
			dataFilled =  true;
		}
	}


}
