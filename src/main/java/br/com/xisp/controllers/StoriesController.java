package br.com.xisp.controllers;

import static br.com.caelum.vraptor.view.Results.*;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.xisp.models.Interation;
import br.com.xisp.models.Project;
import br.com.xisp.models.Story;
import br.com.xisp.models.TypeStory;
import br.com.xisp.models.User;
import br.com.xisp.repository.InteractionRepository;
import br.com.xisp.repository.ProjectRepository;
import br.com.xisp.repository.StoryRepository;
import br.com.xisp.repository.TypeStoryRepository;
import br.com.xisp.session.ProjectSession;
import br.com.xisp.session.UserSession;

@Resource
public class StoriesController {
	
	private StoryRepository repository;
	private Result result;
	private Project currentProject;
	private ProjectSession projectSession;
	private ProjectRepository projectRepository;
	private InteractionRepository interationRepository;
	private TypeStoryRepository typestoryRepository;
	private User currentUser;
	private final Validator validator;
	
	public StoriesController(StoryRepository repository, ProjectRepository repositoryProject, InteractionRepository interationRepository, TypeStoryRepository typestoryRepository,  Result result, ProjectSession projectSession, UserSession user, Validator validator) {
		this.repository = repository;
		this.result = result;
		this.projectRepository = repositoryProject;
		this.projectSession = projectSession;
		this.interationRepository = interationRepository;
		this.typestoryRepository = typestoryRepository;
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
		//Carrega os tipos do enum para serem mostrados no select na view
		List<String> types = new ArrayList<String>();
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
			//Logar
			//Redirect 
		}
		result.include("types", listTypes);
		result.include("listIterations", listIterations);
	}
	
	@Path("/stories")
	@Post
	public void add(final Story story) throws Exception {
		story.setProject(projectSession.getProject());
		story.setCreatedBy(this.currentUser);
		repository.add(story);
		validator.onErrorUsePageOf(StoriesController.class).index(projectSession.getProject());
		result.redirectTo(StoriesController.class).neww();
	}
	
	@Path("/stories/board")
	public void board(){
		
		List<Story> listStoriesNotFinished = new ArrayList<Story>();
		List<Story> noStarted = new ArrayList<Story>();
		List<Story> readyDev = new ArrayList<Story>();
		List<Story> readyTest = new ArrayList<Story>();
		listStoriesNotFinished = repository.showAllStoriesNotFinished(projectSession.getProject());
		
		for (Story story : listStoriesNotFinished) {
			if(story.getStatus().equals(br.com.xisp.models.Status.NOSTARTED))
				noStarted.add(story);
			if(story.getStatus().equals(br.com.xisp.models.Status.IN_DEV))
				readyDev.add(story);
			if(story.getStatus().equals(br.com.xisp.models.Status.READY_FOR_TEST))
				readyTest.add(story);
			
		}
		
		result.include("noStarted", noStarted);
		result.include("readyDev", readyDev);
		result.include("readyTest", readyTest);
		
	}
	
	@Get
	@Path("/stories/mudaStatus/{story.id}/{status.name}")
	public void mudaStatus(Story story, Status status){
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
		
		
		
		r.setQtdStories(10);
		System.out.println("muda status " + story.getId() + " Status: " + status.getName());
		result.use(json()).from(r).serialize();
	}
}
