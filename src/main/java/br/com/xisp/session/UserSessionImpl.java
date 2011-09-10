package br.com.xisp.session;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.com.xisp.models.User;

@Component
@SessionScoped
public class UserSessionImpl implements UserSession, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3716681881130553322L;
	private final HttpSession session;

	public UserSessionImpl(HttpSession session) {
		this.session = session;
	}

	public void setUser(User user) {
		this.session.setAttribute("currentUser", user);
	}

	public User getUser() {
		return (User) this.session.getAttribute("currentUser");
	}

}