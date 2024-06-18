package com.ylog.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtility {
	
	public static String getGmtDateTime() {
		
		Date date = new Date();
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		String strDate = formatter.format(date);  
		return strDate;
		
	}

}
