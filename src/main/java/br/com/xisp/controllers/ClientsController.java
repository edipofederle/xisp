package br.com.xisp.controllers;

import java.sql.SQLException;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
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
	public void add(final Client client) throws SQLException, Exception {
		validateClient(client);
		validator.onErrorUsePageOf(ClientsController.class).neww();
		repo.add(client);
		result.include("success", true);
		result.include("message", "<strong>Sucesso!</strong> Cliente criado com sucesso.");
		result.redirectTo(this).index();
	}
	
	private void validateClient(final Client client) {
		validator.checking(new Validations() {
			{
				that(!client.getName().isEmpty(), "erro",
						"validacao.client.name");
				that(!client.getEndereco().isEmpty(), "erro",
						"validacao.client.endereco");
			}
		});
		
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
	public void remove(Client client){
		boolean error = false;
		try{
			repo.remove(client);
		}catch (Exception e) {
			error = true;
			result.include("erroDeleteClient","Um erro ocorreu");
			result.forwardTo(ErrorsController.class).index();
		}
		if(!error){
			result.include("success", true);
			result.include("message", "<strong>Sucesso!</strong> Cliente deletado com sucesso.");
			result.redirectTo(this).index();
		}
	}
	
	


}
