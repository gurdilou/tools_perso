package com.client.screens.predictionscreen.editmemberspart.translation;

import com.google.gwt.i18n.client.Messages;


public interface EditMembersTranslationMessages extends Messages{

	@DefaultMessage("Are you sure you want to reset ''{0}'' password ?")
	String confirmReset(String name);
	@DefaultMessage("Are you sure you want to lay off ''{0}''?")
	String confirmDelete(String name);
	@DefaultMessage("Are you sure you want to delete the schedule of the ''{0}'' ?")
	String confirmDeleteSchedule(String dateDeleted);

}
