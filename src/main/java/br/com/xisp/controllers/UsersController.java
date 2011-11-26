package br.com.xisp.controllers;
import static br.com.caelum.vraptor.view.Results.json;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.xisp.models.User;
import br.com.xisp.models.UserTemp;
import br.com.xisp.repository.UserRepository;

@Resource
public class UsersController {
	

	private final Validator validator;
	private final UserRepository repository;
	private final Result result;
	
	public UsersController(UserRepository repostiory, Validator validator, Result result) {
		this.repository = repostiory;
		this.validator = validator;
		this.result = result;
	}

	@Path("/users/index")
	@Get
	public void index() {
		result.include("users", repository.showAll());
	}
	
	
	@Path("/users")
	@Post
	public void add(final User user) {
		validator.checking(new Validations() {
			{
				that(!user.getName().isEmpty(), "erro", "validacao.user.name");
				that(!user.getEmail().isEmpty(), "erro", "validacao.user.email");
				that(!user.getPassword().isEmpty(), "erro", "validacao.user.pass");
				that(!isUserDuplicate(user), "erro", "validacao.user.duplicate");
			}
		});
		validator.onErrorUsePageOf(UsersController.class).newUser();
		repository.add(user);
		result.include("success", true);
		result.include("message", "<strong>Sucesso!</strong> Usu‡rio criado com sucesso.");
		result.redirectTo(this).index();
	}
	
	public void newUser() {}
	
	
	@Path("/users/{user.id}")
	@Get
	public User show(User user){
		return repository.load(user);
	}
	
	private boolean isUserDuplicate(User user){
		boolean isDuplicate = repository.isDuplicate(user.getName());
		return isDuplicate;
	}
	
	@Path("/users")
	@Put
	public void update(final User user) {
		//validator.onErrorUsePageOf(UserController.class).newProject();
		result.include("user", user);
		repository.update(user);
		result.include("success", true);
		result.include("message", "<strong>Sucesso!</strong> Usuario alterado com sucesso.");
		result.redirectTo(this).index();
	}
	
	@Path("/users/{user.id}")
	@Delete
	public void remove(User user){
		boolean error = true;
		
		try{
			repository.remove(user);
		}catch (Exception e) {
			error = true;
			result.include("erroDeleteUser","Ocorre um erro.");
			result.forwardTo(ErrorsController.class).index();
		}
		if(!error){
			result.include("success", true);
			result.include("message", "<strong>Sucesso!</strong> Usuario deletado com sucesso.");
			result.redirectTo(this).index();
		}
	}
	
	
	@Path("/users/{user.id}/edita")
	@Get
	public User edita(User user) {
		return repository.load(user);
	}
	
	@Path("/users/getAllUsers/")
	public void getAllUsers(){
		List<User> listUsers = new ArrayList<User>();
		List<UserTemp> listUsers2 = new ArrayList<UserTemp>();
		listUsers = this.repository.showAll();
		
		for (User user : listUsers) {
			UserTemp u = new UserTemp();
			u.setId(user.getId());
			u.setName(user.getName());
			listUsers2.add(u);
			
		}
		
		result.use(json()).from(listUsers2).serialize();
	}
		
}