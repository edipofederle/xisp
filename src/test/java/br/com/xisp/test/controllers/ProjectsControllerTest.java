package br.com.xisp.test.controllers;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.ValidationException;
import br.com.xisp.controllers.ProjectsController;
import br.com.xisp.models.Client;
import br.com.xisp.models.Project;
import br.com.xisp.models.User;
import br.com.xisp.repository.ClientRepository;
import br.com.xisp.repository.ProjectRepository;
import br.com.xisp.session.UserSession;

public class ProjectsControllerTest {


	private Mockery mockery;
	private MockResult result;
	private ProjectRepository repo;
	private ProjectsController controller;
	private UserSession sessionUser;
	private ClientRepository clientRepostiroy;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		repo = mockery.mock(ProjectRepository.class);
		clientRepostiroy = mockery.mock(ClientRepository.class);
		result = new MockResult();
		sessionUser = mockery.mock(UserSession.class);
		mockery.checking(new Expectations() {
			{
				allowing(sessionUser);
			}
		});
		controller = new ProjectsController(repo, clientRepostiroy, new MockValidator(), result, sessionUser);

	}
	
	@Test
	public void shouldLoadClientWhenLoadNewPage(){
		willFindAllClient();
		controller.newProject();
		List<Client> list = result.included("clients");
		Assert.assertNotNull(list);
	}
	
	@Test
	public void shouldListAllProjects(){
		willListAllProjects();
		controller.index();
	}
	
	@Test
	public void shouldLoadAProjectShow(){
		Project project = givenAProject();
		willLoadAProjectToEdit(project);
		controller.show(project);
	}

	private void willLoadAProjectToEdit(final Project project) {
		mockery.checking(new Expectations() {
			{
				one(clientRepostiroy).showAll();
				allowing(repo).load(with(any(Project.class)));
			}
		});
	}
	
	private void willReturnAProject(){
		mockery.checking(new Expectations() {
			{
				repo.load(with(any(Project.class)));
				will(returnValue(with(any(Project.class))));
			}
		});
	}

	private void willListAllProjects() {
		mockery.checking(new Expectations() {
			{
				one(repo).showAll(with(any(User.class)));
				allowing(anything());
			}
		});
	}

	@Test
	public void shouldAddAValidProject(){
		Project project = givenAProject();
		willAddTheProject(project);
		
		controller.add(project);
	}

	private Project givenAProject() {
		Project project = new Project();
		project.setName("Test Project");
		project.setDescription("Description of Test Project");
		willAddTheProject(project);
		repo.add(project);
		return project;
	}
	
	@Test
	public void shouldUpdateAProject(){
		Project project = givenAProject();
		willUpdateTheProject(project);
		controller.alterar(project);
	}

	@Test
	public void shouldNOTAddProject() throws Exception{
		Project project = new Project();
		project.setName("");
		project.setDescription("");
		willNotAddTheProject(project);
	    try {
	        controller.add(project);
	       Assert.fail();
	    } catch (ValidationException e) {
	       java.util.List<Message> errors = e.getErrors();
	       Assert.assertEquals("Nome nao pode ser branco.", errors.get(0).getMessage());
	       Assert.assertEquals("Descricao nao pode ser branco.", errors.get(1).getMessage());
	    }
	}
	
	@Test
	public void shouldValidateProjectEdit() throws Exception{
		Project project = givenValidProject();
		try{
			project.setDescription("Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos, e vem sendo utilizado desde o século XVI, quando um impressor .Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos, e vem sendo utilizado desde o século XVI, quando um impressor .");
			controller.alterar(project);
			Assert.fail();
		}catch (ValidationException e) {
		       java.util.List<Message> errors = e.getErrors();
		       Assert.assertEquals("Descricao muito longa.", errors.get(0).getMessage());
		}
		
	}
	
	@Test
	public void shouldNOTAddProjectWithDescriptionLong() throws Exception{
		Project project = new Project();
		project.setName("Teste");
		project.setDescription("Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos, e vem sendo utilizado desde o século XVI, quando um impressor .Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos, e vem sendo utilizado desde o século XVI, quando um impressor .");
		willNotAddTheProject(project);
	    try {
	        controller.add(project);
	       Assert.fail();
	    } catch (ValidationException e) {
	       java.util.List<Message> errors = e.getErrors();
	       Assert.assertEquals("Descricao muito longa.", errors.get(0).getMessage());
	    }
	}
	
	@Test
	public void shouldRemoveAProject() throws Exception{
		Project project = givenValidProject();
		willAddTheProject(project);
		controller.add(project);
		controller.remove(project);
		Assert.assertNull(repo.load(project));
	}
	
	@Test
	public void shouldShowAProject(){
		Project project = givenValidProject();
		willShowAProject(project);
		controller.index();
	}
	
	@Test
	public void mustReturnAllProjectsInAGivenUser(){
		User user = givenAValidUser();
		willReturnAllProejctsBelongsToUser(user);
	}

	@SuppressWarnings("unused")
	private void willLoadProject(final Project project) {
		mockery.checking(new Expectations() {
			{
				one(repo).load(project);
				one(clientRepostiroy).showAll();
			}
		});
	}

	private void willReturnAllProejctsBelongsToUser(final User user) {
		mockery.checking(new Expectations() {
			{
				one(repo).showAll(user);
			}
		});
	}

	private void willShowAProject(final Project project) {
		mockery.checking(new Expectations() {
			{
				one(repo).load(project);
				allowing(anything());
			}
		});
		
	}

	private void willAddTheProject(final Project project) {
		mockery.checking(new Expectations() {
			{
				one(repo).add(project);
				allowing(anything());
			}
		});
	}
	
	private void willUpdateTheProject(final Project project){
		mockery.checking(new Expectations() {
			{
				one(repo).update(project);
			}
		});
	}

	private void willNotAddTheProject(final Project project) {
		mockery.checking(new Expectations() {
			{
				never(repo).add(project);
			}
		});
	}
	
	private void willFindAllClient() {
		mockery.checking(new Expectations() {
			{
				one(clientRepostiroy).showAll();
			}
		});
	}
	
	private Project givenValidProject() {
		Project project = new Project();
		project.setName("Meu Projeto");
		project.setDescription("Minha descricao do meu projeto");
		willAddTheProject(project);
		repo.add(project);
		return project;
	}
	
	private User givenAValidUser() {
		User user = new User();
		user.setName("Fulano");
		user.setEmail("edipo@gmail.com");
		user.setPassword("edipo");
		return user;
	}

}