package com.client.screens.loginscreen;

import com.client.constants.AppConstants;
import com.client.requests.loginscreen.LoginScreenRequester;
import com.client.screens.ScreenManager;
import com.client.screens.ShareLootorScreen;
import com.client.screens.loginscreen.translation.LoginScreenTranslation;
import com.client.screens.predictionscreen.PredictionScreen;
import com.client.widgets.HomeDatePicker;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * The login screen
 * @author gurdi
 *
 */
public class LoginScreen extends ShareLootorScreen{
	//---------------------------------------- CONSTANTS ------------------------------------------------
	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private LoginScreenTranslation constants = GWT.create(LoginScreenTranslation.class);
	
	private VerticalPanel longinPanelCtn;
	private LoginScreenRequester requester;
	private Button signInButt;
	private TextBox projectBox;
	private TextBox idBox;
	private PasswordTextBox pwdBox;
	private Label projectField;
	private Label idField;
	private Label pwdField;
	private Label newProjectField;
	private TextBox newProjectBox;
	private Label newProjectAdminField;
	private TextBox newProjectAdminBox;
	private Label newProjectAdminPwdField;
	private PasswordTextBox newProjectAdminPwdBox;
	private Button createProjectButt;
	private Label newProjectStartDateField;
	private HomeDatePicker newProjectStartDateValue;

	private Label newProjectEmailField;

	private TextBox newProjectEmailInput;

	private FlexTable createProjectFormFieldsCtnr;
	
	
	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * @param screenManager 
	 */
	public LoginScreen(ScreenManager screenManager){
		super(screenManager);
		
		//variables
		this.requester = new LoginScreenRequester(this);
				
				
		//HTML elems
		VerticalPanel mainPanel = new VerticalPanel();
		HTMLPanel loginPanel = new HTMLPanel("");
		this.longinPanelCtn		= new VerticalPanel();
		initUpperForm();
		initLowerForm();
		
		//Archi
		this.add(mainPanel);
		mainPanel.add(loginPanel);
		loginPanel.add(longinPanelCtn);
		
		
		//CSS
		this.addStyleName("SL-loginScreen");
	}
	//---------------------------------------- PRIVATE --------------------------------------------------

	/**
	 * Initiaze longin form
	 */
	private void initUpperForm() {
		FlexTable loginFormFieldsCtnr = new FlexTable();
		this.projectField = new Label(constants.project());
		this.projectBox = new TextBox();
		this.idField = new Label(constants.login());
		this.idBox = new TextBox();
		this.pwdField = new Label(constants.password());
		this.pwdBox = new PasswordTextBox();
		
		this.signInButt = new Button(constants.signIn());
		
		loginFormFieldsCtnr.setWidget(0, 0, projectField);
		loginFormFieldsCtnr.setWidget(0, 1, projectBox);
		loginFormFieldsCtnr.setWidget(1, 0, idField);
		loginFormFieldsCtnr.setWidget(1, 1, idBox);
		loginFormFieldsCtnr.setWidget(2, 0, pwdField);
		loginFormFieldsCtnr.setWidget(2, 1, pwdBox);

		pwdBox.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
					trySignIn();
				}
			}
		});
		
		
		loginFormFieldsCtnr.setWidget(3, 1, signInButt);
		signInButt.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				trySignIn();
			}
		});
		
		longinPanelCtn.add(loginFormFieldsCtnr);
	}
	
	/**
	 * Try to sign in
	 */
	private void trySignIn() {
		if(checkLoginForm()){
			signInButt.setEnabled(false);
			requester.doLogin(projectBox.getText(), idBox.getText(), pwdBox.getText());
		}
	}

	/**
	 * Check login form fields
	 * @return
	 */
	private boolean checkLoginForm() {
		projectField.removeStyleName(AppConstants.CSS_ERROR_FIELD);
		projectBox.removeStyleName(AppConstants.CSS_ERROR_BOX);
		idField.removeStyleName(AppConstants.CSS_ERROR_FIELD);
		idBox.removeStyleName(AppConstants.CSS_ERROR_BOX);
		pwdField.removeStyleName(AppConstants.CSS_ERROR_FIELD);
		pwdBox.removeStyleName(AppConstants.CSS_ERROR_BOX);
		if(!projectBox.getText().isEmpty() && !idBox.getText().isEmpty() && !pwdBox.getText().isEmpty()){
			return true;
		}else{
			if(projectBox.getText().isEmpty()){
				projectField.addStyleName(AppConstants.CSS_ERROR_FIELD);
				projectBox.addStyleName(AppConstants.CSS_ERROR_BOX);
			}
			if(idBox.getText().isEmpty()){
				idField.addStyleName(AppConstants.CSS_ERROR_FIELD);
				idBox.addStyleName(AppConstants.CSS_ERROR_BOX);
			}
			if(pwdBox.getText().isEmpty()){
				pwdField.addStyleName(AppConstants.CSS_ERROR_FIELD);
				pwdBox.addStyleName(AppConstants.CSS_ERROR_BOX);
			}
			return false;
		}
	}

	private boolean checkCreateForm() {
		newProjectField.removeStyleName(AppConstants.CSS_ERROR_FIELD);
		newProjectBox.removeStyleName(AppConstants.CSS_ERROR_BOX);
		newProjectAdminField.removeStyleName(AppConstants.CSS_ERROR_FIELD);
		newProjectAdminBox.removeStyleName(AppConstants.CSS_ERROR_BOX);
		newProjectAdminPwdField.removeStyleName(AppConstants.CSS_ERROR_FIELD);
		newProjectAdminPwdBox.removeStyleName(AppConstants.CSS_ERROR_BOX);
		newProjectEmailField.removeStyleName(AppConstants.CSS_ERROR_FIELD);
		newProjectEmailInput.removeStyleName(AppConstants.CSS_ERROR_BOX);
		
		if(createProjectFormFieldsCtnr.getRowCount() == 7){
			createProjectFormFieldsCtnr.removeRow(4);
		}
		
		boolean valid = true;
		if(newProjectBox.getText().isEmpty()){
			newProjectField.addStyleName(AppConstants.CSS_ERROR_FIELD);
			newProjectBox.addStyleName(AppConstants.CSS_ERROR_BOX);
			valid = false;
		}
		if(newProjectAdminBox.getText().isEmpty()){
			newProjectAdminField.addStyleName(AppConstants.CSS_ERROR_FIELD);
			newProjectAdminBox.addStyleName(AppConstants.CSS_ERROR_BOX);
			valid = false;
		}
		if(newProjectAdminPwdBox.getText().isEmpty()){
			newProjectAdminPwdField.addStyleName(AppConstants.CSS_ERROR_FIELD);
			newProjectAdminPwdBox.addStyleName(AppConstants.CSS_ERROR_BOX);
			valid = false;
		}

		//email format invalid
		if(newProjectEmailInput.getText().isEmpty()){
			newProjectEmailField.addStyleName(AppConstants.CSS_ERROR_FIELD);
			newProjectEmailInput.addStyleName(AppConstants.CSS_ERROR_BOX);	
			valid = false;
		}else{
			boolean matchFound = AppConstants.EMAIL_REGEXP.test(newProjectEmailInput.getText());
			if(!matchFound){
				newProjectEmailField.addStyleName(AppConstants.CSS_ERROR_FIELD);
				newProjectEmailInput.addStyleName(AppConstants.CSS_ERROR_BOX);
				
				Label error = new Label(constants.invalidFormat());
				error.addStyleName(AppConstants.CSS_ERROR_FIELD);
				createProjectFormFieldsCtnr.insertRow(4);
				createProjectFormFieldsCtnr.setWidget(4, 1, error);
	
				valid = false;
			}
		}
		
		return valid;
	}

	/**
	 * Initialize create project form
	 */
	private void initLowerForm() {
		DisclosurePanel lowerOpenPanel = new DisclosurePanel(constants.createNewProject());
		
		
		this.createProjectFormFieldsCtnr = new FlexTable();
		this.newProjectField = new Label(constants.newProject());
		this.newProjectBox = new TextBox();
		this.newProjectAdminField = new Label(constants.adminLogin());
		this.newProjectAdminBox = new TextBox();
		this.newProjectAdminPwdField = new Label(constants.adminPassword());
		this.newProjectAdminPwdBox = new PasswordTextBox();
		this.newProjectEmailField = new Label(constants.email());
		this.newProjectEmailInput = new TextBox();
		this.newProjectStartDateField = new Label(constants.startDay());
		this.newProjectStartDateValue = new HomeDatePicker();

		
		this.createProjectButt = new Button(constants.create());
		
		createProjectFormFieldsCtnr.setWidget(0, 0, newProjectField);
		createProjectFormFieldsCtnr.setWidget(0, 1, newProjectBox);
		createProjectFormFieldsCtnr.setWidget(1, 0, newProjectAdminField);
		createProjectFormFieldsCtnr.setWidget(1, 1, newProjectAdminBox);
		createProjectFormFieldsCtnr.setWidget(2, 0, newProjectAdminPwdField);
		createProjectFormFieldsCtnr.setWidget(2, 1, newProjectAdminPwdBox);
		createProjectFormFieldsCtnr.setWidget(3, 0, newProjectEmailField);
		createProjectFormFieldsCtnr.setWidget(3, 1, newProjectEmailInput);
		createProjectFormFieldsCtnr.setWidget(4, 0, newProjectStartDateField);
		createProjectFormFieldsCtnr.setWidget(4, 1, newProjectStartDateValue);
		
		
		createProjectFormFieldsCtnr.setWidget(5, 1, createProjectButt);
		createProjectButt.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(checkCreateForm()){
					createProjectButt.setEnabled(false);
					requester.doCreateProject(newProjectBox.getText(), newProjectAdminBox.getText(), 
							newProjectAdminPwdBox.getText(), 
							newProjectStartDateValue.getValue(),
							newProjectEmailInput.getText());
				}
			}
		});
		longinPanelCtn.add(lowerOpenPanel);
		lowerOpenPanel.add(createProjectFormFieldsCtnr);
		
	}
	//---------------------------------------- PUBLIC ---------------------------------------------------
	/**
	 * When the login is valid
	 * Login will be checked at each action
	 */
	public void onValidLogin() {
		getApp().switchScreens( new PredictionScreen(getApp()) );
	}
	/**
	 * When login is not valid
	 */
	public void onWrongLogin() {
		signInButt.setEnabled(true);
		createProjectButt.setEnabled(true);
	}
}
