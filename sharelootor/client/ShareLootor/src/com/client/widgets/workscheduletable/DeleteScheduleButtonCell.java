package com.client.widgets.workscheduletable;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class DeleteScheduleButtonCell extends ButtonCell {

	  @Override
	  public void render(Context context, SafeHtml data, SafeHtmlBuilder sb) {
	    sb.appendHtmlConstant("<button type=\"button\" tabindex=\"-1\">");
	    if (data != null) {
	      sb.append(SafeHtmlUtils.fromTrustedString("&#10060;"));
	    }
	    sb.appendHtmlConstant("</button>");
	  }

}
