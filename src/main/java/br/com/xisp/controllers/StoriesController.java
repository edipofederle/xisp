package br.com.xisp.controllers;

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
import br.com.xisp.models.Type;
import br.com.xisp.models.TypeStory;
import br.com.xisp.models.User;
import br.com.xisp.repository.InteractionRepository;
import br.com.xisp.repository.ProjectRepository;
import br.com.xisp.repository.StoryRepository;
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
	private User currentUser;
	private final Validator validator;
	
	public StoriesController(StoryRepository repository, ProjectRepository repositoryProject, InteractionRepository interationRepository,  Result result, ProjectSession projectSession, UserSession user, Validator validator) {
		this.repository = repository;
		this.result = result;
		this.projectRepository = repositoryProject;
		this.projectSession = projectSession;
		this.interationRepository = interationRepository;
		this.currentUser = user.getUser();
		this.validator = validator;
	}
	
	@Path("/stories/{project.id}/index")
	@Get
	public void index(Project project) {
		Project p = projectRepository.load(project);
		this.projectSession.setProject(p);
		this.currentProject = this.projectSession.getProject();
		if(this.currentProject == null){
			result.include("selectProjectBefore", "Selecione um projeto!");
			result.redirectTo(ErrorsController.class).index();
		}else{
			repository.showAllStoriesNotFinished(this.currentProject);
		}
		result.include("project", p);
		result.include("unRelatedStories", this.repository.unrelatedStories(this.currentProject));
	}
	
	public void neww(){
		//Carrega os tipos do enum para serem mostrados no select na view
		List<String> types = new ArrayList<String>();
		List<Interation> listIterations = new ArrayList<Interation>();
		
		for (Type type : Type.values()) {
			types.add(type.getType());
		}
		
		//Carrega Todas as Iteracoes de um dado Projecto
		try{
			listIterations = this.interationRepository.showAllInterations(projectSession.getProject());
		}catch (Exception e) {
			// TODO: handle exception
		}
		result.include("types", types);
		result.include("listIterations", listIterations);
	}
	
	@Path("/stories")
	@Post
	public void add(final Story story) {
		story.setProject(projectSession.getProject());
		story.setCreatedBy(this.currentUser);
		TypeStory ts = new TypeStory();
		ts.setId(1L);
		ts.setType("Funcionalidade");
		story.setTypeStory(ts); //TODO ira vir da tabel StoryType
		repository.add(story);
		validator.onErrorUsePageOf(StoriesController.class).index(projectSession.getProject());
		result.redirectTo(StoriesController.class).neww();
	}
}
