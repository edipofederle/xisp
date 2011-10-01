package br.com.xisp.test.models;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

import br.com.xisp.models.Interation;

public class InterationTest {
	
	@Test
	public void interationShouldBeCurrentCreatedYesterday(){
		Interation interation = new Interation();
		interation.setName("Current Interation");
		
		Date minhaData = new Date();  
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(minhaData);
		// incrementa minha data mais sete dias  
		calendar.add(Calendar.DAY_OF_MONTH, -1); 
		
		interation.setStartDate(calendar.getTime());
		interation.setEndDate(new Date());
		Assert.assertTrue(interation.isCurrent());
	}
	
	
	@Test
	public void interationShouldBeCurrentCreatedNow(){
		Interation interation = new Interation();
		interation.setName("Current Interation");
		interation.setStartDate(new Date());
		Date minhaData = new Date();  
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(minhaData);
		// incrementa minha data mais sete dias  
		calendar.add(Calendar.DAY_OF_MONTH, 2); 
		
		interation.setEndDate(calendar.getTime());
		Assert.assertTrue(interation.isCurrent());
	}
	
	@Test
	public void interationShouldBeNotCurrentCreatedStartTommorow(){
		Interation interation = new Interation();
		interation.setName("Current Interation");
		Date minhaData = new Date();  
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(minhaData);
		// incrementa minha data mais sete dias  
		calendar.add(Calendar.DAY_OF_MONTH, 1); 
		
		Date end = new Date();  
		Calendar calendarEnd = Calendar.getInstance();  
		calendar.setTime(end);
		// incrementa minha data mais sete dias  
		calendar.add(Calendar.DAY_OF_MONTH, 1); 
		
		interation.setStartDate(calendar.getTime());
		interation.setEndDate(calendarEnd.getTime());
		Assert.assertFalse(interation.isCurrent());
	}
	@Test
	public void interationStartedTodayAndFinishToday(){
		Interation interation = new Interation();
		interation.setName("Current Interation");
		interation.setStartDate(new Date());
		interation.setEndDate(new Date());
		Assert.assertTrue(interation.isCurrent());
	}

}
