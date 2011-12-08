package br.com.xisp.test.controllers;

import java.sql.SQLException;
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
import br.com.xisp.mail.Mailer;
import br.com.xisp.models.Client;
import br.com.xisp.models.Project;
import br.com.xisp.models.User;
import br.com.xisp.repository.ClientRepository;
import br.com.xisp.repository.ProjectRepository;
import br.com.xisp.repository.StoryRepository;
import br.com.xisp.repository.UserRepository;
import br.com.xisp.session.InterationSession;
import br.com.xisp.session.ProjectSession;
import br.com.xisp.session.UserSession;

public class ProjectsControllerTest {

	private Mockery mockery;
	private MockResult result;
	private ProjectRepository repo;
	private ProjectsController controller;
	private UserSession sessionUser;
	private InterationSession sessionIteration;
	private ClientRepository clientRepostiroy;
	private UserRepository userRepository;
	private ProjectSession sessionProject;
	private StoryRepository storyRepository;
	private Mailer mailer;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		repo = mockery.mock(ProjectRepository.class);
		clientRepostiroy = mockery.mock(ClientRepository.class);
		userRepository = mockery.mock(UserRepository.class);
		storyRepository = mockery.mock(StoryRepository.class);
		result = new MockResult();
		sessionUser = mockery.mock(UserSession.class);
		mailer = mockery.mock(Mailer.class);
		sessionProject = mockery.mock(ProjectSession.class);
		sessionIteration = mockery.mock(InterationSession.class);
		mockery.checking(new Expectations() {
			{
				allowing(sessionUser);
			}
		});
		controller = new ProjectsController(repo, clientRepostiroy,
				userRepository, storyRepository, new MockValidator(), result,
				sessionUser, sessionProject, sessionIteration, mailer);
	}

	@Test
	public void shouldLoadClientWhenLoadNewPage() {
		willFindAllClient();
		controller.newProject();
		List<Client> list = result.included("clients");
		Assert.assertNotNull(list);
	}

	@Test
	public void shouldListAllProjects() {
		willListAllProjects();
		controller.index();
	}

	public void shouldLoadAProjectShow() {
		final Project project = givenAProject();
		willLoadAProjectToEdit(project);
		willLoadAllUsers(project);

		mockery.checking(new Expectations() {
			{
				one(sessionProject).setProject(with(any(Project.class)));
				one(sessionProject).getProject();
				one(storyRepository).showAllStories(with(any(Project.class)));
			}
		});
		controller.show(project);
		Assert.assertEquals("Test Project", project.getName());
	}

	@Test
	public void shouldAddAValidProject() throws SQLException, Exception {
		Project project = givenAProject();
		willAddTheProject(project);

		controller.add(project);
	}

	@Test
	public void shouldUpdateAProject() {
		Project project = givenAProject();
		willUpdateTheProject(project);
		controller.alterar(project);
	}

	@Test
	public void shouldNOTAddProject() throws Exception {
		Project project = new Project();
		project.setName("");
		project.setDescription("");
		willNotAddTheProject(project);
		try {
			controller.add(project);
			Assert.fail();
		} catch (ValidationException e) {
			java.util.List<Message> errors = e.getErrors();
			Assert.assertEquals("Nome nao pode ser branco.", errors.get(0)
					.getMessage());
			Assert.assertEquals("Descricao nao pode ser branco.", errors.get(1)
					.getMessage());
		}
	}

	@Test
	public void shouldValidateProjectEdit() throws Exception {
		final Project project = givenValidProject();
		try {
			project.setDescription("Lorem Ipsum � simplesmente uma simula��o de texto da ind�stria tipogr�fica e de impressos, e vem sendo utilizado desde o s�culo XVI, quando um impressor .Lorem Ipsum � simplesmente uma simula��o de texto da ind�stria tipogr�fica e de impressos, e vem sendo utilizado desde o s�culo XVI, quando um impressor .");
			mockery.checking(new Expectations() {
				{
					one(repo).load(project);
					will(returnValue(any(Project.class)));
				}
			});
			controller.alterar(project);
			Assert.fail();
		} catch (ValidationException e) {
			java.util.List<Message> errors = e.getErrors();
			Assert.assertEquals("Descricao muito longa.", errors.get(0)
					.getMessage());
		}

	}

	@Test
	public void shouldNOTAddProjectWithDescriptionLong() throws Exception {
		Project project = new Project();
		project.setName("Teste");
		project.setDescription("Lorem Ipsum � simplesmente uma simula��o de texto da ind�stria tipogr�fica e de impressos, e vem sendo utilizado desde o s�culo XVI, quando um impressor .Lorem Ipsum � simplesmente uma simula��o de texto da ind�stria tipogr�fica e de impressos, e vem sendo utilizado desde o s�culo XVI, quando um impressor .");
		willNotAddTheProject(project);
		try {
			controller.add(project);
			Assert.fail();
		} catch (ValidationException e) {
			java.util.List<Message> errors = e.getErrors();
			Assert.assertEquals("Descricao muito longa.", errors.get(0)
					.getMessage());
		}
	}

	@Test
	public void shouldRemoveAProject() throws Exception {
		Project project = givenValidProject();
		willAddTheProject(project);
		controller.add(project);
		controller.remove(project);
		Assert.assertNull(repo.load(project));
	}

	@Test
	public void shouldShowAProject() throws Exception {
		Project project = givenValidProject();
		willShowAProject(project);
		controller.index();
	}

	@Test
	public void mustReturnAllProjectsInAGivenUser() {
		User user = givenAValidUser();
		willReturnAllProejctsBelongsToUser(user);
	}

	@Test
	public void shouldAddAUserHasParticipante() {
		mockery.checking(new Expectations() {
			{
				one(mailer).sendMail(with(any(String.class)), with(any(String.class)), with(any(String.class)), with(any(String.class)));
			}
		});
		final Project p = givenAProject();
		final User user = givenAValidUser();
		willLoadProjectAndLoadUser(p, user);


		controller.addColaborator(p, user);
	}

	@Test
	public void shouldRemoveAUserHasParticipant() {
		final Project p = givenAProject();
		final User user = givenAValidUser();
		willLoadProjectAndLoadUser(p, user);
		mockery.checking(new Expectations() {
			{
				one(mailer).sendMail(with(any(String.class)), with(any(String.class)), with(any(String.class)), with(any(String.class)));
			}
		});
		controller.addColaborator(p, user);
		willLoadProjectAndLoadUser(p, user);
		controller.removeColaborator(p, user);
	}

	private void willLoadProjectAndLoadUser(final Project p, final User user) {
		mockery.checking(new Expectations() {
			{
				one(repo).load(p);
				will(returnValue(p));
				one(userRepository).load(user);
				will(returnValue(user));

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

	@SuppressWarnings("unused")
	private void willLoadProject(final Project project) {
		mockery.checking(new Expectations() {
			{
				one(repo).load(project);
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

	private void willAddTheProject(final Project project) throws Exception {
		mockery.checking(new Expectations() {
			{
				one(repo).add(project);
				allowing(anything());
			}
		});
	}

	private void willUpdateTheProject(final Project project) {
		mockery.checking(new Expectations() {
			{
				one(repo).load(project);
				will(returnValue(project));
				one(repo).update(project);

			}
		});
	}

	private void willNotAddTheProject(final Project project) throws Exception {
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

	private Project givenValidProject() throws Exception {
		Project project = new Project();
		project.setName("Meu Projeto");
		project.setClient(givenAClient());
		project.setDescription("Minha descricao do meu projeto");
		willAddTheProject(project);
		return project;
	}

	private Client givenAClient() {
		Client client = new Client();
		client.setName("Robert");
		client.setEndereco("New York - Main Street");
		return client;
	}

	private User givenAValidUser() {
		User user = new User();
		user.setId(1L);
		user.setName("Fulano");
		user.setEmail("edipo@gmail.com");
		user.setPassword("edipo");
		return user;
	}

	private Project givenAProject() {
		Project project = new Project();
		project.setId(1L);
		project.setName("Test Project");
		project.setDescription("Description of Test Project");
		return project;
	}

	private void willLoadAllUsers(final Project project) {
		mockery.checking(new Expectations() {
			{
				one(userRepository).usersWithoutProjects(project);
			}
		});
	}

	private void willLoadAProjectToEdit(final Project project) {
		mockery.checking(new Expectations() {
			{
				one(clientRepostiroy).showAll();
				allowing(repo).load(with(any(Project.class)));
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


}