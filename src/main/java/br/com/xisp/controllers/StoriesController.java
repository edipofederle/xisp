package br.com.xisp.controllers;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.xisp.models.AcceptenceTest;
import br.com.xisp.models.Interation;
import br.com.xisp.models.Project;
import br.com.xisp.models.Story;
import br.com.xisp.models.TypeStory;
import br.com.xisp.models.User;
import br.com.xisp.repository.AcceptenceTestRepository;
import br.com.xisp.repository.InteractionRepository;
import br.com.xisp.repository.ProjectRepository;
import br.com.xisp.repository.StoryRepository;
import br.com.xisp.repository.TypeStoryRepository;
import br.com.xisp.session.ProjectSession;
import br.com.xisp.session.UserSession;

/**
 * 
 * @author edipo
 *
 * Controller Responsavel por controlar a parte das Estorias de Usuario
 *
 */
@Resource
public class StoriesController {
	
	private StoryRepository repository;
	private Result result;
	private Project currentProject;
	private ProjectSession projectSession;
	private ProjectRepository projectRepository;
	private InteractionRepository interationRepository;
	private TypeStoryRepository typestoryRepository;
	private AcceptenceTestRepository acceptenceTestRepository;
	private User currentUser;
	private final Validator validator;
	
	public StoriesController(StoryRepository repository, ProjectRepository repositoryProject,
							 InteractionRepository interationRepository, TypeStoryRepository typestoryRepository,
		                     AcceptenceTestRepository acceptenceTestRepository, Result result, ProjectSession projectSession, UserSession user, Validator validator) {
		this.repository = repository;
		this.result = result;
		this.projectRepository = repositoryProject;
		this.projectSession = projectSession;
		this.interationRepository = interationRepository;
		this.typestoryRepository = typestoryRepository;
		this.acceptenceTestRepository = acceptenceTestRepository;
		this.currentUser = user.getUser();
		this.validator = validator;
	}
	
	@Path("/stories/{project.id}/index")
	@Get
	public void index(Project project) {
		List<Story> listStoriesNotFinished = new ArrayList<Story>();
		Project p = projectRepository.load(project);
		this.projectSession.setProject(p);
		this.currentProject = this.projectSession.getProject();
		if(this.currentProject == null){
			result.include("selectProjectBefore", "Selecione um projeto!");
			result.redirectTo(ErrorsController.class).index();
		}else{
			listStoriesNotFinished = repository.showAllStoriesNotFinished(this.currentProject);
		}
		result.include("project", p);
		result.include("unRelatedStories", this.repository.unrelatedStories(this.currentProject));
		result.include("listStoriesNotFinished", listStoriesNotFinished);
	}
	
	public void neww() throws Exception{
		List<Interation> listIterations = new ArrayList<Interation>();
		List<TypeStory> listTypes = new ArrayList<TypeStory>();
		try{
			listTypes = this.typestoryRepository.findAll();
		}catch (Exception e) {
			// TODO: handle exception
		}
		//Carrega Todas as Iteracoes de um dado Projecto
		try{
			listIterations = this.interationRepository.showAllInterations(projectSession.getProject());
		}catch (Exception e) {
			//TODO Logar
			//TODO Redirect 
		}
		result.include("types", listTypes);
		result.include("listIterations", listIterations);
	}
	
	@Path("/stories")
	@Post
	public void add(final Story story) throws Exception {
		story.setProject(projectSession.getProject());
		story.setCreatedBy(this.currentUser);
		AcceptenceTest at = new AcceptenceTest();
		at.setTest(story.getAcceptsTest());
		acceptenceTestRepository.add(at);
		story.setTest(at);
		repository.add(story);
		validator.onErrorUsePageOf(StoriesController.class).index(projectSession.getProject());
		result.redirectTo(StoriesController.class).neww();
	}
	
	/**
	 * Redireciona para a view onde se encontra o quadro com com as estorias em 
	 * seus diferentes niveis.
	 */
	@Path("/stories/board")
	public void board(){
		
		List<Story> listStoriesNotFinished = new ArrayList<Story>();
		List<Story> noStarted = new ArrayList<Story>();
		List<Story> inDev = new ArrayList<Story>();
		List<Story> readyTest = new ArrayList<Story>();
		List<Story> inTest = new ArrayList<Story>();
		List<Story> finished = new ArrayList<Story>();
		
		listStoriesNotFinished = repository.showAllStoriesNotFinished(projectSession.getProject());
		
		for (Story story : listStoriesNotFinished) {
			if(story.getStatus().equals(br.com.xisp.models.Status.NOSTARTED))
				noStarted.add(story);
			if(story.getStatus().equals(br.com.xisp.models.Status.IN_DEV))
				inDev.add(story);
			if(story.getStatus().equals(br.com.xisp.models.Status.READY_FOR_TEST))
				readyTest.add(story);
			if(story.getStatus().equals(br.com.xisp.models.Status.IN_TEST))
				inTest.add(story);
			if(story.getStatus().equals(br.com.xisp.models.Status.FINISHED))
				finished.add(story);
		}
		
		result.include("noStarted", noStarted);
		result.include("inDev", inDev);
		result.include("readyTest", readyTest);
		result.include("finished",finished);
		result.include("inTest", inTest);
	}
	
	/**
	 * Muda o status de um data estoria de usuario
	 * 
	 * @param story
	 * @param status
	 */
	@Get
	@Path("/stories/mudaStatus/{story.id}/{status.name}")
	public void mudaStatus(Story story, Status status){
		//I know this code is bad
		ResultChangeStory r = new ResultChangeStory();
		r.setId(story.getId());
		r.setStatus(status.getName());
		
		if(status.getName().equals("em_dev")){
			Story us = repository.find(story.getId());
			us.setStatus(br.com.xisp.models.Status.IN_DEV);
		}
		if(status.getName().equals("pronta_para_dev")){
			Story us = repository.find(story.getId());
			us.setStatus(br.com.xisp.models.Status.NOSTARTED);
		}
		if(status.getName().equals("pronta_para_testes")){
			Story us = repository.find(story.getId());
			us.setStatus(br.com.xisp.models.Status.READY_FOR_TEST);
		}
		if(status.getName().equals("em_testes")){
			Story us = repository.find(story.getId());
			us.setStatus(br.com.xisp.models.Status.IN_TEST);
		}
		if(status.getName().equals("finalizadas")){
			Story us = repository.find(story.getId());
			us.setStatus(br.com.xisp.models.Status.FINISHED);
		}
		
		r.setQtdStories(10);
		System.out.println("muda status " + story.getId() + " Status: " + status.getName());
		result.use(json()).from(r).serialize();
	}
}
