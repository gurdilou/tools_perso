package com.client.widgets.workscheduletable;

import java.util.Date;

import com.client.constants.AppConstants;
import com.client.model.WorkScheduleEdition;
import com.client.model.WorkingDay;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;

public class WorkScheduleTable extends DataGrid<WorkScheduleEdition>{
	//---------------------------------------- CONSTANTS ------------------------------------------------
	private static WorkScheduleTableTranslationConstants constants = GWT.create(WorkScheduleTableTranslationConstants.class);

	private static final double DELETE_WIDTH = 50;
	private static final double UPDATE_WIDTH = 150;
	private static final double DAY_WIDTH = 100;
	//---------------------------------------- VARIABLES ------------------------------------------------
	//Variables
	private boolean supportEdition = false;
	private boolean enableDeleteLine = false;
	private ListDataProvider<WorkScheduleEdition> model;
	private SingleSelectionModel<WorkScheduleEdition> selectionModel;


	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * Constructor
	 * @param supportEdition
	 * @param enableDateEdition
	 */
	public WorkScheduleTable(){
		super();
		
		setWidth("1024px");
		setAutoHeaderRefreshDisabled(false);
		setEmptyTableWidget( new Label(constants.noData()) );

		//MM need a pager ?
		//TODO
		//	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		//	    pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		//	    pager.setDisplay(dataGrid);

		//	    this.selectionModel = new SingleSelectionModel<WorkScheduleEdition>();

		refreshTableColumns();

		//To manipulate lines
		this.model = new ListDataProvider<WorkScheduleEdition>();
		model.addDataDisplay(this);

		//To handle selection
		selectionModel = new SingleSelectionModel<WorkScheduleEdition>();
		setSelectionModel(selectionModel);

		addStyleName("SL-WorkScheduleTable");
	}

	//---------------------------------------- PRIVATE --------------------------------------------------
	/**
	 * Init table columns
	 */
	public void refreshTableColumns() {
		//netooyage
		while(getColumnCount() > 0){
			removeColumn(0);
		}
		
		if(enableDeleteLine){
			Column<WorkScheduleEdition, String> deleteCol = new Column<WorkScheduleEdition, String>(new DeleteScheduleButtonCell()) {

				@Override
				public String getValue(WorkScheduleEdition object) {
					return "&#10060;";
				}
	        };

	        deleteCol.setFieldUpdater(new FieldUpdater<WorkScheduleEdition, String>() {
				@Override
				public void update(int index, WorkScheduleEdition object, String value) {
					onDeleteLine(object, this, index);
				}
	        });
			addColumn(deleteCol, "");
			setColumnWidth(deleteCol, DELETE_WIDTH, Unit.PX);	
		}
		
		
		//Workschedule creation time
		Column<WorkScheduleEdition, Date> startDateCol = new Column<WorkScheduleEdition, Date>(getTypeDateCell()) {

			@Override
			public Date getValue(WorkScheduleEdition object) {
				return object.getStartDay();
			}
		};
		startDateCol.setFieldUpdater(new FieldUpdater<WorkScheduleEdition, Date>() {
			@Override
			public void update(int index, WorkScheduleEdition object, Date value) {
				selectionModel.setSelected(object, true);
				if(!value.equals(object.getStartDay())){
					onLineDateEdition(object, value,this, index);
				}
			}
		});
		addColumn(startDateCol, constants.updateDay());
		setColumnWidth(startDateCol, UPDATE_WIDTH, Unit.PX);	

		//Monday schedule
		Column<WorkScheduleEdition, String> mondaySchedule = new Column<WorkScheduleEdition, String>( getTypeStringCell() ) {

			@Override
			public String getValue(WorkScheduleEdition object) {
				return object.getSchedule().getMonday().getNbHoursWorked()+"";
			}
		};
		mondaySchedule.setFieldUpdater(new FieldUpdater<WorkScheduleEdition, String>() {
			@Override
			public void update(int index, WorkScheduleEdition object, String value) {
				selectionModel.setSelected(object, true);
				int valueInt = 0;
				try{
					valueInt = Integer.parseInt(value);
				}catch(NumberFormatException e){
					valueInt = -1;
				}
				if(valueInt != object.getSchedule().getMonday().getNbHoursWorked()){
					onLineDayEdition(object, object.getSchedule().getMonday(), valueInt, this, index);
				}
			}
		});
		addColumn(mondaySchedule, constants.monday());
		setColumnWidth(mondaySchedule, DAY_WIDTH, Unit.PX);	

		//Tuesday schedule
		Column<WorkScheduleEdition, String> tuesdaySchedule = new Column<WorkScheduleEdition, String>( getTypeStringCell() ) {

			@Override
			public String getValue(WorkScheduleEdition object) {
				return object.getSchedule().getTuesday().getNbHoursWorked()+"";
			}
		};
		tuesdaySchedule.setFieldUpdater(new FieldUpdater<WorkScheduleEdition, String>() {
			@Override
			public void update(int index, WorkScheduleEdition object, String value) {
				selectionModel.setSelected(object, true);
				int valueInt = 0;
				try{
					valueInt = Integer.parseInt(value);
				}catch(NumberFormatException e){
					valueInt = -1;
				}
				if(valueInt != object.getSchedule().getTuesday().getNbHoursWorked()){
					onLineDayEdition(object, object.getSchedule().getTuesday(), valueInt, this, index);
				}
			}
		});
		addColumn(tuesdaySchedule, constants.tuesday());
		setColumnWidth(tuesdaySchedule, DAY_WIDTH, Unit.PX);

		//wednesday schedule
		Column<WorkScheduleEdition, String> wednesdaySchedule = new Column<WorkScheduleEdition, String>( getTypeStringCell() ) {

			@Override
			public String getValue(WorkScheduleEdition object) {
				return object.getSchedule().getWednesday().getNbHoursWorked()+"";
			}
		};
		wednesdaySchedule.setFieldUpdater(new FieldUpdater<WorkScheduleEdition, String>() {
			@Override
			public void update(int index, WorkScheduleEdition object, String value) {
				selectionModel.setSelected(object, true);
				int valueInt = 0;
				try{
					valueInt = Integer.parseInt(value);
				}catch(NumberFormatException e){
					valueInt = -1;
				}
				if(valueInt != object.getSchedule().getWednesday().getNbHoursWorked()){
					onLineDayEdition(object, object.getSchedule().getWednesday(), valueInt, this, index);
				}
			}
		});
		addColumn(wednesdaySchedule, constants.wednesday());
		setColumnWidth(wednesdaySchedule, DAY_WIDTH, Unit.PX);

		//Thursday schedule
		Column<WorkScheduleEdition, String> thursdaySchedule = new Column<WorkScheduleEdition, String>( getTypeStringCell() ) {

			@Override
			public String getValue(WorkScheduleEdition object) {
				return object.getSchedule().getThursday().getNbHoursWorked()+"";
			}
		};
		thursdaySchedule.setFieldUpdater(new FieldUpdater<WorkScheduleEdition, String>() {
			@Override
			public void update(int index, WorkScheduleEdition object, String value) {
				selectionModel.setSelected(object, true);
				int valueInt = 0;
				try{
					valueInt = Integer.parseInt(value);
				}catch(NumberFormatException e){
					valueInt = -1;
				}
				if(valueInt != object.getSchedule().getThursday().getNbHoursWorked()){
					onLineDayEdition(object, object.getSchedule().getThursday(), valueInt, this, index);
				}
			}
		});
		addColumn(thursdaySchedule, constants.thursday());
		setColumnWidth(thursdaySchedule, DAY_WIDTH, Unit.PX);	

		//friday schedule
		Column<WorkScheduleEdition, String> fridaySchedule = new Column<WorkScheduleEdition, String>( getTypeStringCell() ) {

			@Override
			public String getValue(WorkScheduleEdition object) {
				return object.getSchedule().getFriday().getNbHoursWorked()+"";
			}
		};
		fridaySchedule.setFieldUpdater(new FieldUpdater<WorkScheduleEdition, String>() {
			@Override
			public void update(int index, WorkScheduleEdition object, String value) {
				selectionModel.setSelected(object, true);
				int valueInt = 0;
				try{
					valueInt = Integer.parseInt(value);
				}catch(NumberFormatException e){
					valueInt = -1;
				}
				if(valueInt != object.getSchedule().getFriday().getNbHoursWorked()){
					onLineDayEdition(object, object.getSchedule().getFriday(), valueInt, this, index);
				}
			}
		});
		addColumn(fridaySchedule, constants.friday());
		setColumnWidth(fridaySchedule, DAY_WIDTH, Unit.PX);	


		//saturday schedule
		Column<WorkScheduleEdition, String> saturdaySchedule = new Column<WorkScheduleEdition, String>( getTypeStringCell() ) {

			@Override
			public String getValue(WorkScheduleEdition object) {
				return object.getSchedule().getSaturday().getNbHoursWorked()+"";
			}
		};
		saturdaySchedule.setFieldUpdater(new FieldUpdater<WorkScheduleEdition, String>() {
			@Override
			public void update(int index, WorkScheduleEdition object, String value) {
				selectionModel.setSelected(object, true);
				int valueInt = 0;
				try{
					valueInt = Integer.parseInt(value);
				}catch(NumberFormatException e){
					valueInt = -1;
				}
				if(valueInt != object.getSchedule().getSaturday().getNbHoursWorked()){
					onLineDayEdition(object, object.getSchedule().getSaturday(), valueInt, this, index);
				}
			}
		});
		addColumn(saturdaySchedule, constants.saturday());
		setColumnWidth(saturdaySchedule, DAY_WIDTH, Unit.PX);	


		//sunday schedule
		Column<WorkScheduleEdition, String> sundaySchedule = new Column<WorkScheduleEdition, String>( getTypeStringCell() ) {

			@Override
			public String getValue(WorkScheduleEdition object) {
				return object.getSchedule().getSunday().getNbHoursWorked()+"";
			}
		};
		sundaySchedule.setFieldUpdater(new FieldUpdater<WorkScheduleEdition, String>() {
			@Override
			public void update(int index, WorkScheduleEdition object, String value) {
				selectionModel.setSelected(object, true);
				int valueInt = 0;
				try{
					valueInt = Integer.parseInt(value);
				}catch(NumberFormatException e){
					valueInt = -1;
				}
				if(valueInt != object.getSchedule().getSunday().getNbHoursWorked()){
					onLineDayEdition(object, object.getSchedule().getSunday(), valueInt, this, index);
				}
			}
		});
		addColumn(sundaySchedule, constants.sunday());
		setColumnWidth(sundaySchedule, DAY_WIDTH, Unit.PX);	
	}


	/**
	 * Return the String type cell according to edition mode
	 * @return
	 */
	private Cell<String> getTypeStringCell() {
		Cell<String> result;
		if(supportEdition){
			result = new EditTextCell(){
				@Override
				public void onBrowserEvent(Cell.Context context, Element parent, String value, NativeEvent event, ValueUpdater<java.lang.String> valueUpdater){
					try{
						Integer.parseInt(value);
					}catch(NumberFormatException e){
						value = "0";
					}
					super.onBrowserEvent(context, parent, value, event, valueUpdater);
				}
			};
		}else{
			result = new TextCell();
		}
		return result;
	}

	/**
	 * Return the type date according to edition mode
	 * @return
	 */
	private Cell<Date> getTypeDateCell() {
		Cell<Date> result;
		DateTimeFormat fmt = AppConstants.VIEW_TIME_FORMAT;
		if(supportEdition){
			result = new DatePickerCell(fmt);
		}else{
			result = new DateCell(fmt);
		}
		return result;
	}
	/**
	 * @return the number of work schedule edition
	 */
	public int getLinesCount() {
		return model.getList().size();
	}

	//---------------------------------------- GETTER SETTER---------------------------------------------
	/**
	 * @return the supportEdition
	 */
	public boolean isSupportEdition() {
		return supportEdition;
	}

	/**
	 * @param supportEdition the supportEdition to set
	 */
	public void setSupportEdition(boolean supportEdition) {
		this.supportEdition = supportEdition;
		removeStyleName("editable");
		removeStyleName("notEditable");
		if(supportEdition){
			addStyleName("editable");
		}else{
			addStyleName("notEditable");
		}
	}
	/**
	 * @return the line selected index
	 */
	public int getIndexLineSelected() {
		int index = -1;
		WorkScheduleEdition line = getLineSelected();
		if(line != null){	
			index = model.getList().indexOf(line);
		}
		return index;
	}
	/**
	 * @return the enableDeleteLine
	 */
	public boolean isEnableDeleteLine() {
		return enableDeleteLine;
	}

	/**
	 * @param enableDeleteLine the enableDeleteLine to set
	 */
	public void setEnableDeleteLine(boolean enableDeleteLine) {
		this.enableDeleteLine = enableDeleteLine;
	}
	//---------------------------------------- PUBLIC ---------------------------------------------------
	/**
	 * Add a line in grid
	 * @param schedule
	 */
	public void addLine(WorkScheduleEdition schedule){
		model.getList().add(schedule);
		redraw();
	}
	/**
	 * remove a line in grid
	 * @param schedule
	 */
	public boolean removeLine(WorkScheduleEdition schedule){
		boolean result = model.getList().remove(schedule);
		redraw();
		return result;
	}
	/**
	 * Remove all lines
	 */
	public void clearLines() {
		model.getList().clear();
		redraw();
	}

	/**
	 * The line selected
	 * @return
	 */
	public WorkScheduleEdition getLineSelected(){
		return selectionModel.getSelectedObject();
	}
	
	/**
	 *  Select a line
	 */
	public void setLineSelected(WorkScheduleEdition schedule){
		selectionModel.setSelected(schedule, true);
		
		
		int index = model.getList().indexOf(schedule);
		if( (index != -1) && (index < getRowCount()) ){
			getRowElement(index).getCells().getItem(0).scrollIntoView();
		}
	}

	/**
	 * Select line at given index
	 * @param index
	 */
	public void setLineIndexSelected(int index) {
		if( (index >= 0) && (index < model.getList().size()) ){
			setLineSelected(model.getList().get(index));
		}
	}

	/**
	 * When something has been edited
	 * @param object
	 * @param newValue 
	 * @param dayEdited 
	 * @param fieldUpdater
	 * @param index
	 */
	public void onLineDayEdition(WorkScheduleEdition object, WorkingDay dayEdited, int newValue, FieldUpdater<WorkScheduleEdition, ?> fieldUpdater, int index) {
		/* to override if needed */
	}
	/**
	 * When a schedule date has been edited
	 * @param object 
	 * @param object
	 * @param fieldUpdater
	 * @param index
	 */
	public void onLineDateEdition(WorkScheduleEdition object, Date newValue, FieldUpdater<WorkScheduleEdition, ?> fieldUpdater, int index) {
		/* to override if needed */
	}
	/**
	 * When button delete clicked
	 * @param object
	 * @param fieldUpdater
	 * @param index
	 */
	public void onDeleteLine(WorkScheduleEdition object, FieldUpdater<WorkScheduleEdition, String> fieldUpdater, int index) {
		/* to override if needed */
	}



}
