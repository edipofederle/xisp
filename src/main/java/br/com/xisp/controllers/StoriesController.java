package br.com.xisp.controllers;
import static br.com.caelum.vraptor.view.Results.json;
import static br.com.caelum.vraptor.view.Results.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.xisp.auxajax.ResultChangeStory;
import br.com.xisp.auxajax.Status;
import br.com.xisp.models.AcceptenceTest;
import br.com.xisp.models.History;
import br.com.xisp.models.Interation;
import br.com.xisp.models.Project;
import br.com.xisp.models.Story;
import br.com.xisp.models.TypeStory;
import br.com.xisp.models.User;
import br.com.xisp.repository.AcceptenceTestRepository;
import br.com.xisp.repository.HistoryStoryRepository;
import br.com.xisp.repository.InteractionRepository;
import br.com.xisp.repository.ProjectRepository;
import br.com.xisp.repository.StoryRepository;
import br.com.xisp.repository.TypeStoryRepository;
import br.com.xisp.repository.UserRepository;
import br.com.xisp.session.InterationSession;
import br.com.xisp.session.ProjectSession;
import br.com.xisp.session.UserSession;

/**
 * 
 * @author edipo
 * 
 *         Controller Responsavel por controlar a parte das Estorias de Usuario
 * 
 */
@Resource
public class StoriesController {

	private StoryRepository repository;
	private Result result;
	private Project currentProject;
	private ProjectSession projectSession;
	private InterationSession interationSession;
	private ProjectRepository projectRepository;
	private InteractionRepository interationRepository;
	private TypeStoryRepository typestoryRepository;
	private UserRepository userRepository;
	private AcceptenceTestRepository acceptenceTestRepository;
	private HistoryStoryRepository historyStoryRepository;
	private User currentUser;
	private final Validator validator;
	
	static Logger logger = Logger.getLogger(StoriesController.class);  

	public StoriesController(StoryRepository repository,
			ProjectRepository repositoryProject,
			InteractionRepository interationRepository,
			TypeStoryRepository typestoryRepository,
			AcceptenceTestRepository acceptenceTestRepository,
			UserRepository userRepository, Result result,
			ProjectSession projectSession, InterationSession interationSession,
			HistoryStoryRepository historyStoryRepository, UserSession user,
			Validator validator) {
		this.repository = repository;
		this.result = result;
		this.projectRepository = repositoryProject;
		this.projectSession = projectSession;
		this.interationSession = interationSession;
		this.interationRepository = interationRepository;
		this.typestoryRepository = typestoryRepository;
		this.acceptenceTestRepository = acceptenceTestRepository;
		this.historyStoryRepository = historyStoryRepository;
		this.userRepository = userRepository;
		this.currentUser = user.getUser();
		this.validator = validator;
	}

	@Path("/stories/{project.id}/index")
	@Get
	public void index(Project project) {
		List<Story> stories = new ArrayList<Story>();
		Project p = projectRepository.load(project);
		this.projectSession.setProject(p);
		this.currentProject = this.projectSession.getProject();
		if (this.currentProject == null) {
			result.include("selectProjectBefore", "Selecione um projeto!");
			result.redirectTo(ErrorsController.class).index();
		} else {
			stories = repository.showAllStories(this.currentProject,
					getcurrentIteration());
		}
		result.include("project", p);
		result.include("unRelatedStories",
				this.repository.unrelatedStories(this.currentProject));
		result.include("stories", stories);
	}

	public void neww() throws Exception {
		List<Interation> listIterationsTemp = new ArrayList<Interation>();
		List<Interation> listIterations = new ArrayList<Interation>();
		List<TypeStory> listTypes = new ArrayList<TypeStory>();
		List<User> listUsers = new ArrayList<User>();
		try {
			listTypes = this.typestoryRepository.findAll();
		} catch (Exception e) {
			logger.error("Erro ao carregar tipos de estoria de usuario");
		}
		// Carrega Todas as Iteracoes de um dado Projecto
		try {
			listIterationsTemp = this.interationRepository
					.showAllInterations(projectSession.getProject());
			for (Interation i : listIterationsTemp) {
				if (!i.isDone())
					listIterations.add(i);
			}
			listUsers = this.userRepository.showAll();
		} catch (Exception e) {
			logger.error("Erro ao caregar Iteracoe nao finalizadas");
		}

		if (listIterations.size() == 0 || listIterations.equals(null)) {
			result.include("erroSemIteracoes",
					"Nao existem iteracoes criada, crie uma primeiro.");
			result.forwardTo(ErrorsController.class).index();
		}
		result.include("types", listTypes);
		result.include("listIterations", listIterations);
		result.include("users", listUsers);
	}

	@Path("/stories")
	@Post
	public void add(final Story story) throws Exception {
		story.setProject(projectSession.getProject());
		story.setCreatedBy(this.currentUser);
		AcceptenceTest at = new AcceptenceTest();
		at.setTest(story.getAcceptsTest());
		
		boolean hasError = false;
		
		try {
			acceptenceTestRepository.add(at);
		} catch (SQLException e) {
			logger.error("Erro de sql ao salvar testes de aceitacao para estoria " + story.getId() + "." + e.getMessage());
			hasError = true;
		} catch (Exception e) {
			logger.error("Erro geral ao salvar testes de aceitacao para estoria " + story.getId() + "." + e.getMessage());
			hasError = true;
		}
		story.setTest(at);
		try {
			repository.add(story);
		} catch (SQLException e) {
			logger.error("Erro de sql ao salvar estoria para estoria " + story.getId() + "." + e.getMessage());
			hasError = true;
		} catch (Exception e) {
			logger.error("Erro gerak ao salvar estoria para estoria " + story.getId() + "." + e.getMessage());
			hasError = true;
		}
		if(!hasError){
			result.include("success", true);
			result.include("sucessoEstoria", "Estoria " + story.getName() +  " foi cadastrada com sucesso");
			result.redirectTo(StoriesController.class).neww();
		}else{
			result.forwardTo(ErrorsController.class).index();
		}
	}

	/**
	 * Redireciona para a view onde se encontra o quadro com com as estorias em
	 * seus diferentes niveis.
	 */
	@Path("/stories/board")
	public void board() {

		List<Story> stories = new ArrayList<Story>();
		List<Story> noStarted = new ArrayList<Story>();
		List<Story> inDev = new ArrayList<Story>();
		List<Story> readyTest = new ArrayList<Story>();
		List<Story> inTest = new ArrayList<Story>();
		List<Story> finished = new ArrayList<Story>();

		stories = repository.showAllStories(projectSession.getProject(),
				getcurrentIteration());

		for (Story story : stories) {
			if (story.getStatus().equals(br.com.xisp.models.Status.NOSTARTED))
				noStarted.add(story);
			if (story.getStatus().equals(br.com.xisp.models.Status.IN_DEV))
				inDev.add(story);
			if (story.getStatus().equals(
					br.com.xisp.models.Status.READY_FOR_TEST))
				readyTest.add(story);
			if (story.getStatus().equals(br.com.xisp.models.Status.IN_TEST))
				inTest.add(story);
			if (story.getStatus().equals(br.com.xisp.models.Status.FINISHED))
				finished.add(story);
		}
		
		//Recupera Todas as Iteracoes
		List<Interation> listIterations = 	this.interationRepository.showAllInterations(this.projectSession.getProject());
	
		
		result.include("iterations", listIterations);
		result.include("noStarted", noStarted);
		result.include("inDev", inDev);
		result.include("readyTest", readyTest);
		result.include("finished", finished);
		result.include("inTest", inTest);
	}

	private Interation getcurrentIteration() {
		return this.interationSession.getInteration();
	}

	/**
	 * Muda o status de um data estoria de usuario
	 * 
	 * @param story
	 * @param status
	 */
	@Get
	@Path("/stories/mudaStatus/{story.id}/{status.name}")
	public void mudaStatus(Story story, Status status) {
		// I know this code is bad
		ResultChangeStory r = new ResultChangeStory();
		r.setId(story.getId());
		r.setStatus(status.getName());

		Story us = repository.find(story.getId());

		History hist = new History();
		hist.setOrigin(us.getStatus().getStatus());
		hist.setDestiny(status.getName());
		hist.setModifyAd(new Date());

		hist.setStory(story);
		this.historyStoryRepository.add(hist);

		if (status.getName().equals("em_dev")) {
			us.setStatus(br.com.xisp.models.Status.IN_DEV);
			us.setStartedAt(new Date());
		}
		if (status.getName().equals("pronta_para_dev")) {
			us.setStatus(br.com.xisp.models.Status.NOSTARTED);
		}
		if (status.getName().equals("pronta_para_testes")) {
			us.setStatus(br.com.xisp.models.Status.READY_FOR_TEST);
		}
		if (status.getName().equals("em_testes")) {
			us.setStatus(br.com.xisp.models.Status.IN_TEST);
		}
		if (status.getName().equals("finalizadas")) {
			us.setStatus(br.com.xisp.models.Status.FINISHED);
		}

		r.setQtdStories(10);
		System.out.println("muda status " + story.getId() + " Status: "
				+ status.getName());
		result.use(json()).from(r).serialize();
	}

	@Path("/stories/history/{story.id}")
	public void findStoryHistory(Story story) {
		Story s = this.repository.find(story.getId());
		result.include("story", s);

	}

	@Path("/stories/{story.id}")
	@Delete
	public void remove(Story story) throws Exception {
		Story s = this.repository.find(story.getId());
		this.repository.remove(s);
		result.include("success", true);
		result.include("message",
				"<strong>Sucesso!</strong> Story deletada com sucesso.");
		result.use(logic()).forwardTo(StoriesController.class).index(this.projectSession.getProject());
	}
	
	/**
	 * 
	 * @param update_value 
	 * @param element_id
	 * @param atributo
	 * 
	 * update_value novo nome do atributo
	 * element_id id da estoria a ser atualizada
	 * atributo = atributo da estoria a ser atualizado
	 * 
	 * M�todo chamado via ajax, resultado devolvido para a view pelo updateDescription.jsp
	 */
	@Path("/stories/updateDescription")
	public void updateDescription(String newvalue, Long elementid, String atributo){
		System.out.println(newvalue + " " + elementid + " " + atributo);
		
		//Chama m�todo de atualizao montando a query dinamicamente
		Story story = this.repository.find(elementid);
		story.setDescription(newvalue);
		this.repository.update(story);
		
		result.include("storyContent", newvalue);
	}
	
	/**
	 * 
	 * @param update_value 
	 * @param element_id
	 * @param atributo
	 * 
	 * update_value novo nome do atributo
	 * element_id id da estoria a ser atualizada
	 * atributo = atributo da estoria a ser atualizado
	 * 
	 * M�todo chamado via ajax, resultado devolvido para a view pelo update.jsp
	 */
	@Path("/stories/updateName")
	public void updateName(String newvalue, Long elementid, String atributo){
		System.out.println(newvalue + " " + elementid + " " + atributo);
		
		//Chama m�todo de atualizao montando a query dinamicamente
		Story story = this.repository.find(elementid);
		if(!newvalue.isEmpty())
			story.setName(newvalue);
		this.repository.update(story);
		if(!newvalue.isEmpty())
			result.include("storyContent", newvalue);
		else
			result.include("storyContent", story.getName());
	}
	

}
