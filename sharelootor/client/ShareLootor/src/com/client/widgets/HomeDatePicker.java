package com.client.widgets;

import java.util.Date;

import com.client.constants.AppConstants;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

/**
 * A label link with a date picker
 * @author gurdi
 *
 */
public class HomeDatePicker extends Label {
	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------
	private Date value;
	private boolean isEnabled;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	public HomeDatePicker(){
		super();
		this.value = new Date();
		this.isEnabled = true;

		refreshLabelValue();

		getElement().getStyle().setCursor(Cursor.POINTER);
		
		//Add date edition
		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(isEnabled){
					final PopupPanel datePickCtn = new PopupPanel(true, true);
					datePickCtn.setAnimationEnabled(true);
					
					final DatePicker datePick = new DatePicker();
					datePick.setValue(value);
					datePick.setCurrentMonth(value);
					
					//events
					datePickCtn.addCloseHandler(new CloseHandler<PopupPanel>() {
						@Override
						public void onClose(CloseEvent<PopupPanel> event) {
							Date startDate = datePick.getValue();
							if(startDate != null){
								value = startDate;
								refreshLabelValue();
							}
						}
					});
					datePick.addValueChangeHandler(new ValueChangeHandler<Date>() {
						@Override
						public void onValueChange(ValueChangeEvent<Date> event) {
							Date startDate = datePick.getValue();
							if(startDate != null){
								value = startDate;
								refreshLabelValue();
							}
							datePickCtn.hide();
						}
					});
					
					//build
					datePickCtn.setWidget(datePick);
					datePickCtn.show();
					datePickCtn.getElement().getStyle().setLeft(getAbsoluteLeft(), Unit.PX);
					datePickCtn.getElement().getStyle().setTop(getAbsoluteTop()+getOffsetHeight()+3, Unit.PX);
				}
			}
		});
	}
  
	//---------------------------------------- PRIVATE --------------------------------------------------
	/**
	 * Refresh label string
	 */
	private void refreshLabelValue() {
		if(value != null){
			String todayStr = AppConstants.VIEW_TIME_FORMAT.format(value);
			setText(todayStr);
		}else{
			setText("");
		}
	}

	//---------------------------------------- GETTER SETTER---------------------------------------------
	/**
	 * @return the value
	 */
	public Date getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Date value) {
		this.value = value;

		refreshLabelValue();
	}

	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
		if(isEnabled){
			getElement().getStyle().setCursor(Cursor.POINTER);
		}else{
			getElement().getStyle().setCursor(Cursor.DEFAULT);
		}
	} 
  
	//---------------------------------------- PUBLIC ---------------------------------------------------
	


}
