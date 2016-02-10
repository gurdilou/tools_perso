package com.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MessageFrame extends DialogBox{

	//---------------------------------------- VARIABLES ------------------------------------------------
	
	private VerticalPanel mainPanel;
	private HorizontalPanel footerPanel;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * 
	 * @param title
	 */
	public MessageFrame(String title){
		setText(title);

		setAnimationEnabled(true);
		setGlassEnabled(true);

		this.mainPanel = new VerticalPanel();
		this.footerPanel = new HorizontalPanel();
		
		//build
		setWidget(mainPanel);
		mainPanel.add(footerPanel);
		
		
		//css
		mainPanel.addStyleName("dialogBox-mainPanel");
		footerPanel.addStyleName("dialogBox-footerPanel");
		
		//pos
		mainPanel.setCellHorizontalAlignment(footerPanel, HasHorizontalAlignment.ALIGN_RIGHT);
	}
	
	//---------------------------------------- PRIVATE ------------------------------------------------
	//---------------------------------------- PUBLIC -------------------------------------------------
	/**
	 * Add a button
	 * @param butt
	 */
	public void addOneButton(Button butt){
		footerPanel.add(butt);
		butt.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
	}
	/**
	 * Add two buttons
	 * @param buttLeft
	 * @param buttRight
	 */
	public void addTwoButtons(Button buttLeft, Button buttRight, final boolean closeOnClick){
		footerPanel.add(buttLeft);
		buttLeft.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(closeOnClick){
					hide();
				}
			}
		});
		footerPanel.add(buttRight);
		buttRight.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(closeOnClick){
					hide();
				}
			}
		});
	}
	/**
	 * Set popup content
	 */
	public void setContent(Widget contentWidget){
		if(mainPanel.getWidgetCount() > 1){
			mainPanel.remove(0);
		}
		mainPanel.insert(contentWidget, 0);
	}
	/**
	 * Set message as popup content
	 */
	public void setMessage(String msg, String css){
		HTMLPanel contentPanel = new HTMLPanel("");
		contentPanel.addStyleName("dialogBox-msgPanel");
		
		HTML msgLabel = new HTML(msg);
		if(!css.isEmpty()){
			msgLabel.addStyleName(css);
		}
		contentPanel.add(msgLabel);
		setContent(contentPanel);
	}
}
