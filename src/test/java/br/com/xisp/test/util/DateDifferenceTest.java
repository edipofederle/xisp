package br.com.xisp.test.util;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import br.com.xisp.datemath.DateMath;
import br.com.xisp.datemath.InvalidMaskException;
import br.com.xisp.utils.DateDifference;

public class DateDifferenceTest {
	
	
	
	@Test
	public void shouldRetiurn3Days() throws InvalidMaskException{
		Date start  = new DateMath().on("29/10/2011 10:30", "dd/MM/yyyy HH:mm").result();
		Date end = new DateMath().on("01/11/2011 10:30", "dd/MM/yyy HH:mm").result();
		Assert.assertEquals(3, DateDifference.diferenceInDays(start, end));
	}
	
	@Test
	public void shouldRetiurn4Days() throws InvalidMaskException{
		Date start  = new DateMath().on("29/10/2011 10:30", "dd/MM/yyyy HH:mm").result();
		Date end = new DateMath().on("02/11/2011 10:30", "dd/MM/yyy HH:mm").result();
		Assert.assertEquals(4, DateDifference.diferenceInDays(start, end));
	}
	
	@Test
	public void shouldRetiurn10Days() throws InvalidMaskException{
		Date start  = new DateMath().on("29/10/2011 10:30", "dd/MM/yyyy HH:mm").result();
		Date end = new DateMath().on("08/11/2011 10:30", "dd/MM/yyy HH:mm").result();
		Assert.assertEquals(10, DateDifference.diferenceInDays(start, end));
	}
	
}
