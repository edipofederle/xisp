package br.com.xisp.test.dao;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.com.xisp.models.Interation;
import br.com.xisp.models.Project;
import br.com.xisp.persistence.InterationDao;
import br.com.xisp.persistence.ProjectDao;

public class InterationDaoTest {
	
	private InterationDao dao;
	private ProjectDao daop;
	private Session session;
	
	@Before
	public void setUp() throws Exception {
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.configure().setProperty("hibernate.connection.url", "jdbc:mysql://127.0.0.1/xispTest");
		session = cfg.buildSessionFactory().openSession();
		session.beginTransaction();
		dao = new InterationDao(session);
		daop = new ProjectDao(session);
	}
	
	@After
	public void down(){
		session.beginTransaction().commit();
	}
	
	
	@Test
	public void shouldPersisteInteraction(){
		clearInteraction();
		final Project p = givenAProject("Projecto de teste 0001");
		Interation i = givenAInteration("One 2", p);
		dao.add(i);
		this.session.flush();
		Assert.assertEquals("One 2",dao.find("One 2").getName());
	}
	
	@Test
	public void shouldReturnOnlyInteractioNoFinished(){
		clearInteraction();
		final Project p = givenAProject("Projecto de teste 100");
		Interation interation = new Interation();
		interation.setName("Algum nome");
		interation.setStartDate(new Date());
		Date minhaData = new Date();
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(minhaData);  
		// incrementa minha data mais um dias  
		calendar.add(Calendar.DAY_OF_MONTH, -1);  
		interation.setEndDate(minhaData);
		interation.setProject(p);
		interation.setDone(true);
		dao.add(interation);
		this.session.flush();
		Assert.assertEquals(0, dao.showAllInterations(p).size());
	}

	
	private void clearInteraction() {
		this.session.createQuery("DELETE FROM Interation").executeUpdate();
	}

	@Ignore
	public void shouldLoadOnlyInteractionOfGivenProject(){
		Date start = new Date();  

		Date end = new Date();  
		Calendar endc = Calendar.getInstance();  
		endc.setTime(end);
		// incrementa minha data mais sete dias  
		endc.add(Calendar.DAY_OF_MONTH, 32);
		
		Date start2 = new Date();  
		Calendar startc2 = Calendar.getInstance();  
		startc2.setTime(start2);
		// incrementa minha data mais sete dias  
		endc.add(Calendar.DAY_OF_MONTH, 13);
		
		Date end2 = new Date();  
		Calendar endc2 = Calendar.getInstance();  
		endc2.setTime(end2);
		// incrementa minha data mais sete dias  
		endc2.add(Calendar.DAY_OF_MONTH, 100);
		Project p1 = givenAProject("Projeto Teste 244");
		Interation i = givenAInteration("Nome Inte 13", givenAProject("Projeto Teste"), start, end);
		Assert.assertEquals("Nome Inte 2", dao.showAllInterations(p1).get(0).getName());
	}
	
	private Interation givenAInteration(String name, Project project, Date start, Date end) {
		Interation interation = new Interation();
		interation.setName(name);
		interation.setStartDate(start);
		interation.setEndDate(end);
		interation.setProject(project);
		dao.add(interation);
		return interation;
	}

	private Project givenAProject(String name) {
		Project project = new Project();
		project.setId(1L);
		project.setName(name);
		project.setDescription("Description of Test Project");
		daop.add(project);
		return project;
	}
	
	private Interation givenAInteration(String name, Project project) {
		Interation interation = new Interation();
		interation.setName(name);
		interation.setStartDate(new Date());
		Date minhaData = new Date();
		Calendar calendar = Calendar.getInstance();  
		calendar.add(Calendar.DAY_OF_MONTH, 2); 
		// incrementa minha data mais um dias  
		calendar.add(Calendar.DAY_OF_MONTH, 5);  
		interation.setEndDate(minhaData);
		interation.setProject(project);
		return interation;
	}
		
}