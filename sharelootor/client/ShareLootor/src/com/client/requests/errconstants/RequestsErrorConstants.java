package com.client.requests.errconstants;

import com.google.gwt.i18n.client.Constants;

public interface RequestsErrorConstants extends Constants {

	@DefaultStringValue("Project already exists.")
	String errProjectAlreadyExists();
	@DefaultStringValue("Project does not exist.")
	String errProjectDoesntExist();
	@DefaultStringValue("Wrong user/password.")
	String errWrongLoginPassword();

	

}
