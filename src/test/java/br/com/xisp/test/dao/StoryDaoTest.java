package br.com.xisp.test.dao;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Before;
import org.junit.Test;

import br.com.xisp.models.Project;
import br.com.xisp.models.Status;
import br.com.xisp.models.Story;
import br.com.xisp.models.User;
import br.com.xisp.persistence.ProjectDao;
import br.com.xisp.persistence.StoryDao;
import br.com.xisp.persistence.UserDao;

public class StoryDaoTest {

	private StoryDao storydao;
	private ProjectDao projectdao;
	private UserDao userdao;
	private Session session;

	@Before
	public void setUp() throws Exception {
		AnnotationConfiguration cfg = new AnnotationConfiguration();
		cfg.configure().setProperty("hibernate.connection.url",
				"jdbc:mysql://127.0.0.1/xisp");
		session = cfg.buildSessionFactory().openSession();
		session.beginTransaction();
		storydao = new StoryDao(session);
		projectdao = new ProjectDao(session);
		userdao = new UserDao(session);
	}

	@Test
	public void testShouldPersisteStoryOne() {
		Story story = givenAStory("Create a Crud for Users", givenAProject(), Status.READY_FOR_DEV);
		Story storyFound = storydao.find("Create a Crud for Users");
		assertThat(storyFound, is(story));
		Assert.assertEquals("RFD", storyFound.getStatus().getStatus());
	}

	@Test
	public void testShouldReturnAllNotDoneStoriesFromProject() {
		Project p = givenAProject();
		givenFiveStories(p, Status.READY_FOR_DEV);
		Assert.assertEquals(5, storydao.showAllStoriesNotFinished(p).size());
	}
	
	@Test
	public void testShouldReturnAllFinishedStoriesFromProject(){
		Project p = givenAProject();
		givenFiveStories(p, Status.FINISHED);
		givenAStory("Story of Sea", p, Status.READY_FOR_TEST);
		Assert.assertEquals(1, storydao.showAllStoriesNotFinished(p).size());
	}

	@Test
	public void testShouldChangeStatusStory(){
		Story s = givenAStory("My Story", givenAProject(), Status.READY_FOR_DEV);
		s.setStatus(Status.READY_FOR_TEST);
		storydao.update(s);
		Assert.assertEquals(Status.READY_FOR_TEST, storydao.find(s.getName()).getStatus());
	}
	
	@Test
	public void testShouldFinishStory(){
		Story s = givenAStory("My Second Story", givenAProject(), Status.READY_FOR_DEV);
		s.markAsCompleted();
		storydao.update(s);
		Assert.assertEquals(Status.FINISHED, storydao.find(s.getName()).getStatus());
	}

	private void givenFiveStories(Project project, Status status) {
		for (int i = 0; i < 5; i++)
			givenAStory("Story " + i, project, status);
	}

	private Project givenAProject() {
		Project project = new Project();
		project.setId(1L);
		project.setName("Test Project");
		project.setDescription("Description of Test Project");
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
	
	private Story givenAStory(String name, Project project, Status status) {
		Story story = new Story();
		story.setCreatedBy(givenAUser());
		story.setName(name);
		story.setDescription("Here Description for the user story " + name);
		story.setStatus(status);
		story.setProject(project);
		storydao.add(story);
		return story;
	}

}