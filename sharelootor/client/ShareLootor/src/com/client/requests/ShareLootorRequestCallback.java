package com.client.requests;

import com.client.model.Properties;
import com.client.model.ServerUtils;
import com.client.requests.errconstants.RequestsErrorTranslator;
import com.client.widgets.MessageFrame;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.ui.Button;

/**
 * Utility to generalize requests handle
 * @author gurdi
 *
 */
public abstract class ShareLootorRequestCallback implements RequestCallback{

	//---------------------------------------- CONSTANTS ------------------------------------------------

	
	//---------------------------------------- VARIABLES ------------------------------------------------
	
	//variables
	private Properties properties;

	//---------------------------------------- CONSTRUCTOR ----------------------------------------------
	public ShareLootorRequestCallback(Properties properties) {
		this.properties = properties;
	}
  
	//---------------------------------------- PRIVATE --------------------------------------------------

	/**
	 * Display an server error
	 * @param response 
	 */
	protected void showErrorServer(Response response) {
		//Other thing returned
		MessageFrame errorFrame = new MessageFrame("Server error");
		errorFrame.setMessage(response.getText(), "errorLabel");
		errorFrame.addOneButton(new Button("OK"));
		errorFrame.center();
	}

	/**
	 * Misformed server response
	 * @param obj
	 */
	protected void showErrorMisformedResponse(JSONObject obj) {
		//must contain key "succeed"
		MessageFrame errorFrame = new MessageFrame("Server error");
		errorFrame.setMessage("Misformed response : <br/>"+obj.toString(), "errorLabel");
		errorFrame.addOneButton(new Button("OK"));
		errorFrame.center();	
	}

	/**
	 * After having received an error in the response
	 * @param obj
	 */
	protected void showErrorResponseMsg(JSONObject obj) {
		//server raised an error
		MessageFrame errorFrame = new MessageFrame("Server error");
		String message = "error";
		if(obj.containsKey(ServerUtils.KEY_MSG)){
			if(obj.get(ServerUtils.KEY_MSG).isString() != null){
				message = obj.get(ServerUtils.KEY_MSG).isString().stringValue();
			}
			if(obj.get(ServerUtils.KEY_MSG).isObject() != null){
				message = obj.get(ServerUtils.KEY_MSG).isObject().toString();
			}
			if(obj.get(ServerUtils.KEY_MSG).isNumber()!= null){
				message = obj.get(ServerUtils.KEY_MSG).isNumber()+"";
			}
		}
		if(obj.containsKey(ServerUtils.KEY_ERROR_CODE) && (obj.get(ServerUtils.KEY_ERROR_CODE).isString()!= null) ){
			message = RequestsErrorTranslator.translateErrorCode(obj.get(ServerUtils.KEY_ERROR_CODE).isString().stringValue(), message);
		}
		errorFrame.setMessage(message, "errorLabel");
		errorFrame.addOneButton(new Button("OK"));
		errorFrame.center();
	}

	//---------------------------------------- GETTER SETTER---------------------------------------------
	/**
	 * @return the app
	 */
	public Properties getProps() {
		return properties;
	}
  
	//---------------------------------------- PUBLIC ---------------------------------------------------
	@Override
	public void onError(Request request, Throwable exception) {
		MessageFrame errorFrame = new MessageFrame("Server error");
		errorFrame.setMessage(exception.getMessage(), "errorLabel");
		errorFrame.addOneButton(new Button("OK"));
		errorFrame.center();
	}

	/**
	 * {@inheritDoc}
	 * check json validity in app context
	 */
	@Override
	public void onResponseReceived(Request request, Response response) {
		try{
			JSONObject obj = JSONParser.parseStrict(response.getText()).isObject();
			if(obj != null){
				if(obj.containsKey(ServerUtils.KEY_SUCCEED)){
					if(obj.get(ServerUtils.KEY_SUCCEED).isBoolean().booleanValue()){
						onResponseReceived(true, obj, request, response);
					}else{
						showErrorResponseMsg(obj);
						onResponseReceived(false, obj, request, response);
					}
				}else{
					showErrorMisformedResponse(obj);
					onResponseReceived(false, obj, request, response);
				}
			}else{
				showErrorServer(response);
				onResponseReceived(false, null, request, response);
			}
		}catch(JSONException e){
			showErrorServer(response);
			onResponseReceived(false, null, request, response);
		}
	}
	
	//---------------------------------------- ABSTRACT ---------------------------------------------------
	/**
	 * When we received a valid repsonse
	 * @param validJsonResponse
	 * @param request
	 * @param response
	 */
	protected abstract void onResponseReceived(boolean succeed,JSONObject jsonResponse, Request request, Response response);		

}
