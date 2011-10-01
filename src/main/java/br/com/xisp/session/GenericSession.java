package br.com.xisp.session;

import javax.servlet.http.HttpSession;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@SessionScoped
public class GenericSession {
	
	private final HttpSession session;

	public GenericSession(HttpSession session) {
		this.session = session;
	}
	
	public String getRedirect(){
		return (String) this.session.getAttribute("redirectTo");
	}
	
	public void setRedirect(String to){
		this.session.setAttribute("redirectTo", to);
	}
	
}
