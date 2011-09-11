package br.com.xisp.controllers;

import java.util.List;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.xisp.models.Project;
import br.com.xisp.models.User;
import br.com.xisp.repository.ProjectRepository;
import br.com.xisp.session.UserSession;

/**
 * @author edipo Classe controladora responsavel pelos projetos
 */

@Resource
public class ProjectsController {

	private final Validator validator;
	private final ProjectRepository repository;
	private final Result result;
	private final User currentUser;

	public ProjectsController(ProjectRepository repository, Validator validator, Result result, UserSession user) {
		this.repository = repository;
		this.validator = validator;
		this.result = result;
		this.currentUser = user.getUser();
	}

	@Path("/projects/index")
	@Get
	public void index() {
		result.include("projects", repository.showAll(currentUser));

	}

	@Path("/projects")
	@Post
	public void add(final Project project) {
		validateProject(project);
		validator.onErrorUsePageOf(ProjectsController.class).newProject();
		project.setOwner(this.currentUser);
		repository.add(project);
		result.include("success", true);
		result.include("message", "<strong>Sucesso!</strong> Projeto criado com sucesso.");
		result.redirectTo(this).index();
	}

	public List<Project> lista() {
		return repository.showAll(currentUser);
	}

	public void newProject() {}
	
	@Path("/projects/{project.id}/edita")
	@Get
	public Project edita(Project project) {
		return repository.load(project);
	}
	
	@Path("/projects/{project.id}")
	@Get
	public Project show(Project project){
		return repository.load(project);
	}

	@Path("/projects")
	@Put
	public void alterar(final Project project) {
		validateProject(project);
		validator.onErrorUsePageOf(ProjectsController.class).newProject();
		result.include("project", project);
		project.setOwner(this.currentUser);
		repository.update(project);
		result.include("success", true);
		result.include("message", "<strong>Sucesso!</strong> Projeto alterado com sucesso.");
		result.redirectTo(this).index();
	}

	private void validateProject(final Project project) {
		validator.checking(new Validations() {
			{
				that(!project.getName().isEmpty(), "erro",
						"validacao.project.name");
				that(!project.getDescription().isEmpty(), "erro",
						"validacao.project.description");
				that(!(project.getDescription().length() > 244), "erro",
						"validacao.project.maior");
			}
		});
	}

	@Path("/projects/{project.id}")
	@Delete
	public void remove(Project project) {
		repository.remove(project);
		result.include("success", true);
		result.include("message", "<strong>Sucesso!</strong> Projeto deletado com sucesso.");
		result.redirectTo(ProjectsController.class).index();
	}
}