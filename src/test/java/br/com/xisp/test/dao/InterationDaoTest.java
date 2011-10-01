package br.com.xisp.test.dao;

import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.joda.time.LocalDate;
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
		Project p = givenAProject();
		Interation i = givenAInteration("One", p);
		dao.add(i);
		Assert.assertEquals("One",dao.find("One").getName());
	}
	

	private Interation givenAInteration(String name, Project project) {
		Interation interation = new Interation();
		interation.setName(name);
		interation.setStartDate(new LocalDate().minusDays(1));
		interation.setEndDate(new LocalDate().plusDays(2));
		interation.setProject(project);
		return interation;
	}

	private Project givenAProject() {
		Project project = new Project();
		project.setId(1L);
		project.setName("Test Project");
		project.setDescription("Description of Test Project");
		daop.add(project);
		return project;
	}
	
}