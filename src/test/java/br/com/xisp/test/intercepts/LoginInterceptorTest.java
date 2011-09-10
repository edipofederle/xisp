package br.com.xisp.test.intercepts;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertFalse;
import mocks.MockHttpSession;

import org.aspectj.lang.annotation.After;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.resource.DefaultResourceClass;
import br.com.caelum.vraptor.resource.DefaultResourceMethod;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.xisp.controllers.LoginController;
import br.com.xisp.intercepts.LoginInterceptor;
import br.com.xisp.models.User;
import br.com.xisp.session.UserSessionImpl;

public class LoginInterceptorTest {
	
	private Mockery mockery;
	private LoginInterceptor interceptor;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private InterceptorStack stack;
	private UserSessionImpl sessionUser;
	
	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();

		request = mockery.mock(HttpServletRequest.class);
		response = mockery.mock(HttpServletResponse.class);
		stack = mockery.mock(InterceptorStack.class);
		sessionUser = new UserSessionImpl(new MockHttpSession());
		interceptor = new LoginInterceptor(sessionUser, request, response, new MockResult());
	}
	
	@After(value = "")
	public void tearDown() throws Exception {
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void shouldAutorizeUserIfInSession() throws Exception {
		givenThereIsAUserInTheSession();

		shouldExecuteFlow();

		whenInterceptOccurs();
	}
	
	@Test
	public void shoudlRedirectToLoginPageIfThereIsNoUserInSession() throws Exception {
		givenThereIsNotAUserInTheSession();

		shouldNotExecuteFlow();
		shouldRedirectToLoginPage();

		whenInterceptOccurs();
	}
	
	@Test
	public void shouldDontInterceptLoginController() throws Exception {
		assertFalse(interceptor.accepts(anyResourceMethodOf(LoginController.class)));
	}
	
	@Test(expected=InterceptionException.class)
	public void shouldRethrowExceptions() throws Exception {
		givenThereIsNotAUserInTheSession();
		givenResponseThrowsException();

		whenInterceptOccurs();
	}
	
	private void givenThereIsAUserInTheSession() { sessionUser.setUser(new User());	}
	private void givenThereIsNotAUserInTheSession() {sessionUser.setUser(null);	}

	private void shouldExecuteFlow() {
		mockery.checking(new Expectations() {
			{
				one(stack).next(with(any(ResourceMethod.class)), with(any(Object.class)));
			}
		});
	}
	
	private void shouldNotExecuteFlow() {
		mockery.checking(new Expectations() {
			{
				never(stack).next(with(any(ResourceMethod.class)), with(any(Object.class)));
			}
		});

	}
	
	private void whenInterceptOccurs() {
		interceptor.intercept(stack, null, null);
	}
	
	private void shouldRedirectToLoginPage() throws IOException {
		mockery.checking(new Expectations() {
			{
				one(response).sendRedirect(with(containsString("/login/login")));
				allowing(request);
			}
		});
	}
	
	private ResourceMethod anyResourceMethodOf(Class<?> clazz) {
		return new DefaultResourceMethod(new DefaultResourceClass(clazz), clazz.getDeclaredMethods()[0]);
	}
	
	private void givenResponseThrowsException() throws IOException {

		mockery.checking(new Expectations() {
			{
				one(response).sendRedirect(with(any(String.class)));
				will(throwException(new IOException()));

				ignoring(anything());
			}
		});
	}


}
