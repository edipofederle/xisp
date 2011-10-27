package br.com.xisp.test.models;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.xisp.models.Project;
import br.com.xisp.models.Status;
import br.com.xisp.models.Story;
import br.com.xisp.models.TypeStory;
import br.com.xisp.models.User;

public class StoryTest {
	
	private Story story;
	
	@Before
	public void setUp(){
		story = new Story();
	}
	
	@Test
	public void testShouldReturnStatusRDD(){
		story.setName("Build a Tower");
		TypeStory t = givenAType();
		story.setTypeStory(t);
		story.setStatus(Status.IN_DEV);
		story.setDescription("Figure out how build a tower");
		Assert.assertEquals("Em Dev", story.getStatus().getStatus());
		Assert.assertEquals("Funcionalidade", story.getTypeStory().getType());
	}

	@Test
	public void testStoryOne(){
		Project project = givenAProject();
		Story story = givenAStory(project);
		Assert.assertEquals("Edipo", story.getCreatedBy().getName());
		Assert.assertEquals("Test Project", story.getProject().getName());
	}
	
	@Test
	public void testStoryChangeStatus(){
		Project project = givenAProject();
		Story story = givenAStory(project);
		Assert.assertEquals("Nao Iniciada",story.getStatus().getStatus());
		story.setStatus(Status.READY_FOR_TEST);
		Assert.assertEquals("Pronta para Testes",story.getStatus().getStatus());
	}
	
	@Test
	public void testStoryWhenInDevShouldFillStartAt(){
		Project project = givenAProject();
		Story story = givenAStory(project);
		Assert.assertEquals("Nao Iniciada",story.getStatus().getStatus());
		story.setStatus(Status.IN_DEV);
		Assert.assertEquals("Em Dev",story.getStatus().getStatus());
		Assert.assertEquals(new Date(), story.getStartedAt());
	}
	
	@Test
	public void testFinishStory(){
		Project project = givenAProject();
		Story story = givenAStory(project);
		story.markAsCompleted();
		Assert.assertEquals("Finalizada", story.getStatus().getStatus());
	}
	
	@Test
	public void testStoryShouldCreateAsNotStarted(){
		Project project = givenAProject();
		Story story = givenAStory(project);
		Assert.assertEquals("Nao Iniciada", story.getStatus().getStatus());
	}
	
	@Test
	public void testShouldSetCurrentDateAsEndDateWhenStoryFinish(){
		Project project = givenAProject();
		Story story = givenAStory(project);
		story.setStatus(Status.FINISHED);
		Assert.assertEquals(new Date(),story.getEndAt());
	}
	
	@Test
	public void testShouldNotUpdateEndDateWhenStoryBackToTheDevArea(){
		Date startDate = new Date();
		startDate.setTime(2);
		Project project = givenAProject();
		Story story = givenAStory(project);
		story.setStartedAt(startDate);
		story.setStatus(Status.FINISHED);
		story.setStatus(Status.IN_DEV);
		Assert.assertSame(startDate, story.getStartedAt());
		
		
	}
	
	private Project givenAProject() {
		Project project = new Project();
		project.setId(1L);
		project.setName("Test Project");
		project.setDescription("Description of Test Project");
		return project;
	}
	
	private Story givenAStory(final Project project){
		 Story story = new Story();
		 story.setCreatedBy(givenAUser());
		 story.setName("Create a Crud for Users");
		 story.setDescription("Here Description for the user story");
		 story.setProject(project);
		 return story;
	}

	private User givenAUser() {
		User user =new User();
		user.setName("Edipo");
		user.setEmail("edipofederle@gmail.com");
		user.setPassword("edipo");
		return user;
	}
	
	private TypeStory givenAType() {
		TypeStory type = new TypeStory();
		type.setType("Funcionalidade");
		return type;
	}
	
}
