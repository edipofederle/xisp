package br.com.xisp.controllers;

import static br.com.caelum.vraptor.view.Results.logic;

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
import br.com.xisp.models.Story;
import br.com.xisp.models.User;
import br.com.xisp.repository.ClientRepository;
import br.com.xisp.repository.ProjectRepository;
import br.com.xisp.repository.StoryRepository;
import br.com.xisp.repository.UserRepository;
import br.com.xisp.session.ProjectSession;
import br.com.xisp.session.UserSession;
import br.com.xisp.utils.StoryUtil;

/**
 * O resource <code>ProjectsController</code> manipula todas as opera�oes
 * com Projects, coisas como adicionar, remover editar Projeto.
 * 
 * Este controller tende a ser REST.
 * @author edipo
 *
 */
@Resource
public class ProjectsController {

	private final Validator validator;
	private final ProjectRepository repository;
	private final Result result;
	private final User currentUser;
	private final ClientRepository clientRepository;
	private final UserRepository userRepository;
	private final ProjectSession projectSession;
	private final StoryRepository storyRepository;
	
	
	/**
	 * Recebe todas as dependencias atraves do construtor.
	 * @param ProjectRepository repository.
	 * @param ClientRepository  clientRespository.
	 * @param UserRepository userRepository
	 * @param validator VRaptor validator.
	 * @param result VRaptor result handler.
	 * @param UserSession session para o usuario corrente.
	 */
	public ProjectsController(ProjectRepository repository, ClientRepository clientRespository, UserRepository userRepository, StoryRepository storyRepository, Validator validator, Result result, UserSession user, ProjectSession projectSession) {
		this.repository = repository;
		this.clientRepository = clientRespository;
		this.userRepository = userRepository;
		this.storyRepository = storyRepository;
		this.validator = validator;
		this.result = result;
		this.currentUser = user.getUser();
		this.projectSession = projectSession;
	}

	@Path("/projects/index")
	@Get
	public void index() {
		result.include("projects", repository.showAll(currentUser));

	}
	
	/**
	 * 
	 * VIEW: Redireciona para index page projects em caso de sucesso e para newProject em caso
	 * de falha.
	 * Este metodo adiciona um projeto.
	 * @param project
	 */
	@Path("/projects")
	@Post
	public void add(final Project project) {
		validateProject(project);
		validator.onErrorUsePageOf(ProjectsController.class).newProject();
		project.setOwner(this.currentUser);
		repository.add(project);
		result.include("success", true);
		result.include("message", "<strong>Sucesso!</strong> Projeto criado com sucesso.");
		result.include("defineFirstInteration", "� recomendado que voce cria a primeira itera�ao agora.");
		projectSession.setProject(project);
		result.redirectTo(InterationsController.class).index();
	}
	
	/**
	 * Este metodo apenas redireciona para o jsp newProject.jsp
	 */
	public void newProject() {
		result.include("clients", clientRepository.showAll());
	}
	
	/**
	 * 
	 * Este metodo eh responsavel por devolver um objeto Project populado para a view edita.
	 * 
	 * VIEW: /projets/1/edita
	 * @param project
	 * @return Project
	 */
	@Path("/projects/{project.id}/edita")
	@Get
	public Project edita(Project project) {
		Project p = loadProject(project);
		p.setListaClients(clientRepository.showAll());
		result.include("nameClient", p.getClient().getName());
		return p;
	}
	/**
	 * 
	 * Este metodo eh responsavel por exibir um projeto
	 * 
	 * VIEW: /proejcts/1
	 * @param project
	 * @return project
	 */
	@Path("/projects/{project.id}")
	@Get
	public Project show(Project project){
		
		List<Story> list = this.storyRepository.showAllStories(this.projectSession.getProject());
		
		int finished = 0;
		
		for (Story story : list) {
			if(story.getStatus().equals(br.com.xisp.models.Status.FINISHED))
				finished++;
		}
		
		result.include("users", userRepository.usersWithoutProjects(project));
		result.include("avg", StoryUtil.calculeAvgForStories(list));
		result.include("qntFinalizadas", finished);
		Project p = repository.load(project);
		projectSession.setProject(p);
		return p;
	}

	/**
	 * 
	 * Este metodo � respons�vel por realizar as altera�oes em um projeto.
	 * @param project
	 */
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
	
	/**
	 * Aceita request HTTP DELETE
	 * VIEW: /proejcts/1
	 * 
	 * Metodo que remove um <code>Project</code>
	 * 
	 * @param project
	 * @throws Exception
	 */
	@Path("/projects/{project.id}")
	@Delete
	public void remove(Project project) throws Exception {
		boolean error = false;
		try{
			repository.remove(project);
		}catch (Exception e) {
			error = true;
			result.include("erroDeleteProject","OPS. Voce precisa remover as Estorias de usuario antes.");
			result.forwardTo(ErrorsController.class).index();
		}
		if(!error){
			result.include("success", true);
			result.include("message", "<strong>Sucesso!</strong> Projeto deletado com sucesso.");
			result.use(logic()).redirectTo(ProjectsController.class).index();
		}
	}
	
	/**
	 * ACEITA request HTTP POST<br />
	 * 
	 * VIEW: /projects/1/participantes/
	 * 
	 * Este metodo � responsavel por adicionar um participante(<code>User</code>) a um <code>Project</code><Br/>
	 * @param project
	 * @param participante
	 */
    @Path("/projects/{project.id}/participantes/") @Post
    public void addColaborator(Project project, User participante) {
    	User _participante = loadUser(participante);
        Project lproject = loadProject(project);
        lproject.getUsers().add(_participante);
        validator.onErrorUsePageOf(ProjectsController.class).show(project);
        result.redirectTo(ProjectsController.class).show(project);
    }

	/**
	 * ACEITA request HTTP POST<br />
	 * 
	 * VIEW: /projects/1/removeParticipantes
	 * 
	 * Este metodo � responsavel por remover um participante(<code>User</code>) a um <code>Project</code>
	 * @param project
	 * @param participante
	 */
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
	
}