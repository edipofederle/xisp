package br.com.xisp.utils;

import java.util.Date;

public  class DateDifference {
	
	public static long diferenceInDays(Date start, Date end) {
		long dt = (end.getTime() - start.getTime()) + 3600000;   
        return (dt / 86400000L);
	}

}
