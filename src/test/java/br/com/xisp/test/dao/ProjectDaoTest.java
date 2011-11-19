package br.com.xisp.test.dao;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Before;
import org.junit.Test;

import br.com.xisp.models.Project;
import br.com.xisp.models.User;
import br.com.xisp.persistence.ProjectDao;

public class ProjectDaoTest {
	
	private ProjectDao dao;
	private Session session;

	@Before
	public void setUp() throws Exception {
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.configure().setProperty("hibernate.connection.url", "jdbc:mysql://127.0.0.1/xispTest");
		session = cfg.buildSessionFactory().openSession();
		session.beginTransaction();
		dao = new ProjectDao(session);
	}

	private Project givenAProject() {
		Project project = new Project();
		project.setName("Project 2");
		project.setDescription("Description of Test Project");
		return project;
	}
	
	@Test
	public void shouldFindProjectGivenAID(){
		Project project = givenAProject();
		dao.add(project);
		Project projectFound = foundAProject(project);
		Assert.assertEquals("Project 2", projectFound.getName());
	}

	@Test
	public void shouldUpdateAProject(){
		Project project = givenAProject();
		dao.add(project);
		project.setName("Novo Nome");
		dao.update(project);
		Project projectFound = foundAProject(project);
		Assert.assertEquals("Novo Nome", projectFound.getName());
	}
	
	@Test
	public void shouldListAllProjects(){
		//clearDataBase();
		User user = givenAUser();
		for(int i=0; i < 5; i++){
			Project project = new Project();
			project.setId(new Long(i));
			project.setName("Name "+i);
			project.setDescription("Desription "+i);
			project.setOwner(user);
			dao.add(project);
		}
		//Assert.assertEquals(5,dao.showAll(user).size());
	}
	
	@Test
	public void shouldRemoveAProject(){
		Project project = givenAProject();
		dao.add(project);
		dao.remove(project);
		Project notFound = foundAProject(project);
		Assert.assertNull(notFound);
	}
	
	@Test
	public void shouldReturnAllProjectsGivenAUser(){
		Project project = givenAProject();
		User owner = givenAUser();
		project.setOwner(owner);
		dao.add(project);
		Assert.assertEquals("edipo2", dao.get(project.getId()).getOwner().getName());
	}
	
	@Test
	public void shouldGetAllUsersFromProject(){
		Project project = givenAProject();
		dao.add(project);
		Project ap = dao.load(project);
		User user = givenAUser();
		project.getUsers().add(user);
		Assert.assertEquals(1, ap.getUsers().size());
		Assert.assertEquals("edipo2", ap.getUsers().get(0).getName());
	}
	

	/*private void clearDataBase() {
		List<Project> projects = dao.listarTudo();
		for (Project project : projects) {
			session.delete(project);
		}
	}*/
	
	private Project foundAProject(Project project) {
		Project projectFound = dao.load(project);
		return projectFound;
	}
	
	private User givenAUser() {
		User user = new User();
		user.setName("edipo2");
		user.setPassword("edipo");
		user.setEmail("edipofederle@gmail.com");
		return user;
	}

}