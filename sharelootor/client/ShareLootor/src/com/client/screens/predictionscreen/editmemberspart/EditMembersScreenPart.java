package com.client.screens.predictionscreen.editmemberspart;

import java.util.Date;
import java.util.Iterator;

import com.client.constants.AppConstants;
import com.client.model.Member;
import com.client.model.Properties;
import com.client.model.User;
import com.client.model.WorkScheduleEdition;
import com.client.model.WorkingDay;
import com.client.requests.predictionscreen.editmembers.EditMembersRequester;
import com.client.screens.predictionscreen.PredictionScreen;
import com.client.screens.predictionscreen.PredictionScreenPart;
import com.client.screens.predictionscreen.editmemberspart.translation.EditMembersTranslationConstants;
import com.client.screens.predictionscreen.editmemberspart.translation.EditMembersTranslationMessages;
import com.client.widgets.MessageFrame;
import com.client.widgets.workscheduletable.WorkScheduleTable;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;

/**
 * Screen part in charge of members management
 * @author gurdi
 *
 */
public class EditMembersScreenPart extends PredictionScreenPart{


	//---------------------------------------- CONSTANTS ------------------------------------------------
	private static final int LISTBOX_NB_ITEMS = 20;
	private static final int HEIGHT_PART = 360;
	
	//---------------------------------------- VARIABLES ------------------------------------------------
	//translation
	private static EditMembersTranslationConstants constants = GWT.create(EditMembersTranslationConstants.class);
	private static EditMembersTranslationMessages messages = GWT.create(EditMembersTranslationMessages.class);
	
	//variables
	private Properties props;
	private boolean initialized = false;
	private PredictionScreen predictionScreen;
	private boolean schedulesInEdition;			// Set to true when user click on the button to edit the table, goes back to false, if user change member
	
	//html elements
	private SplitLayoutPanel mainSplit;
	private HTMLPanel leftPart;
	private VerticalPanel selectMemberCtnr;
	private Button addMemberButt;
	private Button deleteMemberButt;
	private ListBox selectMemberListBox;
	private HTMLPanel rightPart;
	private Label memberSelectedField;
	private Label memberSelectedValue;
	private CheckBox memberHasAdminRight;
	private CheckBox memberHasEditUserRight;
	private WorkScheduleTable memberWorkScheduleTable;
	private HorizontalPanel leftToolbar;
	private HorizontalPanel memberPropTable;
	private VerticalPanel editMemberCtnr;
	private HorizontalPanel rightToolbar;
	private Button memberAddSchedule;
	private Button memberEditSchedule;
	
	private FlexTable rightFieldsCtnr;
	private Button memberResetPassword;
	private Button memberChangePassword;





	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * Constructor
	 * @param predictionScreen
	 */
	public EditMembersScreenPart(PredictionScreen predictionScreen) {
		super();
		
		this.predictionScreen = predictionScreen;
		this.props = predictionScreen.getApp().getProps();
		
		this.mainSplit = new SplitLayoutPanel();
		this.add(mainSplit);
		setHeight(HEIGHT_PART+"px");
		mainSplit.setHeight("100%");

		this.addStyleName("PS-EditUsersScreenPart");
		
		initLeftpart();
		initRightPart();
		
		addEvents();
		

		
	}

	//---------------------------------------- PRIVATE --------------------------------------------------
	/**
	 * Initialize the left split part
	 */
	private void initLeftpart() {
		//Init
		this.leftPart = new HTMLPanel("");
		this.selectMemberCtnr = new VerticalPanel();
		this.leftToolbar = new HorizontalPanel();
		this.addMemberButt = new Button(constants.add());
		this.deleteMemberButt = new Button(constants.delete());
		this.selectMemberListBox = new ListBox();
		
		//Build
		mainSplit.addWest(leftPart, 200);
		leftPart.add(selectMemberCtnr);
		selectMemberCtnr.add(leftToolbar);
		leftToolbar.add(addMemberButt);
		leftToolbar.add(deleteMemberButt);
		selectMemberCtnr.add(selectMemberListBox);
		
		//CSS
		leftPart.addStyleName("EUSP-leftPart");
		selectMemberCtnr.addStyleName("EUSP-selectMemberCtnr");
		leftToolbar.addStyleName("EUSP-leftToolbar");
		addMemberButt.addStyleName("EUSP-addMemberButt");
		deleteMemberButt.addStyleName("EUSP-deleteMemberButt");
		selectMemberListBox.addStyleName("EUSP-selectMemberLBox");
		
		//Size & co
		boolean canEditMembers = props.getUser().hasRightEditUsers();
		selectMemberCtnr.setSize("100%", "100%");
		addMemberButt.setEnabled(canEditMembers);
		deleteMemberButt.setEnabled(canEditMembers);
		selectMemberListBox.setVisibleItemCount(LISTBOX_NB_ITEMS);
		selectMemberListBox.setWidth("100%");
		mainSplit.setWidgetMinSize(leftPart, 200);
		
		
	}	
	
	/**
	 * Init the right part 
	 */
	private void initRightPart() {
		//Init
		this.rightPart = new HTMLPanel("");
		this.editMemberCtnr = new VerticalPanel();
		
		this.memberPropTable = new HorizontalPanel();
		this.memberSelectedField = new Label(constants.member());
		this.memberSelectedValue = new Label(props.getUser().getLogin());
		
		this.rightFieldsCtnr = new FlexTable();
		this.memberHasAdminRight = new CheckBox(constants.adminRights());
		this.memberHasEditUserRight = new CheckBox(constants.editUserRights());

		this.rightToolbar = new HorizontalPanel();
		this.memberAddSchedule = new Button(constants.add());
		this.memberEditSchedule = new Button(constants.editTable());
		this.memberResetPassword = new Button(constants.resetPassword());
		memberResetPassword.setEnabled(props.getUser().hasRightEditUsers());
		this.memberChangePassword = new Button(constants.changePassword());
		this.memberWorkScheduleTable = new WorkScheduleTable(){
			@Override
			public void onLineDayEdition(WorkScheduleEdition object, WorkingDay dayEdited, int newValue, FieldUpdater<WorkScheduleEdition, ?> fieldUpdater, int index) {
				onWorkScheduleTableNbHoursEdition(object, dayEdited, newValue);
			}
			@Override
			public void onLineDateEdition(WorkScheduleEdition object, Date newValue, FieldUpdater<WorkScheduleEdition, ?> fieldUpdater, int index) {
				onWorkScheduleTableDateEdition(object, newValue);
			}
			@Override
			public void onDeleteLine(WorkScheduleEdition object, FieldUpdater<WorkScheduleEdition, String> fieldUpdater, int index) {
				onWorkScheduleDelete(object);
			}
		};
		

		//Build
		mainSplit.add(rightPart);
		rightPart.add(editMemberCtnr);
		
		editMemberCtnr.add(memberPropTable);
		memberPropTable.add(memberSelectedField);
		memberPropTable.add(memberSelectedValue);
		
		editMemberCtnr.add(rightFieldsCtnr);
		rightFieldsCtnr.setWidget(0, 0, memberHasAdminRight);
		rightFieldsCtnr.setWidget(1, 0, memberHasEditUserRight);

		editMemberCtnr.add(rightToolbar);
		rightToolbar.add(memberEditSchedule);
		rightToolbar.add(memberAddSchedule);
		
		editMemberCtnr.add(memberWorkScheduleTable);

		
		//CSS
		rightPart.addStyleName("EUSP-rightPart");
		editMemberCtnr.addStyleName("EUSP-editMemberCtnr");
		memberPropTable.addStyleName("EUSP-memberPropTable");
		memberSelectedField.addStyleName("EUSP-memberSelectedField");
		memberSelectedValue.addStyleName("EUSP-memberSelectedValue");
		memberHasAdminRight.addStyleName("EUSP-memberHasAdminRight");
		memberHasEditUserRight.addStyleName("EUSP-memberHasEditUserRight");
		rightToolbar.addStyleName("EUSP-rightToolbar");
		memberEditSchedule.addStyleName("EUSP-memberEditSchedule");
		memberAddSchedule.addStyleName("EUSP-memberAddSchedule");
		memberChangePassword.addStyleName("EUSP-memberChangePassword");
		memberResetPassword.addStyleName("EUSP-memberResetPassword");
		memberWorkScheduleTable.addStyleName("EUSP-memberWorkScheduleTable");

		
		//Size & co
		memberWorkScheduleTable.setHeight("250px");


	}
	


	/**
	 * Add listneners
	 */
	private void addEvents() {
		addHandlerOnSelectListMember();
		addHandlerOnAddMember();
		addHandlerOnDeleteMember();
		addHandlerOnResetPassword();
		addHandlerOnChangePassword();
		addHandlerOnChangeAdminRightValue();
		addHandlerOnChangeEditUsersRightValue();
		addHandlerOnEditSchedules();
		addHandlerOnAddSchedule();
	}

	/**
	 * Add a schedule to the next possible day with project default values
	 */
	private void addHandlerOnAddSchedule() {
		memberAddSchedule.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Member memberSelected = getMemberSelected();
				if(schedulesInEdition && (memberSelected != null) ){
					Date currentTime = new Date();
					
					//Find the next free date
					while(memberSelected.hasWorkScheduleAt(currentTime)){
						CalendarUtil.addDaysToDate(currentTime, 1);
					}
					
					EditMembersRequester requester = new EditMembersRequester(EditMembersScreenPart.this);
					requester.doAddSchedule(memberSelected, currentTime);
				}
			}
		});
	}

	/**
	 * On edit schedule click
	 */
	private void addHandlerOnEditSchedules() {
		memberEditSchedule.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int indexSelected = memberWorkScheduleTable.getIndexLineSelected();
				schedulesInEdition = !schedulesInEdition;

				updateRightpart();
				memberWorkScheduleTable.setLineIndexSelected(indexSelected);
			}
		});
	}

	/**
	 * Handle admin right checkbox value change
	 */
	private void addHandlerOnChangeAdminRightValue() {
		memberHasAdminRight.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				Member member = getMemberSelected();
				if(props.getUser().hasRightAdmin()){
					EditMembersRequester requester = new EditMembersRequester(EditMembersScreenPart.this);
					requester.doChangeRightAdmin(member, event.getValue());
				}
			}
		});
	}
	/**
	 * Handle users edit right checkbox value change
	 */
	private void addHandlerOnChangeEditUsersRightValue() {
		memberHasEditUserRight.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				Member member = getMemberSelected();
				if(props.getUser().hasRightAdmin()){
					EditMembersRequester requester = new EditMembersRequester(EditMembersScreenPart.this);
					requester.doChangeRightEditUsers(member, event.getValue());
				}
			}
		});
	}

	/**
	 * On change password action
	 */
	private void addHandlerOnChangePassword() {
		memberChangePassword.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final Member member = getMemberSelected();
				if(member != null) {
					PopUpChangePassword popUpPwd = new PopUpChangePassword(EditMembersScreenPart.this, member);
					popUpPwd.center();
				}
			}
		});
	}

	/**
	 * On delete member action
	 */
	private void addHandlerOnDeleteMember() {
		deleteMemberButt.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(props.getUser().hasRightEditUsers()){
					if(props.getProject().getListMembers().size() > 1){
						final Member memberToDelete = getMemberSelected();
						if(memberToDelete != null){
							MessageFrame confirmFrame = new MessageFrame(constants.confirm());
							confirmFrame.setMessage(messages.confirmDelete(memberToDelete.getUserLinked().getLogin()), "");
							Button buttOk = new Button(constants.ok());
							Button buttCancel = new Button(constants.cancel());
							confirmFrame.addTwoButtons(buttOk, buttCancel, true);
							
							buttOk.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									EditMembersRequester requester = new EditMembersRequester(EditMembersScreenPart.this);
									requester.doDeleteMember(memberToDelete);
								}
							});
							confirmFrame.center();
						}
					}else{
						MessageFrame errorFrame = new MessageFrame(constants.error());
						errorFrame.setMessage(constants.projectMustHaveMembers(), "");
						errorFrame.addOneButton(new Button(constants.ok()));
						errorFrame.center();
					}
				}
			}
		});
	}
	
	/**
	 * on reset password action
	 */
	private void addHandlerOnResetPassword() {
		//Will send a new password to the member
		memberResetPassword.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final Member member = getMemberSelected();
				if(member != null) {
					MessageFrame popUpConfirm = new MessageFrame(constants.confirm());
					popUpConfirm.setMessage(messages.confirmReset(member.getUserLinked().getLogin()), "");
					Button okButt = new Button(constants.ok());
					Button cancelButt = new Button(constants.cancel());
					popUpConfirm.addTwoButtons(okButt, cancelButt, true);
					
					okButt.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							EditMembersRequester requester = new EditMembersRequester(EditMembersScreenPart.this);
							requester.doResetPassword(member);
						}
					});
					
					popUpConfirm.center();
				}
			}
		});
	}
	
	/**
	 * on add member action
	 */
	private void addHandlerOnAddMember() {
		
		// Try add member
		addMemberButt.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(props.getUser().hasRightEditUsers()){
					PopUpAddMember popup = new PopUpAddMember(EditMembersScreenPart.this);
					popup.center();
				}
			}
		});
	}

	/**
	 * on select member in list
	 */
	private void addHandlerOnSelectListMember() {
		//Select in list box
		selectMemberListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				schedulesInEdition = false;
				updateRightpart();
			}
		});
	}
	/**
	 * When user has edited a date
	 * @param object
	 */
	private void onWorkScheduleTableDateEdition(WorkScheduleEdition object, Date newValue) {
		Member member = getMemberSelected();
		if(schedulesInEdition && (member != null)){
			if( !member.hasWorkScheduleAt(newValue) ){
				Date oldValue = object.getStartDay();
				object.setStartDay(newValue);
				EditMembersRequester requester = new EditMembersRequester(EditMembersScreenPart.this);		
				requester.doChangeScheduleDate(member, oldValue, object);
			}else{
				responseChangeScheduleDate();
				
				MessageFrame errorFrame = new MessageFrame(constants.error());
				errorFrame.setMessage(constants.dateAlreadyExists(), "");
				Button okButt = new Button(constants.ok());
				errorFrame.addOneButton(okButt);
				
				errorFrame.center();
			}
		}
	}

	/**
	 * When user edit a day schedule value
	 * @param object
	 * @param dayEdited
	 * @param newValue
	 */
	private void onWorkScheduleTableNbHoursEdition(WorkScheduleEdition object, WorkingDay dayEdited, int newValue) {
		Member member = getMemberSelected();
		if(schedulesInEdition && (member != null)){
			int oldValue = dayEdited.getNbHoursWorked();
			dayEdited.setNbHoursWorked(newValue);
			
			EditMembersRequester requester = new EditMembersRequester(EditMembersScreenPart.this);		
			requester.doChangeScheduleNbHours(member, oldValue, object, dayEdited);
		}
	}
	
	/**
	 * When user want to delete a line
	 * @param object
	 * @param dayEdited
	 * @param newValue
	 */
	private void onWorkScheduleDelete(WorkScheduleEdition object) {
		final Member memberSelected = getMemberSelected();
		if(schedulesInEdition && (memberSelected != null) ){
			
			final WorkScheduleEdition scheduleToDelete = object;
			if(scheduleToDelete != null){
				MessageFrame confirmFrame = new MessageFrame(constants.confirm());
				
				String dateDeleted = AppConstants.VIEW_TIME_FORMAT.format(scheduleToDelete.getStartDay());
				confirmFrame.setMessage(messages.confirmDeleteSchedule(dateDeleted), "");
				
				Button buttOk = new Button(constants.ok());
				Button buttCancel = new Button(constants.cancel());
				
				buttOk.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						EditMembersRequester requester = new EditMembersRequester(EditMembersScreenPart.this);
						requester.doDeleteSchedule(memberSelected, scheduleToDelete);
					}
				});
			
				confirmFrame.addTwoButtons(buttOk, buttCancel, true);
				confirmFrame.center();
			}
		}
	}
	
	
	/**
	 * Refresh left part widgets
	 */
	private void updateLeftPart() {
		boolean canEditMembers = props.getUser().hasRightEditUsers();
		addMemberButt.setEnabled(canEditMembers);
		deleteMemberButt.setEnabled(canEditMembers);
	}
	/**
	 * Refresh right part fields
	 */
	private void updateRightpart() {
		Member selectedMember = getMemberSelected();
		if(selectedMember != null){
			memberSelectedValue.setText(selectedMember.getUserLinked().getLogin());	

			memberWorkScheduleTable.clearLines();
			memberWorkScheduleTable.refreshTableColumns();
			
			//checkboxes
			boolean canEditRights = props.getUser().hasRightAdmin();
			memberHasAdminRight.setEnabled(canEditRights);
			memberHasAdminRight.setValue(selectedMember.getUserLinked().hasRightAdmin());
			memberHasEditUserRight.setEnabled(canEditRights);
			memberHasEditUserRight.setValue(selectedMember.getUserLinked().hasRightEditUsers());
			
			refreshTableState();
			
			//Fill table content
			Iterator<WorkScheduleEdition> it = selectedMember.getWorkScheduleHistoryIterator();
			while(it.hasNext()){
				memberWorkScheduleTable.addLine( it.next() );
			}
			
			if(selectedMember.getWorkScheduleHistoryCount()> 0){
				int size = selectedMember.getWorkScheduleHistoryCount();
				memberWorkScheduleTable.setLineIndexSelected(size - 1);
			}
			
			//Change button reset/change pwd
			if(rightFieldsCtnr.getCellCount(0) == 3){
				rightFieldsCtnr.removeCell(0, 2);
			}
			if(selectedMember.getUserLinked().equals(props.getUser())){
				rightFieldsCtnr.setWidget(0, 2, memberChangePassword);
			}else{
				if(props.getUser().hasRightAdmin()){
					rightFieldsCtnr.setWidget(0, 2, memberResetPassword);
				}
			}
		}
	}
	
	/**
	 * Refresh table widgets states
	 */
	private void refreshTableState() {
		//Can edit the table if current user have right or selected himself
		Member memberSelected = getMemberSelected();
		boolean memberSelectedIsCurrentUser=  ( (memberSelected != null) && (memberSelected.getUserLinked().equals(props.getUser())) );
		boolean tableEditable = ( schedulesInEdition && (props.getUser().hasRightEditUsers() || memberSelectedIsCurrentUser) );
		
		
		RootPanel.get().add(new Label("tableEditable : "+Boolean.toString(tableEditable)));
		
		memberWorkScheduleTable.setSupportEdition(tableEditable);
		memberWorkScheduleTable.setEnableDeleteLine(tableEditable);
		memberWorkScheduleTable.refreshTableColumns();
		

		//buttons schedules
		memberEditSchedule.setEnabled(!schedulesInEdition);
		memberAddSchedule.setEnabled(tableEditable);
	}

	/**
	 * @return the current selected member
	 */
	private Member getMemberSelected() {
		String codeMember = selectMemberListBox.getItemText(selectMemberListBox.getSelectedIndex());
		int index = props.getProject().getListMembers().indexOf(new Member(new User(props.getProject(), codeMember)));
		if(index != -1){
			Member memberSelected = props.getProject().getListMembers().get(index);
			return memberSelected;
		}
		return null;
	}
	//---------------------------------------- GETTER SETTER---------------------------------------------
	/**
	 * @return the props
	 */
	public Properties getProps() {
		return props;
	} 
  
	//---------------------------------------- OVERRIDE ---------------------------------------------------


	@Override
	public void onShow() {
		if(!initialized){
			int currentUserIndex = -1;
			for(int i = 0; i < props.getProject().getListMembers().size(); i++){
				Member member = props.getProject().getListMembers().get(i);
				selectMemberListBox.addItem(member.getUserLinked().getLogin());
				
				if( member.getUserLinked().equals(props.getUser()) ){
					currentUserIndex = i;
				}
			}
			
			//Selection of current user if possible
			if(currentUserIndex != -1){
				selectMemberListBox.setItemSelected(currentUserIndex, true);
				updateRightpart();
			}
			initialized = true;
		}
	}

	/**
	 * Server response for "add member"
	 * @param newMember
	 */
	public void responseAddMemberSucceed(Member newMember) {
		//Notify user action succeed
		MessageFrame responseOkayFrame = new MessageFrame(constants.memberAddSucceed());
		responseOkayFrame.setMessage(constants.memberAddSucceedMsg(), "");
		responseOkayFrame.addOneButton(new Button(constants.ok()));
		responseOkayFrame.center();
		
		//add in model view
		props.getProject().getListMembers().add(newMember);
		selectMemberListBox.addItem(newMember.getUserLinked().getLogin());
		selectMemberListBox.setItemSelected(selectMemberListBox.getItemCount() - 1, true);
		updateRightpart();
		
		predictionScreen.onDataChange();
	}

	/**
	 * Server response for rest member password
	 */
	public void responseResetMemberPasswordSucceed() {
		//Notify user action succeed
		MessageFrame responseOkayFrame = new MessageFrame(constants.passwordReseted());
		responseOkayFrame.setMessage(constants.resetPasswordMailSent(), "");
		responseOkayFrame.addOneButton(new Button(constants.ok()));
		responseOkayFrame.center();
		
	}
	/**
	 * Server response for change pwd
	 */
	public void responseChangeMemberPasswordSucceed() {
		//get back to home page
		Window.Location.reload();
	}

	/**
	 * Delete the member in model and view
	 */
	public void responseDeleteMemberSucceed(Member memberToDelete) {
		//Notify user action succeed
		MessageFrame responseOkayFrame = new MessageFrame(constants.memberDeleteSucceed());
		responseOkayFrame.setMessage(constants.memberDeleteSucceedMsg(), "");
		responseOkayFrame.addOneButton(new Button(constants.ok()));
		responseOkayFrame.center();
		
		//add in model view
		props.getProject().getListMembers().remove(memberToDelete);
		int index = selectMemberListBox.getSelectedIndex();
		selectMemberListBox.removeItem(index);
		
		selectMemberListBox.setItemSelected(Math.max( Math.min(index, selectMemberListBox.getItemCount() - 1), 0), true);
		updateRightpart();
		
		predictionScreen.onDataChange();
	}

	/**
	 * Response right edition
	 * @param memberEdited
	 */
	public void responseRightChange(Member memberEdited) {
		updateRightpart();
		updateLeftPart();
	}
	
	/**
	 * Response add work schedule to selected member
	 */
	public void responseAddMemberScheduleSucceed() {
		updateRightpart();
		predictionScreen.onDataChange();
	}

	/**
	 * Response delete schedule
	 * @param scheduleToDelete
	 */
	public void responseDeleteMemberScheduleSucceed( WorkScheduleEdition scheduleToDelete) {
		int index = memberWorkScheduleTable.getIndexLineSelected();
		memberWorkScheduleTable.removeLine(scheduleToDelete);
		
		int newIndex = Math.max(0, Math.min(index, memberWorkScheduleTable.getLinesCount() - 1) );
		memberWorkScheduleTable.setLineIndexSelected( newIndex );
		
		predictionScreen.onDataChange();
	}

	/**
	 * On schedule date edition response
	 */
	public void responseChangeScheduleDate() {
		WorkScheduleEdition line = memberWorkScheduleTable.getLineSelected();
		updateRightpart();
		memberWorkScheduleTable.setLineSelected(line);
		predictionScreen.onDataChange();
	}

	/**
	 * On schedule nb hours change repseonse
	 * @param succeed
	 */
	public void responseChangeScheduleNbHours(boolean succeed) {
		if(!succeed){
			updateRightpart();
		}
		predictionScreen.onDataChange();
	}

}
