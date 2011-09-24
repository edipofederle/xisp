package br.com.xisp.test.models;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.xisp.models.Status;
import br.com.xisp.models.Story;

public class StoryTest {
	
	private Story story;
	
	@Before
	public void setUp(){
		story = new Story();
	}
	
	@Test
	public void testShould(){
		story.setName("Build a Tower");
		story.setDescription("Figure out how build a tower");
		story.setStatus(Status.READY_FOR_DEV);
		Assert.assertEquals("RFD", story.getStatus().getStatus());
	}

}
