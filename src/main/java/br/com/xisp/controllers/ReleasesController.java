package br.com.xisp.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.xisp.models.Interation;
import br.com.xisp.models.Project;
import br.com.xisp.models.Relyase;
import br.com.xisp.repository.InteractionRepository;
import br.com.xisp.repository.ReleaseRepository;
import br.com.xisp.session.ProjectSession;

@Resource
public class ReleasesController {

	private final Result result;
	private final InteractionRepository interationRepo;
	private final ReleaseRepository releaseRepository;
	private final ProjectSession sessionProject;

	public ReleasesController(Result result,
			InteractionRepository interactionRepository,
			ReleaseRepository releaseRepository, ProjectSession sessionProject) {
		this.result = result;
		this.interationRepo = interactionRepository;
		this.releaseRepository = releaseRepository;
		this.sessionProject = sessionProject;
	}

	@Path("/releases/index")
	@Get
	public void index() {
		// Carrega todas as iteracoes Finalizadas
		List<Interation> interationsTemp = new ArrayList<Interation>();
		Project projectIntoSession = this.sessionProject.getProject();
		List<Interation> interations = this.interationRepo.showAllInterations(projectIntoSession);
		for (Interation interation : interations) {
			if(!interation.isHasReleas() && interation.isDone()){
				interationsTemp.add(interation);
			}
		}
		
		//Carrega lista Releases
		List<Relyase> listReleases = releaseRepository.showAll(this.sessionProject.getProject());
		result.include("interations", interationsTemp);
		result.include("releases", listReleases);
	}

	@Path("/releases/create")
	@Post
	public void create(List<Long> iteracao, Relyase release) throws SQLException, Exception {
		List<Interation> listInterations = new ArrayList<Interation>();
		this.releaseRepository.add(release);
		for (Long id : iteracao) {
			Interation i = this.interationRepo.loadById(id);
			i.setHasReleas(true);
			i.setRelyase(release);
			listInterations.add(i);
			this.interationRepo.update(i);
		}
		release.setIterations(listInterations);
		release.setProject(this.sessionProject.getProject());
		result.redirectTo(this).index();
		result.include("success", "Release Criada com sucesso");
	}

}