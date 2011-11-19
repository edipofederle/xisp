package br.com.xisp.persistence;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.xisp.models.TypeStory;
import br.com.xisp.repository.TypeStoryRepository;

@Component
public class TypeStoryDao implements TypeStoryRepository {
	
	private final Session session;
	/**
	 * Creates a new ClientDao.
	 *
	 * @param session hibernate session.
	 */
	public TypeStoryDao(Session session) {
		this.session = session;
	}


	public TypeStory find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void add(TypeStory t) {
		this.session.save(t);
		
	}

	public void update(TypeStory t) {
		// TODO Auto-generated method stub
		
	}

	public void remove(TypeStory t) throws SQLException, Exception {
		// TODO Auto-generated method stub
		
	}


	@SuppressWarnings("unchecked")
	public List<TypeStory> findAll() {
		return this.session.createQuery("from TypeStory").list();
	}

}
