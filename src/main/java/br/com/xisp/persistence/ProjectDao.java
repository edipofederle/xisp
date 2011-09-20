package br.com.xisp.persistence;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.xisp.models.Project;
import br.com.xisp.models.User;
import br.com.xisp.repository.ProjectRepository;

@Component
public class ProjectDao implements ProjectRepository {
	
	private final Session session;
	/**
	 * Creates a new ProjectDao.
	 *
	 * @param session hibernate session.
	 */
	public ProjectDao(Session session) {
		this.session = session;
	}

	public Project find(String name) {
		String sql = "from Project u where u.name = :name";
		Query query = this.session.createQuery(sql).setParameter("name", name);
		return (Project) query.uniqueResult();
	}

	public void add(Project t) {
		this.session.save(t);
	}

	public void update(Project t) {
		this.session.update(t);
	}

	public void remove(Project t) {
		this.session.delete(t);		
	}

	@SuppressWarnings("unchecked")
	public List<Project> showAll(User user) {
		return this.session.createQuery("from Project p where p.owner = :user or :user in elements(p.users)")
                .setParameter("user", user).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Project> showAll() {
		return this.session.createQuery("from Project").list();
	}

	public Project load(Project project) {
		return (Project) session.get(Project.class, project.getId());
	}


	public Project get(Long id) {
		return (Project) session.get(Project.class, id);
	}

}