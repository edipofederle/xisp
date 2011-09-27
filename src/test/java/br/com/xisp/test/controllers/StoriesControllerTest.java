package br.com.xisp.test.controllers;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.xisp.controllers.StoriesController;
import br.com.xisp.models.Project;
import br.com.xisp.repository.ProjectRepository;
import br.com.xisp.repository.StoryRepository;
import br.com.xisp.session.ProjectSession;

public class StoriesControllerTest {
	
	private Mockery mockery;
	private StoryRepository repo;
	private MockResult result;
	private StoriesController controller;
	private ProjectSession projectSession;
	private ProjectRepository projectRepository;

	@Before
	public void setUp() throws Exception {
		this.mockery = new Mockery();
		this.repo = mockery.mock(StoryRepository.class);
		projectSession = mockery.mock(ProjectSession.class);
		this.projectRepository = mockery.mock(ProjectRepository.class);
		this.result = new MockResult();
		mockery.checking(new Expectations() {
			{
				one(projectRepository).load(with(any(Project.class)));
				allowing(projectSession).getProject();
			}
		});
		this.controller = new StoriesController(repo, projectRepository, result, projectSession);
	}
	
	/**
	 * Sem projecto setado na sessao
	 */
	@Test
	public void testNotShouldLoadAllNoDoneStories(){
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
		willLoadAllNoFoneStories();
		controller.index(project);
	}

	private void willNeverLoadAllNoFoneStories(final Project project) {
		mockery.checking(new Expectations() {
			{
				never(projectSession).getProject();
				never(repo).showAllStoriesNotFinished(project);
			}
		});
	}
	
	private void willLoadAllNoFoneStories() {
		mockery.checking(new Expectations() {
			{
				one(repo).showAllStoriesNotFinished(with(any(Project.class)));
				allowing(projectSession).getProject();
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
