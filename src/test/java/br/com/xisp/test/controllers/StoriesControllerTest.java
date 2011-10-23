package br.com.xisp.test.controllers;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.xisp.controllers.StoriesController;
import br.com.xisp.models.Project;
import br.com.xisp.repository.AcceptenceTestRepository;
import br.com.xisp.repository.InteractionRepository;
import br.com.xisp.repository.ProjectRepository;
import br.com.xisp.repository.StoryRepository;
import br.com.xisp.repository.TypeStoryRepository;
import br.com.xisp.session.ProjectSession;
import br.com.xisp.session.UserSession;

public class StoriesControllerTest {
	
	private Mockery mockery;
	private StoryRepository repo;
	private MockResult result;
	private StoriesController controller;
	private ProjectSession projectSession;
	private ProjectRepository projectRepository;
	private InteractionRepository interationRepository;
	private TypeStoryRepository typestoryRepository;
	private AcceptenceTestRepository acceptenceTestRepository;
	private UserSession sessionUser;

	
	@Before
	public void setUp() throws Exception {
		this.mockery = new Mockery();
		this.repo = mockery.mock(StoryRepository.class);
		this.projectSession = mockery.mock(ProjectSession.class);
		this.projectRepository = mockery.mock(ProjectRepository.class);
		this.typestoryRepository = mockery.mock(TypeStoryRepository.class);
		this.acceptenceTestRepository = mockery.mock(AcceptenceTestRepository.class);
		this.sessionUser = mockery.mock(UserSession.class);
		this.interationRepository = mockery.mock(InteractionRepository.class);
		
		this.result = new MockResult();
		mockery.checking(new Expectations() {
			{
				one(projectRepository).load(with(any(Project.class)));
				//allowing(projectSession).getProject();
				one(sessionUser).getUser();
			}
		});
		this.controller = new StoriesController(repo, projectRepository, interationRepository, typestoryRepository, acceptenceTestRepository, result, projectSession, sessionUser, new MockValidator() );
	}
	
	/**
	 * Sem projecto setado na sessao
	 */
	@Test
	public void testNotShouldLoadAllNoDoneStories(){
		mockery.checking(new Expectations() {
			{
				one(projectRepository).load(with(any(Project.class)));will(returnValue(any(Project.class)));
				one(projectSession).setProject(with(any(Project.class)));
				one(projectSession).getProject();
				one(repo).unrelatedStories(with(any(Project.class)));
			}
		});
		Project project = givenAProject();
		willNeverLoadAllNoFoneStories(project);
		controller.index(project);
	}
	
	/**
	 * com projeto setado na sesao
	 */
	@Test
	public void testShouldLoadAllNoDoneStories(){
		Project project = givenAProject();
		willLoadAllNoDoneStories(project);
		controller.index(project);
	}
	
	@Test
	public void testShouldLoadObjectsToNewStory() throws Exception{
		mockery.checking(new Expectations() {
			{
				one(projectSession).getProject();
				one(typestoryRepository).findAll();
				one(interationRepository).showAllInterations(with(any(Project.class)));
			}
		});
		controller.neww();
	}
	
	private void willNeverLoadAllNoFoneStories(final Project project) {
		mockery.checking(new Expectations() {
			{
				one(projectSession).setProject(project);
				one(sessionUser).getUser();
				never(projectSession).getProject();
				never(repo).showAllStoriesNotFinished(project);
			}
		});
	}
	
	private void willLoadAllNoDoneStories(final Project project) {
		mockery.checking(new Expectations() {
			{
				one(repo).showAllStoriesNotFinished(with(any(Project.class)));
				one(projectRepository).load(with(any(Project.class)));
				will(returnValue(with(any(Project.class))));
				one(projectSession).getProject();
				one(projectSession).setProject(with(any(Project.class)));
				one(repo).unrelatedStories(with(any(Project.class)));
			}
		});
	}
	
	private Project givenAProject() {
		Project project = new Project();
		project.setId(1L);
		project.setName("Test Project");
		project.setDescription("Description of Test Project");
		return project;
	}
	
}
