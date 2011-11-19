package br.com.xisp.persistence;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.xisp.models.Interation;
import br.com.xisp.models.Project;
import br.com.xisp.repository.InteractionRepository;

@Component
public class InterationDao implements InteractionRepository {
	
	private final Session session;
	/**
	 * Creates a new InterationDao.
	 *
	 * @param session hibernate session.
	 */
	public InterationDao(Session session) {
		this.session = session;
	}

	public Interation find(Long id) {
		return (Interation) session.get(Interation.class, id);
	}
	
	public Interation loadById(Long id) {
		String sql = "from Interation u where u.id = :id";
		Query query = this.session.createQuery(sql).setParameter("id", id);
		return (Interation) query.uniqueResult();
	}
	

	public void add(Interation t) {
		this.session.save(t);
	}

	public void update(Interation t) {
		this.session.update(t);
		this.session.flush();
		
	}

	public void remove(Interation t) throws SQLException, Exception {
		this.session.delete(t);
	}
	
	/**
	 * Retorna todas as iteracoes ainda nao finalizadas
	 * 
	 * @return List<Iterations>
	 */
	@SuppressWarnings("unchecked")
	public List<Interation> showAllInterations(Project project) {
		return this.session.createQuery("from Interation i where i.project = :project").setParameter("project", project).list();		
	}

	public Interation load(Interation interation) {
		return (Interation) session.get(Interation.class, interation.getId());
	}

}
