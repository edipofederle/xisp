package br.com.xisp.test.dao;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Before;
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
		cfg.configure().setProperty("hibernate.connection.url", "jdbc:mysql://127.0.0.1/xisp");
		session = cfg.buildSessionFactory().openSession();
		session.beginTransaction();
		dao = new InterationDao(session);
		daop = new ProjectDao(session);
	}
	
	@Test
	public void shouldPersisteInteraction(){
		final Project p = givenAProject("Projecto de teste 0");
		Interation i = givenAInteration("One", p);
		dao.add(i);
		Assert.assertEquals("One",dao.find("One").getName());
	}

	private Interation givenAInteration(String name, Project project) {
		Interation interation = new Interation();
		interation.setName(name);
		interation.setStartDate(new Date());
		Date minhaData = new Date();
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(minhaData);  
		// incrementa minha data mais um dias  
		calendar.add(Calendar.DAY_OF_MONTH, 1);  
		interation.setEndDate(minhaData);
		interation.setProject(project);
		return interation;
	}
	
	@Test
	public void shouldLoadOnlyInteractionOfGivenProject(){
		Date start = new Date();  

		Date end = new Date();  
		Calendar endc = Calendar.getInstance();  
		endc.setTime(end);
		// incrementa minha data mais sete dias  
		endc.add(Calendar.DAY_OF_MONTH, 10);
		
		Date start2 = new Date();  
		Calendar startc2 = Calendar.getInstance();  
		startc2.setTime(start2);
		// incrementa minha data mais sete dias  
		endc.add(Calendar.DAY_OF_MONTH, 11);
		
		Date end2 = new Date();  
		Calendar endc2 = Calendar.getInstance();  
		endc2.setTime(end2);
		// incrementa minha data mais sete dias  
		endc2.add(Calendar.DAY_OF_MONTH, 20);
		Project p1 = givenAProject("Projeto Teste 2");
		Interation i = givenAInteration("Nome Inte 1", givenAProject("Projeto Teste"), start, end);
		Interation i2 = givenAInteration("Nome Inte 2", p1, start2, end2);
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
	
}