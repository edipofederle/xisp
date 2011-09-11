package br.com.xisp.test.dao;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Before;
import org.junit.Test;

import br.com.xisp.models.User;
import br.com.xisp.persistence.UserDao;

public class UserDaoTest {
	
	private UserDao dao;
	private Session session;

	@Before
	public void setUp() throws Exception {
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.configure().setProperty("hibernate.connection.url", "jdbc:mysql://127.0.0.1/xisp");
		session = cfg.buildSessionFactory().openSession();
		session.beginTransaction();
		dao = new UserDao(session);
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

	
	private User foundAUser(User user) {
		User userFound = dao.load(user);
		return userFound;
	}
	
}