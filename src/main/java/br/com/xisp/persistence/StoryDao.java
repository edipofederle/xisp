package br.com.xisp.persistence;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.xisp.models.Project;
import br.com.xisp.models.Story;
import br.com.xisp.repository.StoryRepository;

@Component
public class StoryDao implements StoryRepository {
	
	//Sessao atual do hibernate
	private final Session session;

	public StoryDao(Session session) {
		this.session = session;
	}

	public Story find(String name) {
		String sql = "from Story u where u.name = :name";
		Query query = this.session.createQuery(sql).setParameter("name", name);
		return (Story) query.uniqueResult();
	}

	public void add(Story t) {
		this.session.save(t);
	}

	public void update(Story t) {
		this.session.update(t);
	}

	public void remove(Story t) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	public List<Story> showAllStoriesNotFinished(Project project ) {
		return this.session.createQuery("from Story s where s.project = :project and s.status != 'FINISHED'").setParameter("project", project).list();
	}

	@SuppressWarnings("unchecked")
	public List<Story> unrelatedStories(Project p) {
		String hql = "select u from Story u, Project p where p = :project and u.interation is null";
		Query query = session.createQuery(hql);
		query.setParameter("project", p);
		return query.list();
	}
	

}