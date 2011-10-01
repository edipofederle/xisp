package br.com.xisp.test.models;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

import br.com.xisp.models.Interation;

public class InterationTest {
	
	@Test
	public void interationShouldBeCurrentCreatedYesterday(){
		Interation interation = new Interation();
		interation.setName("Current Interation");
		interation.setStartDate(new LocalDate().minusDays(1));
		interation.setEndDate(new LocalDate().plusDays(10));
		Assert.assertTrue(interation.isCurrent());
	}
	
	@Test
	public void interationShouldBeCurrentCreatedNow(){
		Interation interation = new Interation();
		interation.setName("Current Interation");
		interation.setStartDate(new LocalDate());
		interation.setEndDate(new LocalDate().plusDays(10));
		Assert.assertTrue(interation.isCurrent());
	}
	
	@Test
	public void interationShouldBeNotCurrentCreatedStartTommorow(){
		Interation interation = new Interation();
		interation.setName("Current Interation");
		interation.setStartDate(new LocalDate().plusDays(1));
		interation.setEndDate(new LocalDate().plusDays(11));
		Assert.assertFalse(interation.isCurrent());
	}
	
	@Test
	public void interationStartedTodayAndFinishToday(){
		Interation interation = new Interation();
		interation.setName("Current Interation");
		interation.setStartDate(new LocalDate());
		interation.setEndDate(new LocalDate());
		Assert.assertTrue(interation.isCurrent());
	}

}
