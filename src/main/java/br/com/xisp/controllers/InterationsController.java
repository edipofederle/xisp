package br.com.xisp.controllers;
import static br.com.caelum.vraptor.view.Results.json;
import static br.com.caelum.vraptor.view.Results.logic;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.xisp.models.Interation;
import br.com.xisp.models.Project;
import br.com.xisp.models.Story;
import br.com.xisp.repository.InteractionRepository;
import br.com.xisp.repository.ProjectRepository;
import br.com.xisp.repository.StoryRepository;
import br.com.xisp.session.InterationSessionImpl;
import br.com.xisp.session.ProjectSession;

@Resource
public class InterationsController {
	
	private InteractionRepository interationRepo;
	private ProjectSession projectSession;
	private StoryRepository storyRepo;
	private final Result result;
	private InterationSessionImpl sessionInteration;
	
	public InterationsController(InteractionRepository interationRepo, ProjectRepository projectRepo,
			ProjectSession projectSession,InterationSessionImpl sessionInteration,  StoryRepository storyRepo, Result result, Validator validator){
		this.interationRepo = interationRepo;
		this.storyRepo = storyRepo;
		this.projectSession = projectSession;
		this.result = result;
		this.sessionInteration = sessionInteration;
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
		
		try{
			this.interationRepo.add(interation);
		}catch (HibernateException e) {
			//TODO Logar erro
			result.include("iteracaoExists","Ja existe uma iteracao nesse intervalo de datas.");
			result.forwardTo(ErrorsController.class).index();
			result.forwardTo(ErrorsController.class).index();
		}
		result.include("successInteration", "Interacao " + interation.getName() + "criada com sucesso.");
		result.redirectTo(InterationsController.class).index();

	}
	
	
	@Path("/interations/{interation.id}")
	@Get
	public Interation show(Interation interation){
		Interation i = interationRepo.load(interation);
		Project project = projectSession.getProject();
		List<Story> stories = this.storyRepo.showAllStories(project, i);
		result.include("stories", stories);
		return i;
	}
	

	@Path("/interations/remove/{interation.id}")
	public void remove(Interation interation) throws Exception {
		this.interationRepo.remove(interation);
		result.include("success", true);
		result.include("message", "<strong>Sucesso!</strong> Iteracao deletada com sucesso.");
		result.use(logic()).redirectTo(InterationsController.class).index();
	}
	

	@Get
	@Path("/interations/setInteration/{interation.id}")
	public void setInteration(Interation interation){
		Interation inte = this.interationRepo.load(interation);
		SetInteration i = new SetInteration();
		i.setName(inte.getName());
		System.out.println("Setando iteracao " + interation.getId());
		this.sessionInteration.setInteration(inte);
		result.use(json()).from(i).serialize();
	}
	
	public void errorIteration(){
		
	}
	
}