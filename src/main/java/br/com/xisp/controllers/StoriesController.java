package br.com.xisp.controllers;

import org.springframework.jms.connection.SessionProxy;

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
		if(projectSession != null)
			this.currentProject = projectSession.getProject();
	}
	
	@Path("/stories/{project.id}/index")
	@Get
	public Project index(Project project) {
		Project p = projectRepository.load(project);
		if(this.currentProject == null){
			result.include("selectProjectBefore", "Selecione um projeto!");
			result.redirectTo(ErrorsController.class).index();
		}else{
			repository.showAllStoriesNotFinished(this.currentProject);
		}
		return p;
	}
	
	
	
}
