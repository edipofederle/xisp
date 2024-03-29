package br.com.xisp.test.controllers;

import java.sql.SQLException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.ValidationException;
import br.com.xisp.controllers.UsersController;
import br.com.xisp.models.User;
import br.com.xisp.repository.UserRepository;

public class UsersControllerTest {
	
	private Mockery mockery;
	private MockResult result;
	private UserRepository dao;
	private UsersController controller;

	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		dao = mockery.mock(UserRepository.class);
		result = new MockResult();
		controller = new UsersController(dao, new MockValidator(), result);
	}
	
	@Test
	public void shouldIndex(){
		willListAllUsers();
		controller.index();
	}
	
	@Test
	public void shouldAddAValidUSer() throws SQLException, Exception{
		User user = givenAValidUser();
		willAddUser(user);
		willValidateDuplication(user);
		controller.add(user);
	}
	
	@Test
	public void shoulNotAddInvalidUser() throws SQLException, Exception{
		User user = givenAInvalidUser();
		willNotAddUser(user);
		willValidateDuplication(user);
	    try {
	       controller.add(user);
	       Assert.fail();
	    } catch (ValidationException e) {
	       java.util.List<Message> errors = e.getErrors();
	       Assert.assertEquals("O campo name deve ser preenchido", errors.get(0).getMessage());
	       Assert.assertEquals("O campo email deve ser preenchido", errors.get(1).getMessage());
	       Assert.assertEquals("O campo password deve ser preenchido", errors.get(2).getMessage());
	    }
	}
	
	@Test
	public void shouldNotCreateADuplicateUser() throws SQLException, Exception{
		User user = givenAValidUser();
		willAddUser(user);
		willValidateDuplication(user);
		controller.add(user);
		User sameUser = givenAValidUser();
		 try {
			willAddUser(sameUser);
			willValidateDuplicationAndReturnTrue(sameUser);
			controller.add(sameUser);
			Assert.fail();
		 } catch (ValidationException e) {
		       java.util.List<Message> errors = e.getErrors();
		     Assert.assertEquals("Usuario existente.", errors.get(0).getMessage());
		 }
	}
	
	@Test
	public void shouldUpdateAUser(){
		User user = givenAValidUser();
		user.setName("Novo Nome");
		willUpdateTheUser(user);
		controller.update(user);
	}
	
	@Test
	public void loadForEdit(){
		User user = givenAInvalidUser();
		mockery.checking(new Expectations() {{
			one(dao).load(with(any(User.class)));
		}});
		controller.edita(user);
	}
	
	@Test
	public void loadForShow(){
		User user = givenAInvalidUser();
		mockery.checking(new Expectations() {{
			one(dao).load(with(any(User.class)));
		}});
		controller.show(user);
	}
	
	@Test
	public void shouldRemoveAUser() throws Exception{
		User user = givenAValidUser();
		willAddUser(user);
		willValidateDuplicationAndReturnFalse(user);
		controller.add(user);
		willRemoveAUser(user);
		controller.remove(user);
		Assert.assertNull(dao.load(user));
	}
	
	@Test
	public void shouldLoadAUserToEdit(){
		User user = givenAValidUser();
		willLoadAUser(user);
		controller.edita(user);
	}

	private void willLoadAUser(final User user) {
		mockery.checking(new Expectations() {{
			one(dao).load(user);
		}});
	}

	private void willRemoveAUser(final User user) throws Exception {
		mockery.checking(new Expectations() {{
			one(dao).remove(user);
			allowing(anything());
		}});
	}

	private void willUpdateTheUser(final User user) {
		mockery.checking(new Expectations() {{
			one(dao).update(user);
		}});
		
	}

	private void willValidateDuplicationAndReturnTrue(final User sameUser) {
		mockery.checking(new Expectations() {{
		    one (dao).isDuplicate(sameUser.getName());
		    will(returnValue(true));
		}});
	}
	
	private void willValidateDuplicationAndReturnFalse(final User sameUser) {
		mockery.checking(new Expectations() {{
		    one (dao).isDuplicate(sameUser.getName());
		    will(returnValue(false));
		}});
	}

	private void willValidateDuplication(final User user) {
		mockery.checking(new Expectations() {
			{one(dao).isDuplicate(user.getName());}
		});
		
	}

	private User givenAInvalidUser() {
		User user = new User();
		user.setName("");
		user.setEmail("");
		user.setPassword("");
		return user;
	}

	private void willAddUser(final User user) throws Exception {
		mockery.checking(new Expectations() {
			{one(dao).add(user); }
		});
		
	}
	
	private void willListAllUsers() {
		mockery.checking(new Expectations() {
			{one(dao).showAll();}
		});
	}
	

	private void willNotAddUser(final User user) throws Exception {
		mockery.checking(new Expectations() {
			{never(dao).add(user);}
		});
	}

	private User givenAValidUser() {
		User user = new User();
		user.setName("Fulano");
		user.setEmail("edipo@gmail.com");
		user.setPassword("edipo");
		return user;
	}

}