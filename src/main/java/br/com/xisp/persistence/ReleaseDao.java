package br.com.xisp.persistence;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.xisp.models.Project;
import br.com.xisp.models.Relyase;
import br.com.xisp.repository.ReleaseRepository;

@Component
public class ReleaseDao implements ReleaseRepository {
	
	//Sessao atual do hibernate
	private final Session session;
	
	public ReleaseDao(Session session) {
		this.session = session;
	}

	public Relyase find(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void add(Relyase t) {
		this.session.save(t);
	}

	public void update(Relyase t) {
		// TODO Auto-generatedmethod stub
		
	}

	public void remove(Relyase t) throws SQLException, Exception {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unchecked")
	public List<Relyase> showAll(Project project) {
		return this.session.createQuery("from Relyase").list();
	}

}
