package br.com.xisp.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.xisp.models.Project;
import br.com.xisp.models.User;
import br.com.xisp.repository.UserRepository;

@Component
public class UserDao  implements UserRepository{

	private final Session session;
	/**
	 * Creates a new UserDao.
	 *
	 * @param session hibernate session.
	 */
	public UserDao(Session session) {
		this.session = session;
	}

	
	public User find(Long id) {
		return (User) session.get(User.class, id);
	}

	public void add(User user) {
		session.save(user);
	}

	public void update(User t) {
		this.session.update(t);
	}

	public void remove(User t) {
		this.session.delete(t);		
	}

	public User load(User user) {
		return (User) session.get(User.class, user.getId());
	}

	@SuppressWarnings("unchecked")
	public List<User> showAll() {
		return this.session.createCriteria(User.class).list();
	}

	public boolean isDuplicate(String name) {
		String sql = "from User u where u.name = :string";
		Query query = session.createQuery(sql).setParameter("string", name);
		User u = (User) query.uniqueResult();
		if(u == null)
			return false;
		else
			return true;
	}

	public User login(String email, String password) throws Exception {
		try {
			Query query = session.createQuery("from User where email = :email and password = :password");
			query.setParameter("email", email);
			query.setParameter("password", password);
			User u = (User) query.uniqueResult();
			if(u == null)
				throw new Exception("Usuario nao encontrado");
			return u;
		} catch (Exception e) {
			throw new Exception("N‹o foi poss’vel acessar o sistema!", e);
		}

	}


	@SuppressWarnings("unchecked")
	public List<User> usersWithoutProjects(Project project) {
		String hql = "select u from User u, Project p where p = :project " +
				"and u != p.owner and u not in elements (p.users)";
		Query query = session.createQuery(hql);
		query.setParameter("project", project);
		return query.list();
	}
}
