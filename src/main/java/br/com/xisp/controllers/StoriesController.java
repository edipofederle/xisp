package br.com.xisp.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.xisp.models.Project;
import br.com.xisp.repository.ProjectRepository;
import br.com.xisp.repository.StoryRepository;
import br.com.xisp.session.ProjectSession;

@Resource
public class StoriesController {
	
	private StoryRepository repository;
	private Result result;
	private Project currentProject;
	private ProjectSession projectSession;
	private ProjectRepository projectRepository;
	
	public StoriesController(StoryRepository repository, ProjectRepository repositoryProject,  Result result, ProjectSession projectSession) {
		this.repository = repository;
		this.result = result;
		this.projectRepository = repositoryProject;
		this.projectSession = projectSession;
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
}
