package com.client.requests;

import com.client.model.ServerUtils;
import com.client.widgets.MessageFrame;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.ui.Button;

public class ShareLootorRequestBuilder extends RequestBuilder{
	//---------------------------------------- VARIABLES ------------------------------------------------
	
	  
	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	/**
	 * Override to control in out if needed
	 * @param actionName
	 */
	public ShareLootorRequestBuilder(String actionName) {
		super(POST, ServerUtils.SERVER_BASE+actionName);
		setHeader("Content-type", "application/x-www-form-urlencoded");
	}	
  
	//---------------------------------------- PRIVATE --------------------------------------------------
	
  
	//---------------------------------------- PUBLIC ---------------------------------------------------
	@Override
	public Request sendRequest(String requestData, RequestCallback callback) {
		try{
			return super.sendRequest(requestData, callback);
		}catch(RequestException e){
			MessageFrame errorFrame = new MessageFrame("Client error");
			errorFrame.setMessage(e.getMessage(), "errorLabel");
			errorFrame.addOneButton(new Button("OK"));
			errorFrame.center();
			return null;
		}
	}
  

	
}