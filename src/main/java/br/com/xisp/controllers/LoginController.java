package br.com.xisp.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.xisp.models.User;
import br.com.xisp.repository.UserRepository;
import br.com.xisp.session.InterationSession;
import br.com.xisp.session.ProjectSession;
import br.com.xisp.session.UserSession;
/**
 * @author edipo
 * Classe controller responsavel pelo login, no seu construtor ela recebe um result(Vraptor), um userDao e uma
 * Session do hibernate.
 */
@Resource
public class LoginController {
	
	private Result result;
	private UserRepository userDao;
	private UserSession userSession;
	private InterationSession sessionInteretion;
	private ProjectSession sessionProject;
	
	public LoginController(Result result, UserRepository userDao, UserSession userSession, InterationSession sessionInteretion, ProjectSession sessionProject ) {
        this.result = result;
        this.userDao = userDao;
        this.userSession = userSession;
        this.sessionInteretion = sessionInteretion;
        this.sessionProject = sessionProject;
    }
	
	@Get
    @Path("/login/login")
    public void login() {}
	
	@Post
    @Path("/login/login")
    public void login(User user) {
		if(!user.getEmail().isEmpty() && !user.getPassword().isEmpty()){
	        try {
	        	User _user = userDao.login(user.getEmail(), user.getPassword());
	        	if(_user != null){
	        		userSession.setUser(_user);
	        		result.redirectTo(ProjectsController.class).index();
	        	}
	        } catch (Exception e) {
	            e.printStackTrace();
	            result.forwardTo(this).login();
	        }
		}else{
			result.forwardTo(this).login();
		}
    }
	
	 @Get
	    @Path("/login/logout")
	    public void logout() {
	        userSession.setUser(null);
	        sessionInteretion.setInteration(null);
	        sessionProject.setProject(null);
	        result.redirectTo(this).login();
	    }
}
