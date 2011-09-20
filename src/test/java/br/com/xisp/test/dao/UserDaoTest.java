package br.com.xisp.test.dao;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Before;
import org.junit.Test;

import br.com.xisp.models.Project;
import br.com.xisp.models.User;
import br.com.xisp.persistence.ProjectDao;
import br.com.xisp.persistence.UserDao;

public class UserDaoTest {
	
	private UserDao dao;
	private ProjectDao pdao;
	private Session session;

	@Before
	public void setUp() throws Exception {
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.configure().setProperty("hibernate.connection.url", "jdbc:mysql://127.0.0.1/xisp");
		session = cfg.buildSessionFactory().openSession();
		session.beginTransaction();
		dao = new UserDao(session);
		pdao = new ProjectDao(session);
	}
	
	@Test
	public void shouldAddAUser(){
		User user = givenAUser();
		assertThat(dao.find("edipo2"), is(user));
	}
	
	@Test
	public void shouldUpdateAUser(){
		User user = givenAUser();
		dao.add(user);
		user.setName("Novo Nome");
		dao.update(user);
		User userFound = foundAUser(user);
		Assert.assertEquals("Novo Nome", userFound.getName());
	}

	private User givenAUser() {
		User user = new User();
		user.setName("edipo2");
		user.setPassword("edipo");
		user.setEmail("edipofederle@gmail.com");
		dao.add(user);
		return user;
	}
	
	@Test
	public void shouldLoadUser(){
		User user = givenAUser();
		Assert.assertNotNull(dao.find(user.getName()));
	}
	
	@SuppressWarnings("unused")
	@Test
	public void shouldMatchUserAndPasswordLogin() throws Exception{
		User user = givenAUser();
		User aUser = dao.login("edipofederle@gmail.com", "edipo");
		Assert.assertNotNull(aUser);
	}
	
	@SuppressWarnings("unused")
	@Test(expected=Exception.class)
	public void shouldNotFindAUser() throws Exception{
		User user = givenAUser();
		User aUser = dao.login("elf@gmail.com", "edipo");
		Assert.assertNull(aUser);
	}
	
	@Test
	public void shouldRemoveAUser(){
		User user = givenAUser();
		dao.add(user);
		dao.remove(user);
		User notFound = foundAUser(user);
		Assert.assertNull(notFound);
	}
	
	@Test
	public void shouldReturnUsersWithoutProjects(){
		Project project = givenAProject();
		pdao.add(project);
		User owner = givenAnUserOwnerOf(project);
		User participant = givenAnUserParticipanteOf(project);
		User user = givenAUserWihtoutProject("pedro");

		List<User> users = dao.usersWithoutProjects(project);

		assertThat(users, hasItem(user));
		assertThat(users, not(hasItem(owner)));
		assertThat(users, not(hasItem(participant)));
	}
	
	private Project givenAProject() {
		Project project = new Project();
		project.setId(1L);
		project.setName("Project 2");
		project.setDescription("Description of Test Project");
		return project;
	}
	
	private User givenAnUserParticipanteOf(Project project) {
		User user = givenAUserWihtoutProject("Diego");
		project.setUsers(Arrays.asList(user));
		this.session.flush();
		return user;
	}
	
	private User foundAUser(User user) {
		User userFound = dao.load(user);
		return userFound;
	}
	
	private User givenAnUserOwnerOf(Project project) {
		User user = givenAUserWihtoutProject("Robson");
		project.setOwner(user);
		this.session.flush();
		return user;
	}

	private User givenAUserWihtoutProject(String name) {
		User user = new User();
		user.setName(name);
		user.setEmail(name+"@gmail.com");
		user.setPassword("secret");
		dao.add(user);
		this.session.flush();
		return user;
	}
	
}