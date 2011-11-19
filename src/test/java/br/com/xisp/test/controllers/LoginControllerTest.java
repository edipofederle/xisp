package br.com.xisp.test.controllers;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.xisp.controllers.LoginController;
import br.com.xisp.models.User;
import br.com.xisp.repository.UserRepository;
import br.com.xisp.session.InterationSession;
import br.com.xisp.session.ProjectSession;
import br.com.xisp.session.UserSession;

public class LoginControllerTest {
	
	private Mockery mockery;
	private MockResult result;
	private UserRepository dao;
	private LoginController controller;
	private UserSession sessionUser;
	private InterationSession interationSession;
	private ProjectSession projectSession;
	
	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		dao = mockery.mock(UserRepository.class);
		interationSession = mockery.mock(InterationSession.class);
		projectSession= mockery.mock(ProjectSession.class);
		result = new MockResult();
		sessionUser = mockery.mock(UserSession.class);
		controller = new LoginController(result, dao, sessionUser,interationSession, projectSession);
	}
	
	@Test
	public void shouldLogin() throws Exception{
		final User user = givenAUser();
		mockery.checking(new Expectations() {
			{one(dao).add(user);}
		});
		dao.add(user);
		willCheckUser(user.getEmail(), user.getPassword(), user);
		controller.login(user);
	}
	
	@Test
	public void shouldNotLogin() throws Exception{
		final User user = givenAUser();
		willNotMakeLogin(user);
		willCheckUser(user.getEmail(), user.getPassword(), user);
		controller.login(user);
	}
	
	@Test(expected=java.lang.Exception.class)
	public void shouldNotLoginExp() throws Exception{
		final User user = givenAInvalidLogin();
		mockery.checking(new Expectations() {
			{one(dao).login(null, null);}
		});
		controller.login(user);
	}
	
	@Test
	public void shouldLogout() throws Exception{
		final User user = givenAUser();
		givenUserIntoSession(user);
		
		willLogoutUser();
		controller.logout();
		Assert.assertNull(sessionUser.getUser());
	}
	
	private void givenUserIntoSession(final User user) {
		mockery.checking(new Expectations() {
			{one(sessionUser).setUser(user);}
		});
		sessionUser.setUser(user);
		
	}
	
	private void willLogoutUser() throws Exception {
		mockery.checking(new Expectations() {
			{
				one(sessionUser).setUser(null);
				ignoring(anything());
			}
		});
	}

	private void willNotMakeLogin(final User user) {
		mockery.checking(new Expectations() {
			{never(sessionUser).setUser(user);}
		});
	}

	private User givenAUser() {
		final User user = new User();
		user.setEmail("edipo");
		user.setName("edipo");
		user.setPassword("edipo");
		return user;
	}

	private void willCheckUser(final String email, final String password, final User user) throws Exception {
		mockery.checking(new Expectations() {
			{
				one(dao).login(email, password);
				one(sessionUser).setUser(with(any(User.class)));
				
			}
		});
	}
	
	private User givenAInvalidLogin() {
		final User user = new User();
		user.setEmail(null);
		user.setName("edipo");
		user.setPassword(null);
		return user;
	}
	
}
