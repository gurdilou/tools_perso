package com.client.screens.predictionscreen.editmemberspart;

import com.client.constants.AppConstants;
import com.client.model.Member;
import com.client.requests.predictionscreen.editmembers.EditMembersRequester;
import com.client.screens.predictionscreen.editmemberspart.translation.EditMembersTranslationConstants;
import com.client.widgets.MessageFrame;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class PopUpChangePassword extends MessageFrame{


	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------
	//translation
	private static EditMembersTranslationConstants constants = GWT.create(EditMembersTranslationConstants.class);
	
	//Links
	private EditMembersScreenPart parentScreen;
	private Member member;
	
	//html elems
	private FlexTable mainPanel;
	private Label oldPwdField;
	private PasswordTextBox oldPwdInput;
	private Label newPwdField;
	private PasswordTextBox newPwdInput;
	private Label newPwdConfirmField;
	private PasswordTextBox newPwdConfirmInput;
	private Button buttOk;
	private Button buttCancel;



	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	public PopUpChangePassword(EditMembersScreenPart parentScreen, Member member) {
		super(constants.popUpChangePasswordTitle());
		
		this.parentScreen = parentScreen;
		this.member = member;
		
		//init
		this.mainPanel = new FlexTable();
		this.oldPwdField = new Label(constants.oldPwd());
		this.oldPwdInput = new PasswordTextBox();
		this.newPwdField = new Label(constants.newPwd());
		this.newPwdInput = new PasswordTextBox();
		this.newPwdConfirmField = new Label(constants.newPwdConfirm());
		this.newPwdConfirmInput = new PasswordTextBox();
		
		this.buttOk = new Button(constants.ok());
		this.buttCancel = new Button(constants.cancel());
		
		buildPopup();
		setStylesPopup();
		addEventsPopup();
	}

	//---------------------------------------- PRIVATE --------------------------------------------------
	/**
	 * Build frame
	 */
	private void buildPopup() {
		setContent(mainPanel);
		mainPanel.setWidget(0, 0, oldPwdField);
		mainPanel.setWidget(0, 1, oldPwdInput);
		mainPanel.setWidget(1, 0, newPwdField);
		mainPanel.setWidget(1, 1, newPwdInput);
		mainPanel.setWidget(2, 0, newPwdConfirmField);
		mainPanel.setWidget(2, 1, newPwdConfirmInput);
		
		addTwoButtons(buttOk, buttCancel, false);
	}	
	
	/**
	 * CSS
	 */
	private void setStylesPopup() {
		
		//classes
		this.addStyleName("ESP-PopUpChangePassword");
		mainPanel.addStyleName("PUCP-mainPanel");
		oldPwdField.addStyleName("PUCP-oldPwdField");
		oldPwdInput.addStyleName("PUCP-oldPwdInput");
		newPwdField.addStyleName("PUCP-newPwdField");
		newPwdInput.addStyleName("PUCP-newPwdInput");
		newPwdConfirmField.addStyleName("PUCP-newPwdConfirmField");
		newPwdConfirmInput.addStyleName("PUCP-newPwdConfirmInput");
	}
	
	/**
	 * Check fields value
	 * @return
	 */
	private boolean checkFields() {
		oldPwdField.removeStyleName(AppConstants.CSS_ERROR_FIELD);
		oldPwdInput.removeStyleName(AppConstants.CSS_ERROR_BOX);
		newPwdField.removeStyleName(AppConstants.CSS_ERROR_FIELD);
		newPwdInput.removeStyleName(AppConstants.CSS_ERROR_BOX);
		newPwdConfirmField.removeStyleName(AppConstants.CSS_ERROR_FIELD);
		newPwdConfirmInput.removeStyleName(AppConstants.CSS_ERROR_BOX);
		if(mainPanel.getRowCount() == 4){
			mainPanel.removeRow(3);
		}
		
		boolean result = true;

		if(oldPwdInput.getText().isEmpty()){
			oldPwdField.addStyleName(AppConstants.CSS_ERROR_FIELD);
			oldPwdInput.addStyleName(AppConstants.CSS_ERROR_BOX);
			result = false;
		}
		if(newPwdInput.getText().isEmpty()){
			newPwdField.addStyleName(AppConstants.CSS_ERROR_FIELD);
			newPwdInput.addStyleName(AppConstants.CSS_ERROR_BOX);
			result = false;
		}
		if(newPwdConfirmInput.getText().isEmpty()){
			newPwdConfirmField.addStyleName(AppConstants.CSS_ERROR_FIELD);
			newPwdConfirmInput.addStyleName(AppConstants.CSS_ERROR_BOX);
			result = false;
		}
		//if new pwd != confirmation
		if(!newPwdInput.getText().equals(newPwdConfirmInput.getText())){
			Label error = new Label(constants.pwdMismatch());
			error.addStyleName(AppConstants.CSS_ERROR_FIELD);
			mainPanel.setWidget(3, 1, error);
			newPwdConfirmInput.addStyleName(AppConstants.CSS_ERROR_BOX);
			newPwdInput.addStyleName(AppConstants.CSS_ERROR_BOX);
			result = false;
		}
		return result;
	}
	
	/**
	 * Listeners
	 */
	private void addEventsPopup() {
		buttOk.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(checkFields()){
					EditMembersRequester requester = new EditMembersRequester(parentScreen);
					requester.doChangePassword(member, oldPwdInput.getText(), newPwdConfirmInput.getText());
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
	

  
	//---------------------------------------- GETTER SETTER---------------------------------------------
  
  
	//---------------------------------------- PUBLIC ---------------------------------------------------



}
