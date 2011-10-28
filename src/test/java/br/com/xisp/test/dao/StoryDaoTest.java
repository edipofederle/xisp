package br.com.xisp.test.dao;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Before;
import org.junit.Test;

import br.com.xisp.models.Interation;
import br.com.xisp.models.Project;
import br.com.xisp.models.Status;
import br.com.xisp.models.Story;
import br.com.xisp.models.Type;
import br.com.xisp.models.User;
import br.com.xisp.persistence.InterationDao;
import br.com.xisp.persistence.ProjectDao;
import br.com.xisp.persistence.StoryDao;
import br.com.xisp.persistence.UserDao;

public class StoryDaoTest {

	private StoryDao storydao;
	private ProjectDao projectdao;
	private InterationDao interationdao;
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
		interationdao = new InterationDao(session);
	}

	@Test
	public void testShouldPersisteStoryOneFeature() {
		Story story = givenAStory("Create a Crud for Users", givenAProject(), Status.READY_FOR_DEV, Type.FEATURE);
		Story storyFound = storydao.find("Create a Crud for Users");
		assertThat(storyFound, is(story));
		Assert.assertEquals("RFD", storyFound.getStatus().getStatus());
		Assert.assertNull(storyFound.getInteration());
		Assert.assertEquals("Funcionalidade", storyFound.getType().getType());
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
		givenAStory("Story of Sea", p, Status.READY_FOR_TEST, Type.BUG);
		Assert.assertEquals(1, storydao.showAllStoriesNotFinished(p).size());
		Assert.assertEquals("Bug",storydao.showAllStoriesNotFinished(p).get(0).getType().getType());
	}

	@Test
	public void testShouldChangeStatusStory(){
		Story s = givenAStory("My Story", givenAProject(), Status.READY_FOR_DEV, Type.FEATURE);
		s.setStatus(Status.READY_FOR_TEST);
		storydao.update(s);
		Assert.assertEquals(Status.READY_FOR_TEST, storydao.find(s.getName()).getStatus());
	}
	
	@Test
	public void testShouldFinishStory(){
		Story s = givenAStory("My Second Story", givenAProject(), Status.READY_FOR_DEV, Type.BUG);
		s.markAsCompleted();
		storydao.update(s);
		Assert.assertEquals(Status.FINISHED, storydao.find(s.getName()).getStatus());
	}
	
	@Test
	public void testShouldReturnAllStoriesNotBelongsToAnyIterations(){
		Project p = givenAProject();
		Story s1 = givenAStory("Story One", p, Status.READY_FOR_DEV, Type.IMPROVE);
		Story s2 = givenAStory("Story Two", p, Status.READY_FOR_DEV, Type.IMPROVE);
		Story s3 = givenAStory("Story Three", p, Status.READY_FOR_DEV, Type.IMPROVE);
		
		//Projecto tem um iteracao, nao deve aparecer nos unRelatedStories
		Story s4 = givenAStoryWithInteration("Story Four", p, Status.READY_FOR_DEV, givenInteration(p));
		
		List<Story> unRelatedStories = storydao.unrelatedStories(p);
		Assert.assertEquals(3, unRelatedStories.size());
		for (Story story : unRelatedStories) {
			Assert.assertNull(story.getInteration());
			Assert.assertEquals("Melhoria", story.getType().getType());
		}
		Assert.assertNotNull(s4.getInteration());		
	}

	private Story givenAStoryWithInteration(String string, Project p,
			Status readyForDev, Interation i) {
		Story story = new Story();
		story.setCreatedBy(givenAUser());
		story.setName("Story Story");
		story.setInteration(i);
		story.setDescription("Here Description for the user story Story");
		story.setStatus(Status.READY_FOR_DEV);
		story.setProject(p);
		storydao.add(story);
		return story;
			
	}

	private void givenFiveStories(Project project, Status status) {
		for (int i = 0; i < 5; i++)
			givenAStory("Story " + i, project, status, Type.BUG);
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
	
	private Story givenAStory(String name, Project project, Status status, Type type) {
		Story story = new Story();
		story.setCreatedBy(givenAUser());
		story.setName(name);
		story.setDescription("Here Description for the user story " + name);
		story.setStatus(status);
		story.setProject(project);
		story.setType(type);
		storydao.add(story);
		return story;
	}

	private Interation givenInteration(Project p){
		Interation interation = new Interation();
		interation.setName("Current Interation");
		interation.setProject(p);
		interation.setStartDate(new Date());
		interation.setEndDate(new Date());
		interationdao.add(interation);
		return interation;
	}

}