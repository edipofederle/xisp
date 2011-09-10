package br.com.xisp.repository;

import java.util.List;

import br.com.xisp.models.User;

public interface UserRepository extends BaseRepository<User>{
	public User login(String email, String password) throws Exception;
	User load(User user);
	List<User> showAll();
	public boolean isDuplicate(String name);
}
