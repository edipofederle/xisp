package br.com.xisp.session;


import javax.servlet.http.HttpSession;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.com.xisp.models.Interation;

@Component
@SessionScoped
public class InterationSessionImpl implements InterationSession {
	
	private final HttpSession session;

	public InterationSessionImpl(HttpSession session) {
		this.session = session;
	}
	

	public void setInteration(Interation Interation) {
		this.session.setAttribute("currentInteration", Interation);
	}

	public Interation getInteration() {
		return (Interation) this.session.getAttribute("currentInteration");
	}

}