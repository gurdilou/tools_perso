package com.client.screens.predictionscreen.editmemberspart;

import com.client.constants.AppConstants;
import com.client.model.Member;
import com.client.model.Properties;
import com.client.model.User;
import com.client.requests.predictionscreen.editmembers.EditMembersRequester;
import com.client.screens.predictionscreen.editmemberspart.translation.EditMembersTranslationConstants;
import com.client.widgets.HomeDatePicker;
import com.client.widgets.MessageFrame;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Small popup in order to add a member to the project
 * @author Thomas Luce
 *
 */
public class PopUpAddMember extends MessageFrame{


	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------
	//translation
	private static EditMembersTranslationConstants constants = GWT.create(EditMembersTranslationConstants.class);
	
	//links
	private EditMembersScreenPart editMembersScreenPart;	
	private Properties props;
	private int baseNbLine;
	private int indexInsertLineEmail;
	
	//HTML elems
	private FlexTable mainPanel;
	private Label nameField;
	private TextBox nameInput;
	private Label emailField;
	private TextBox emailInput;
	private CheckBox hasAdminRightInput;
	private CheckBox hasEditUserRightInput;
	private Button buttOk;
	private Button buttCancel;
	private Label startDayField;
	private HomeDatePicker startDayInput;
	private Label hasAdminRightField;
	private Label hasEditUserRightField;







	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * 
	 * @param editMembersScreenPart
	 */
	public PopUpAddMember(EditMembersScreenPart editMembersScreenPart) {
		super(constants.addMemberPopUpTitle());
		
		this.editMembersScreenPart = editMembersScreenPart;
		this.props = editMembersScreenPart.getProps();
		
		//init
		this.mainPanel = new FlexTable();
		this.nameField = new Label(constants.name());
		this.nameInput = new TextBox();
		this.emailField = new Label(constants.email());
		this.emailInput = new TextBox();
		this.startDayField = new Label(constants.startDay());
		this.startDayInput = new HomeDatePicker();
		this.hasAdminRightField = new Label(constants.adminRights());
		this.hasAdminRightInput = new CheckBox();
		this.hasEditUserRightField = new Label(constants.editUserRights());
		this.hasEditUserRightInput = new CheckBox();
		
		this.buttOk = new Button(constants.ok());
		this.buttCancel = new Button(constants.cancel());
		
		//Build
		buildPopupAddMember();
		addEventsPopupAddMember();
		

	
	}




	//---------------------------------------- PRIVATE --------------------------------------------------
	/**
	 * build
	 */
	private void buildPopupAddMember() {
		this.setContent(mainPanel);
		int cursorLine = 0;
		mainPanel.setWidget(cursorLine, 0, nameField);
		mainPanel.setWidget(cursorLine, 1, nameInput);
		cursorLine++;
		
		mainPanel.setWidget(cursorLine, 0, emailField);
		mainPanel.setWidget(cursorLine, 1, emailInput);
		cursorLine++;
		this.indexInsertLineEmail = cursorLine;
		
		
		if(props.getUser().hasRightAdmin()){
			mainPanel.setWidget(cursorLine, 0, hasAdminRightField);
			mainPanel.setWidget(cursorLine, 1, hasAdminRightInput);
			cursorLine ++;
			
			mainPanel.setWidget(cursorLine, 0, hasEditUserRightField);
			mainPanel.setWidget(cursorLine, 1, hasEditUserRightInput);
			cursorLine++;
		}
		
		mainPanel.setWidget(cursorLine, 0, startDayField);
		mainPanel.setWidget(cursorLine, 1, startDayInput);
		cursorLine++;
		
		
		
		this.baseNbLine = mainPanel.getRowCount();
		addTwoButtons(buttOk, buttCancel, false);
	}
	/**
	 * Add listeners
	 */
	private void addEventsPopupAddMember() {
		buttOk.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(formIsValid()){
					EditMembersRequester requester = new EditMembersRequester(editMembersScreenPart);
					Member newMember = createMemberFromFields();
					requester.doAddMember(newMember, emailInput.getText(), startDayInput.getValue(), 
								hasAdminRightInput.getValue(), 
								hasEditUserRightInput.getValue());
					hide();
				}
			}
		});
		buttCancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
	}



	/**
	 * @return true if panel correctly filled
	 */
	private boolean formIsValid() {
		boolean valid = true;
		nameField.removeStyleName(AppConstants.CSS_ERROR_FIELD);
		nameInput.removeStyleName(AppConstants.CSS_ERROR_BOX);
		emailField.removeStyleName(AppConstants.CSS_ERROR_FIELD);
		emailInput.removeStyleName(AppConstants.CSS_ERROR_BOX);
		
		if(mainPanel.getRowCount() > baseNbLine){
			mainPanel.removeRow(indexInsertLineEmail);
		}
		
		if(nameInput.getText().isEmpty()){
			nameField.addStyleName(AppConstants.CSS_ERROR_FIELD);
			nameInput.addStyleName(AppConstants.CSS_ERROR_BOX);
			valid = false;
		}
		if(emailInput.getText().isEmpty()){
			emailField.addStyleName(AppConstants.CSS_ERROR_FIELD);
			emailInput.addStyleName(AppConstants.CSS_ERROR_BOX);
			valid = false;
		}else{
			boolean matchFound = AppConstants.EMAIL_REGEXP.test(emailInput.getText());
			//email format invalid
			if(!matchFound){
				emailField.addStyleName(AppConstants.CSS_ERROR_FIELD);
				emailInput.addStyleName(AppConstants.CSS_ERROR_BOX);
				Label error = new Label(constants.invalidFormat());
				error.addStyleName(AppConstants.CSS_ERROR_FIELD);
				
				mainPanel.insertRow(indexInsertLineEmail);
				mainPanel.setWidget(indexInsertLineEmail, 1, error);
				valid = false;
			}
		}
		
		return valid;
	}


	/**
	 * Create a member from panel
	 * @return
	 */
	private Member createMemberFromFields() {
		Member newMember = new Member(new User(props.getProject(), nameInput.getText()));
		
		return newMember;
	} 
  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------



}
