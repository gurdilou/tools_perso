package com.client.screens.predictionscreen.predictchartpart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.client.model.Member;
import com.client.model.Properties;
import com.client.screens.predictionscreen.PredictionScreen;
import com.client.screens.predictionscreen.predictchartpart.translation.PredictChartTranslationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.corechart.PieChartOptions;
import com.googlecode.gwt.charts.client.options.PieSliceText;

/**
 * The bottom part of prediction screen, containing the cart
 * @author gurdi
 *
 */
public class PredictChartScreenPart extends HTMLPanel{


	//---------------------------------------- CONSTANTS ------------------------------------------------
	//translation
	private static PredictChartTranslationConstants constants = GWT.create(PredictChartTranslationConstants.class);

	//---------------------------------------- VARIABLES ------------------------------------------------
	//variables
	private boolean apiReady	= false;
	private boolean dataReady	= false;

	//Elements
	private PieChart chart					= null;
	private DockLayoutPanel layoutPanel;
	private PieChartOptions options;
	private DataTable dataTable;
	private PredictionScreen predictionScreen;
	private Properties props;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	public PredictChartScreenPart(PredictionScreen predictionScreen) {
		super("");
		
		this.props = predictionScreen.getApp().getProps();
		this.predictionScreen = predictionScreen;

		this.setSize("100%", "100%");
		this.getElement().getStyle().setBackgroundColor("#BBB");
		
		layoutPanel = new DockLayoutPanel(Unit.PX);
		add(layoutPanel);
		layoutPanel.setSize("100%", "100%");
		
		initChartOptions();
		initializeChart();
	}



	//---------------------------------------- PRIVATE --------------------------------------------------
	/**
	 * Load API, and fire chart drawing
	 */
	private void initializeChart() {
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {

			@Override
			public void run() {
				apiReady = true;
				tryDrawChart();
			}
		});
	}
	/**
	 * Set pie chart options
	 */
	private void initChartOptions() {
		// Set options
		this.options = PieChartOptions.create();
		options.setBackgroundColor("#f0f0f0");

		// options.setColors(colors);
		options.setFontName("Tahoma");
		options.setPieResidueSliceColor("#000000");
		options.setPieResidueSliceLabel("Others");
		options.setTitle(constants.profitsRepartition());
		options.setPieSliceText(PieSliceText.LABEL);
	}
	/**
	 * Create or return existing widget
	 * @return
	 */
	private Widget getPieChart() {
		if (chart == null) {
			chart = new PieChart();
		}
		return chart;
	}

	/**
	 * Push data ini graphic
	 */
	private void fillDatasTable() {
		this.dataTable = DataTable.create();
		dataTable.addColumn(ColumnType.STRING, constants.name());
		dataTable.addColumn(ColumnType.NUMBER, constants.nbHoursInProject());
		dataTable.addRows(props.getProject().getListMembers().size());

		HashMap<String, Integer> mappingMemberHours = new HashMap<String, Integer>();
		int totalHoursWorked = 0; 
		for(int i = 0; i < props.getProject().getListMembers().size(); i++){
			Member memberParsed = props.getProject().getListMembers().get(i);
			
			String login = memberParsed.getUserLinked().getLogin();
			
			
			int nbMemberHours = memberParsed.getTotalWorkedHours();
			
			mappingMemberHours.put(login, nbMemberHours);
			totalHoursWorked += nbMemberHours;
		}
		
		float income = props.getProject().getEstimatedIncome();
		Iterator<Entry<String, Integer>> it = mappingMemberHours.entrySet().iterator();
		int index = 0;
		while(it.hasNext()){
			Entry<String, Integer> entry = it.next();
			
			float ratio = ((float)entry.getValue()) / ((float)totalHoursWorked);
			float memberProfit = ratio * income;
			String currency = "";
			if(!props.getProject().getCurrencySymbol().isEmpty()){
				currency = " "+props.getProject().getCurrencySymbol();
			}
			String profitFormatted = NumberFormat.getFormat("0.##").format(memberProfit);
			String name = entry.getKey() +" ("+profitFormatted+currency+")";
			
			dataTable.setValue(index, 0, name);
			dataTable.setValue(index, 1, entry.getValue());
			
			index++;
		}
	}



	/**
	 * Refresh chart draw
	 */
	private void redrawChart() {
		int height = Window.getClientHeight() - predictionScreen.getPanelsHeight();
		options.setHeight(height);
		chart.redraw();
	}

	/**
	 * If all prerequisite are ok, process and draw the chart
	 * @author Thomas Luce
	 */
	private void tryDrawChart() {
		if(apiReady && dataReady){
			fillDatasTable();

			if(!getPieChart().isAttached()){
				layoutPanel.clear();
				layoutPanel.add(getPieChart());
			}
			chart.draw(dataTable, options);
		}
	}

	//---------------------------------------- GETTER SETTER---------------------------------------------



	//---------------------------------------- PUBLIC ---------------------------------------------------
	/**
	 * Re draw the chart in order to fit page
	 */
	public void refreshChart() {
		if(chart != null){
			// Wait for disclosure panel animation end
			Timer timer = new Timer() {
				@Override
				public void run() {
					redrawChart();
				}
			};

			timer.schedule(400); //DiscolsurPanel.ANIMATION_DURATION + small delta
		}	
	}
	
	/**
	 * When datas are arrive
	 * @author Thomas Luce
	 */
	public void onDataReceived(){
		dataReady = true;
		tryDrawChart();
	}


}
