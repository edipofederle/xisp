package br.com.xisp.test.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Before;
import org.junit.Test;

import br.com.xisp.models.History;
import br.com.xisp.models.Story;
import br.com.xisp.persistence.HistoryStoryDao;
import br.com.xisp.persistence.StoryDao;

public class HistoryStoryDaoTest {
	
	private HistoryStoryDao historydao;
	private StoryDao storydao;
	private Session session;
	
	@Before
	public void setUp() throws Exception {
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.configure().setProperty("hibernate.connection.url", "jdbc:mysql://127.0.0.1/xispTest");
		session = cfg.buildSessionFactory().openSession();
		session.beginTransaction();
		historydao = new HistoryStoryDao(session);
		storydao = new StoryDao(session);
	}
	
	@Test
	public void shouldTestShouldReturnHistoryForStory(){
		Story s  = givenAStory();
		History h = givenAHistory();
		h.setStory(s);
		this.historydao.add(h);
		List<History> history = historydao.findHistory(s);
	}

	private History givenAHistory() {
		History history = new History();
		history.setOrigin("Em Dev");
		history.setDestiny("Em Testes");
		return history;
	}
	
	private Story givenAStory(){
		Story story = new Story();
		story.setName("Name Story");
		story.setDescription("Description for User Stories");
		this.storydao.add(story);
		this.session.flush();
		return story;
	}
	
}