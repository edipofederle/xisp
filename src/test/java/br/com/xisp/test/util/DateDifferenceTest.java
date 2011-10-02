package br.com.xisp.test.util;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import br.com.xisp.utils.DateDifference;

public class DateDifferenceTest {
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void testOne(){
		Date start = new Date();
		Date end = new Date();
	    end.setDate(end.getDate() + 10);  
		Assert.assertEquals(10, DateDifference.calculateDifference(end, start));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testOne2(){
		Date start = new Date();
		start.setDate(start.getDate() + 5);
		Date end = new Date();
	    end.setDate(end.getDate() + 11);  
		System.out.println(start.getDay() + "/" + start.getMonth());
		System.out.println(end.getDay() + "/" + end.getMonth());
		Assert.assertEquals(6, DateDifference.calculateDifference(end, start));
	}
	
}
