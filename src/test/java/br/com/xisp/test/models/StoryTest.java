package br.com.xisp.test.models;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.xisp.models.Project;
import br.com.xisp.models.Status;
import br.com.xisp.models.Story;
import br.com.xisp.models.Type;
import br.com.xisp.models.TypeStory;
import br.com.xisp.models.User;

public class StoryTest {
	
	private Story story;
	
	@Before
	public void setUp(){
		story = new Story();
	}
	
	@Test
	public void testShouldReturnStatusRDF(){
		story.setName("Build a Tower");
		TypeStory t = givenAType();
		story.setTypeStory(t);
		story.setDescription("Figure out how build a tower");
		Assert.assertEquals("RFD", story.getStatus().getStatus());
		Assert.assertEquals("Funcionalidade", story.getTypeStory().getType());
	}

	private TypeStory givenAType() {
		TypeStory type = new TypeStory();
		type.setType("Funcionalidade");
		return type;
	}
	
	@Test
	public void testStoryOne(){
		Project project = givenAProject();
		Story story = givenAStory(project);
		Assert.assertEquals("Edipo", story.getCreatedBy().getName());
		Assert.assertEquals("Test Project", story.getProject().getName());
		Assert.assertEquals("RFD", story.getStatus().getStatus());
	}
	
	@Test
	public void testStoryChangeStatus(){
		Project project = givenAProject();
		Story story = givenAStory(project);
		Assert.assertEquals("RFD",story.getStatus().getStatus());
		story.setStatus(Status.READY_FOR_TEST);
		Assert.assertEquals("RFT",story.getStatus().getStatus());
	}
	
	@Test
	public void testFinishStory(){
		Project project = givenAProject();
		Story story = givenAStory(project);
		story.markAsCompleted();
		Assert.assertEquals("FINISHED", story.getStatus().getStatus());
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
		 story.setStatus(Status.READY_FOR_DEV);
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
}
