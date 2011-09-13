package br.com.xisp.controllers;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.xisp.models.Client;
import br.com.xisp.repository.ClientRepository;

@Resource
public class ClientsController {
	
	private ClientRepository repo;
	private final Validator validator;
	private final Result result;
	
	public ClientsController(ClientRepository repo, Validator validator, Result result){
		this.repo = repo;
		this.validator = validator;
		this.result = result;
	}
	

	@Path("/clients/index")
	@Get
	public void index() {
		result.include("clients", repo.showAll());
	}
	
	public void neww() {}
	
	@Path("/clients")
	@Post
	public void add(final Client client) {
		//validateProject(project);
		//validator.onErrorUsePageOf(ProjectsController.class).newProject();
		repo.add(client);
		result.include("success", true);
		result.include("message", "<strong>Sucesso!</strong> Cliente criado com sucesso.");
		result.redirectTo(this).index();
	}
	
	@Path("/clients/{client.id}/edita")
	@Get
	public Client edita(Client client) {
		return repo.load(client);
	}
	

	@Path("/clients")
	@Put
	public void alterar(final Client client) {
		//validateProject(project);
		//validator.onErrorUsePageOf(ProjectsController.class).newProject();
		result.include("client", client);
		repo.update(client);
		result.include("success", true);
		result.include("message", "<strong>Sucesso!</strong> Cliente alterado com sucesso.");
		result.redirectTo(this).index();
	}
	
	@Path("/clients/{client.id}")
	@Delete
	public void remove(Client client) {
		repo.remove(client);
		result.include("success", true);
		result.include("message", "<strong>Sucesso!</strong> Cliente deletado com sucesso.");
		result.redirectTo(this).index();
	}
	
	


}
