package br.com.xisp.persistence;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.xisp.models.History;
import br.com.xisp.repository.HistoryStoryRepository;

@Component
public class HistoryStoryDao implements HistoryStoryRepository {

	
	private final Session session;
	/**
	 * Creates a new HistoryStoryDao.
	 *
	 * @param session hibernate session.
	 */
	public HistoryStoryDao(Session session) {
		this.session = session;
		this.session.flush();
	}
	
	public void add(History historyStory) {
		this.session.save(historyStory);
	}

}