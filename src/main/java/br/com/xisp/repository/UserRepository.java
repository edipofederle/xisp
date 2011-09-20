package br.com.xisp.repository;

import java.util.List;

import br.com.xisp.models.Project;
import br.com.xisp.models.User;

public interface UserRepository extends BaseRepository<User>{
	public User login(String email, String password) throws Exception;
	User load(User user);
	List<User> showAll();
	List<User> usersWithoutProjects(Project project);
	public boolean isDuplicate(String name);
	
}
