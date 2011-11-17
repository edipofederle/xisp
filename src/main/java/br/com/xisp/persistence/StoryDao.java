package br.com.xisp.persistence;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.xisp.models.Interation;
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

	public Story find(Long id) {
		return (Story) session.get(Story.class, id);
	}

	public void add(Story story) {
		this.session.save(story);
	}

	public void update(Story t) {
		this.session.update(t);
	}

	public void remove(Story story) throws SQLException, Exception {
		this.session.delete(story);
		
	}

	@SuppressWarnings("unchecked")
	public List<Story> showAllStories(Project project, Interation interation) {
		String hql = "select distinct s from Story s, Interation i where s.project = :project and s.interation = :interation";
		Query query = session.createQuery(hql);
		query.setParameter("project",project);
		query.setParameter("interation", interation);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Story> unrelatedStories(Project p) {
		String hql = "select u from Story u, Project p where p = :project and u.interation is null";
		Query query = session.createQuery(hql);
		query.setParameter("project", p);
		return query.list();
	}

	public Story find(String name) {
		String sql = "from Story u where u.name = :name";
		Query query = this.session.createQuery(sql).setParameter("name", name);
		return (Story) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Story> showAllStoriesNotFinished(Project project) {
		String hql = "select u from Story u, Project p where p = :project and u.status != FINISHED ";
		Query query = session.createQuery(hql);
		query.setParameter("project", project);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Story> showAllStories(Project p) {
		String hql = "select s from Story s where s.project = :project";
		Query query = session.createQuery(hql);
		query.setParameter("project",p);
		return query.list();
	}

}