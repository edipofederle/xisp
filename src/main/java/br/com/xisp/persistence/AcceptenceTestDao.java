package br.com.xisp.persistence;

import java.sql.SQLException;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.xisp.models.AcceptenceTest;
import br.com.xisp.repository.AcceptenceTestRepository;

@Component
public class AcceptenceTestDao implements AcceptenceTestRepository{
	
	private final Session session;
	/**
	 * Creates a new AcceptenceTestDao.
	 *
	 * @param session hibernate session.
	 */
	public AcceptenceTestDao(Session session) {
		this.session = session;
	}


	public AcceptenceTest find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void add(AcceptenceTest t) {
		this.session.save(t);
		this.session.flush();
	}

	public void update(AcceptenceTest t) {
		// TODO Auto-generated method stub
		
	}

	public void remove(AcceptenceTest t) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}

}
