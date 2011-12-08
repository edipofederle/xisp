package br.com.xisp.test.dao;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.com.xisp.models.Interation;
import br.com.xisp.models.Project;
import br.com.xisp.models.Status;
import br.com.xisp.models.Story;
import br.com.xisp.models.TypeStory;
import br.com.xisp.models.User;
import br.com.xisp.persistence.InterationDao;
import br.com.xisp.persistence.ProjectDao;
import br.com.xisp.persistence.StoryDao;
import br.com.xisp.persistence.TypeStoryDao;
import br.com.xisp.persistence.UserDao;

public class StoryDaoTest {

	private StoryDao storydao;
	private ProjectDao projectdao;
	private InterationDao interationdao;
	private TypeStoryDao typedao;
	private UserDao userdao;
	private Session session;

	@Before
	public void setUp() throws Exception {
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.configure().setProperty("hibernate.connection.url",
				"jdbc:mysql://127.0.0.1/xispTest");
		session = cfg.buildSessionFactory().openSession();
		session.beginTransaction();
		storydao = new StoryDao(session);
		projectdao = new ProjectDao(session);
		userdao = new UserDao(session);
		interationdao = new InterationDao(session);
		typedao = new TypeStoryDao(session);
	}

	@Test
	public void testShouldPersisteStoryOneFeature() throws SQLException, Exception {
		Story story = givenAStory("Create a Crud for Users", givenAProject(), Status.IN_DEV, givenAType());
		Story storyFound = storydao.find("Create a Crud for Users");
		assertThat(storyFound, is(story));
		Assert.assertEquals("Em Dev", storyFound.getStatus().getStatus());
		Assert.assertNull(storyFound.getInteration());
		Assert.assertEquals("Funcionalidade", storyFound.getTypeStory().getType());
	}
	
	@Ignore
	public void testShouldReturnAllFinishedStoriesFromProject() throws SQLException, Exception{
		Project p = givenAProject();
		givenFiveStories(p, Status.FINISHED);
		givenAStory("Story of Sea", p, Status.READY_FOR_TEST, givenAType());
		Assert.assertEquals(1, storydao.showAllStoriesNotFinished(p).size());
		Assert.assertEquals("Funcionalidade",storydao.showAllStoriesNotFinished(p).get(0).getTypeStory().getType());
	}

	@Test
	public void testShouldChangeStatusStory() throws SQLException, Exception{
		Story s = givenAStory("My Story", givenAProject(), Status.IN_DEV, givenAType());
		s.setStatus(Status.READY_FOR_TEST);
		storydao.update(s);
		Assert.assertEquals(Status.READY_FOR_TEST, storydao.find(s.getName()).getStatus());
	}
	
	@Test
	public void testShouldFinishStory() throws SQLException, Exception{
		Story s = givenAStory("My Second Story", givenAProject(), Status.IN_DEV, givenAType());
		s.markAsCompleted();
		storydao.update(s);
		Assert.assertEquals(Status.FINISHED, storydao.find(s.getName()).getStatus());
	}

	private void givenFiveStories(Project project, Status status) throws SQLException, Exception {
		for (int i = 0; i < 5; i++)
			givenAStory("Story " + i, project, status, givenAType());
	}

	private Project givenAProject() {
		Project project = new Project();
		project.setId(1L);
		project.setName("Test Project");
		project.setDescription("Description of Test Project");
		List<Interation> interations = new ArrayList<Interation>();
		interations.add(givenInteration2());
		projectdao.add(project);
		return project;
	}

	private User givenAUser() {
		User user = new User();
		user.setName("Edipo");
		user.setEmail("edipofederle@gmail.com");
		user.setPassword("edipo");
		userdao.add(user);
		return user;
	}
	
	private Story givenAStory(String name, Project project, Status status, TypeStory type) throws SQLException, Exception {
		Story story = new Story();
		story.setCreatedBy(givenAUser());
		story.setName(name);
		story.setDescription("Here Description for the user story " + name);
		story.setStatus(status);
		story.setProject(project);
		story.setTypeStory(type);
		storydao.add(story);
		return story;
	}

	private TypeStory givenAType() {
		TypeStory t = new TypeStory();
		t.setType("Funcionalidade");
		this.typedao.add(t);
		return t;
	}

	
	private Interation givenInteration2(){
		Interation interation = new Interation();
		interation.setName("Current Interation");
		interation.setStartDate(new Date());
		interation.setEndDate(new Date());
		interationdao.add(interation);
		this.session.flush();
		return interation;
	}

}