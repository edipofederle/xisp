package br.com.xisp.session;

import javax.servlet.http.HttpSession;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.com.xisp.models.Project;

@Component
@SessionScoped
public class ProjectSessionImpl implements ProjectSession {
	
	private final HttpSession session;

	public ProjectSessionImpl(HttpSession session) {
		this.session = session;
	}
	
	/**
	 * @param Project
	 * 
	 * Seta um projeto na sessao, eh invocado quando o usuario acessa a view show do projeto
	 */
	public void setProject(Project project) {
		this.session.setAttribute("currentProject", project);
	}

	public Project getProject() {
		return (Project) this.session.getAttribute("currentProject");
	}

}