package br.com.xisp.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.xisp.models.Client;
import br.com.xisp.repository.ClientRepository;

@Component
public class ClientDao implements ClientRepository {
	
	private final Session session;
	/**
	 * Creates a new ClientDao.
	 *
	 * @param session hibernate session.
	 */
	public ClientDao(Session session) {
		this.session = session;
	}

	public Client find(String name) {
		String sql = "from Client u where u.name = :name";
		Query query = this.session.createQuery(sql).setParameter("name", name);
		return (Client) query.uniqueResult();
	}

	public void add(Client t) {
		this.session.save(t);		
	}

	public void update(Client t) {
		this.session.update(t);
	}

	public void remove(Client t) throws Exception{
		 try{
		  this.session.delete(t);
		  this.session.flush();
		 }catch (Exception e) {
		  throw e;
		 }
		}
	
	public Client load(Client client) {
		return (Client) session.get(Client.class, client.getId());
	}

	@SuppressWarnings("unchecked")
	public List<Client> showAll() {
		return this.session.createQuery("from Client").list();
	}
	
}
