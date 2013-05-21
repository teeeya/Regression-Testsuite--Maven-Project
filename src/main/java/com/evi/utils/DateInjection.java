package com.evi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
/*
 * This class returns the current timedate to inject into the config file
 */
public class DateInjection {
	Date dNow;
	SimpleDateFormat ft ;
	public DateInjection() {
	}
	public String returnInjectionDate() {
		dNow = new Date();
		ft= new SimpleDateFormat("MMM dd'th' yyyy',' HH:mm");
		ft.setTimeZone(TimeZone.getTimeZone("UTC"));
		return ft.format(dNow);
	}
}
