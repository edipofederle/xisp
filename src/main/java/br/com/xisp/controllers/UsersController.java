package br.com.xisp.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.xisp.models.User;
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
	
	private boolean isUserDuplicate(User user){
		boolean isDuplicate = repository.isDuplicate(user.getName());
		return isDuplicate;
	}
	
	
}
