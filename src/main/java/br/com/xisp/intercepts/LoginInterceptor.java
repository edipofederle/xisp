package br.com.xisp.intercepts;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.xisp.controllers.LoginController;
import br.com.xisp.session.UserSession;

@Intercepts
public class LoginInterceptor implements Interceptor {
	
		private final UserSession sessionUser;
		private final HttpServletRequest request;
		private final HttpServletResponse response;
		private final Result result;

	    public LoginInterceptor(UserSession sessionUser, HttpServletRequest request, HttpServletResponse response, Result result) {
			this.sessionUser = sessionUser;
			this.request = request;
			this.response = response;
			this.result = result;
	    }

		@SuppressWarnings("unchecked")
		public boolean accepts(ResourceMethod method) {
			return !Arrays.asList(LoginController.class)
				.contains(method.getMethod().getDeclaringClass());
		}

		public void intercept(InterceptorStack stack, ResourceMethod method,
				Object resourceInstance) throws InterceptionException {
			if (this.sessionUser.getUser() == null) {
				try {
					response.sendRedirect(request.getContextPath() + "/login/login");
				} catch (IOException e) {
					throw new InterceptionException(e);
				}
	        } else {
	        	result.include("currentUser", sessionUser.getUser());
	            stack.next(method, resourceInstance);
	        }
		}
	}
