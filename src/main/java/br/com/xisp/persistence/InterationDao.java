package br.com.xisp.persistence;

import java.sql.SQLException;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.xisp.models.Client;
import br.com.xisp.models.Interation;
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


	public Interation find(String name) {
		String sql = "from Interation u where u.name = :name";
		Query query = this.session.createQuery(sql).setParameter("name", name);
		return (Interation) query.uniqueResult();
	}

	public void add(Interation t) {
		this.session.save(t);
	}

	public void update(Interation t) {
		// TODO Auto-generated method stub
		
	}

	public void remove(Interation t) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}

}
