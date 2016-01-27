package com.client.screens.predictionscreen.editmemberspart.translation;

import com.google.gwt.i18n.client.Constants;

public interface EditMembersTranslationConstants extends Constants {

	@DefaultStringValue("Add member...")
	String addMemberPopUpTitle();
	@DefaultStringValue("Name")
	String name();
	@DefaultStringValue("email")
	String email();
	@DefaultStringValue("OK")
	String ok();
	@DefaultStringValue("Cancel")
	String cancel();
	@DefaultStringValue("Invalid email address")
	String invalidFormat();
	@DefaultStringValue("Join on")
	String startDay();
	@DefaultStringValue("Add")
	String add();
	@DefaultStringValue("Delete")
	String delete();
	@DefaultStringValue("Edit")
	String editTable();
	@DefaultStringValue("Block")
	String blockTable();
	@DefaultStringValue("Member : ")
	String member();
	@DefaultStringValue("Administrator rights")
	String adminRights();
	@DefaultStringValue("Edit members rights")
	String editUserRights();
	@DefaultStringValue("Reset password")
	String resetPassword();
	@DefaultStringValue("Change password")
	String changePassword();
	@DefaultStringValue("Member succesfully added")
	String memberAddSucceed();
	@DefaultStringValue("A mail has been sent to the new member.")
	String memberAddSucceedMsg();
	@DefaultStringValue("Confirmation")
	String confirm();
	@DefaultStringValue("Password reseted")
	String passwordReseted();
	@DefaultStringValue("A mail with new password has been sent to the member.")
	String resetPasswordMailSent();
	@DefaultStringValue("Change password...")
	String popUpChangePasswordTitle();
	@DefaultStringValue("Current password")
	String oldPwd();
	@DefaultStringValue("New password")
	String newPwd();
	@DefaultStringValue("Confirm password")
	String newPwdConfirm();
	@DefaultStringValue("Password and confirmation mismatch")
	String pwdMismatch();
	@DefaultStringValue("Member succesfully deleted")
	String memberDeleteSucceed();
	@DefaultStringValue("A pink slip has been sent to the dude.")
	String memberDeleteSucceedMsg();
	@DefaultStringValue("Error")
	String error();
	@DefaultStringValue("Project must have at least one member.")
	String projectMustHaveMembers();
	@DefaultStringValue("A work schedule already exists on this day, please edit this one.")
	String dateAlreadyExists();

	
	



	

}
