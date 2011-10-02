package br.com.xisp.test.util;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import br.com.xisp.utils.DateDifference;

public class DateDifferenceTest {
	
	
	@Test
	public void testOne3(){
		Date start = new Date();
		Date end = new Date();
		Assert.assertEquals(0, DateDifference.calculateDifference(end, start));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testOne2(){
		Date start = new Date();
		Date end = new Date();
		end.setDate(end.getDate()+1);
		Assert.assertEquals(1, DateDifference.calculateDifference(end, start));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testOne4(){
		Date start = new Date();
		start.setDate(start.getDate() +2);
		Date end = new Date();
		end.setDate(end.getDate()+10);
		Assert.assertEquals(8, DateDifference.calculateDifference(end, start));
	}
	
}
