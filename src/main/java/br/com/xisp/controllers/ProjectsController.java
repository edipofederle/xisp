package br.com.xisp.controllers;

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
import br.com.xisp.repository.ClientRepository;
import br.com.xisp.repository.ProjectRepository;
import br.com.xisp.repository.UserRepository;
import br.com.xisp.session.UserSession;
import static br.com.caelum.vraptor.view.Results.logic;

@Resource
public class ProjectsController {

	private final Validator validator;
	private final ProjectRepository repository;
	private final Result result;
	private final User currentUser;
	private final ClientRepository clientRepository;
	private final UserRepository userRepository;

	public ProjectsController(ProjectRepository repository, ClientRepository clientRespository, UserRepository userRepository, Validator validator, Result result, UserSession user) {
		this.repository = repository;
		this.clientRepository = clientRespository;
		this.userRepository = userRepository;
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

	public void newProject() {
		result.include("clients", clientRepository.showAll());
	}
	
	@Path("/projects/{project.id}/edita")
	@Get
	public Project edita(Project project) {
		Project p = loadProject(project);
		p.setListaClients(clientRepository.showAll());
		result.include("nameClient", p.getClient().getName());
		return p;
	}
	
	@Path("/projects/{project.id}")
	@Get
	public Project show(Project project){
		result.include("users", userRepository.usersWithoutProjects(project));
		return repository.load(project);
	}

	@Path("/projects")
	@Put
	public void alterar(final Project project) {
		Project p = loadProject(project);
		validateProject(project);
		validator.onErrorUsePageOf(ProjectsController.class).newProject();
		result.include("project", p);
		project.setOwner(this.currentUser);
		repository.update(p);
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
	public void remove(Project project) throws Exception {
		repository.remove(project);
		result.include("success", true);
		result.include("message", "<strong>Sucesso!</strong> Projeto deletado com sucesso.");
		result.use(logic()).redirectTo(ProjectsController.class).show(project);
	}
	

    @Path("/projects/{project.id}/participantes/") @Post
    public void addColaborator(Project project, User participante) {
    	User _participante = loadUser(participante);
        Project lproject = loadProject(project);
        lproject.getUsers().add(_participante);
        validator.onErrorUsePageOf(ProjectsController.class).show(project);
        result.redirectTo(ProjectsController.class).show(project);
    }

    
    @Path("/projects/{project.id}/removeParticipantes/") @Post
	public void removeColaborator(Project project, User participante) {
    	User _participante = loadUser(participante);
        Project lproject = loadProject(project);
        lproject.getUsers().remove(_participante);
        validator.onErrorUsePageOf(ProjectsController.class).show(project);
        result.redirectTo(ProjectsController.class).show(project);		
	}
    
	private Project loadProject(Project project) {
		Project lproject = repository.load(project);
		return lproject;
	}

	private User loadUser(User participante) {
		User _participante = userRepository.load(participante);
		return _participante;
	}
}