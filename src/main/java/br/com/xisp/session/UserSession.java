package br.com.xisp.session;

import br.com.xisp.models.User;

public interface UserSession {
	public void setUser(User user);
	public User getUser();
}
