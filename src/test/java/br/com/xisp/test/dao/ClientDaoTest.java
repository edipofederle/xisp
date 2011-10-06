package br.com.xisp.test.dao;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.xisp.models.Client;
import br.com.xisp.persistence.ClientDao;

public class ClientDaoTest {
	
	private ClientDao dao;
	private Session session;
	
	@Before
	public void setUp() throws Exception {
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.configure().setProperty("hibernate.connection.url", "jdbc:mysql://127.0.0.1/xisp");
		session = cfg.buildSessionFactory().openSession();
		session.beginTransaction();
		dao = new ClientDao(session);
	}
	
	@Test
	public void shouldFindClientByName(){
		Client client = givenAClient();
		dao.add(client);
		Assert.assertEquals("ABC", dao.find("ABC").getName());
		Assert.assertEquals("ABC Street", dao.find("ABC").getEndereco());
	}
	
	@Test
	public void shouldUpdateAClient(){
		Client client = givenAClient();
		dao.add(client);
		client.setName("Novo Nome");
		dao.update(client);
		Client clientFound = foundAClient(client);
		Assert.assertEquals("Novo Nome", clientFound.getName());
	}
	
	@Test
	public void shouldRemoveAClient() throws Exception{
		Client client = givenAClient();
		dao.add(client);
		dao.remove(client);
		Client notFound = foundAClient(client);
		Assert.assertNull(notFound);
	}
	
	@Test
	public void shouldReturnAllClients() throws Exception{
		for(int i = 0; i < 10; i++){
			Client c = new Client();
			c.setName("Joao " + i);
			c.setEndereco("Endereco " + i);
			dao.add(c);
		}
		Assert.assertEquals(11, dao.showAll().size());
	}
	

	private Client foundAClient(Client client) {
		Client clientFound = dao.load(client);
		return clientFound;
	}

	private Client givenAClient() {
		Client c = new Client();
		c.setName("ABC");
		c.setEndereco("ABC Street");
		return c;
	}
	
}