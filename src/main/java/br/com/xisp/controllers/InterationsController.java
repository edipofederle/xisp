package br.com.xisp.controllers;

import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.xisp.models.Interation;
import br.com.xisp.models.Project;
import br.com.xisp.repository.InteractionRepository;
import br.com.xisp.repository.ProjectRepository;
import br.com.xisp.session.ProjectSession;
import br.com.xisp.utils.DateDifference;

@Resource
public class InterationsController {
	
	private InteractionRepository interationRepo;
	private ProjectRepository projectRepo;
	private ProjectSession projectSession;
	private final Result result;
	
	public InterationsController(InteractionRepository interationRepo, ProjectRepository projectRepo,
			ProjectSession projectSession, Result result){
		this.interationRepo = interationRepo;
		this.projectRepo = projectRepo;
		this.projectSession = projectSession;
		this.result = result;
	}
	
	/**
	 * Apesar Redireciona para interations/index.jsp
	 */
	public void index(){
		List<Interation> listai = interationRepo.showAllInterations(projectSession.getProject());
		
		int totalIterations = listai.size();
		int totalDone = 0;
		int totalNoDone = 0;
		
		for (Interation interation : listai) {
			int days = DateDifference.calculateDifference(interation.getEndDate(), interation.getStartDate());
			interation.setDays(days);
			if(interation.getEndDate().before(new Date())){
				totalDone += 1;
			}else{
				totalNoDone += 1;
			}
		}
		result.include("stats",  totalIterations + " iteracoes, " + totalDone + " finalizadas, " + totalNoDone + " nao finalizadas");
		result.include("interations",listai );
	}
	
	@Path("/interations")
	@Post
	public void save(Interation interation){
		Project project = projectSession.getProject();
		interation.setProject(project);
		this.interationRepo.add(interation);
		result.include("successInteration", "Interacao " + interation.getName() + "criada com sucesso.");
		result.redirectTo(InterationsController.class).index();

	}
	
	
	@Path("/interations/{interation.id}")
	@Get
	public Interation show(Interation interation){
		Interation i = interationRepo.load(interation);
		return i;
	}
	

	
}
