package br.com.xisp.test.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.xisp.controllers.InterationsController;
import br.com.xisp.models.Interation;
import br.com.xisp.models.Project;
import br.com.xisp.repository.InteractionRepository;
import br.com.xisp.repository.ProjectRepository;
import br.com.xisp.repository.StoryRepository;
import br.com.xisp.session.InterationSession;
import br.com.xisp.session.ProjectSession;

public class InterationControllerTest {
	
	

	private Mockery mockery;
	private MockResult result;
	private ProjectRepository projectRepo;
	private InteractionRepository interationRepo;
	private ProjectSession projectSession;
	private InterationsController controller;
	private StoryRepository storyRepo;
	private Project project;
	private InterationSession sessionInteration;

	@Before
	public void setUp() throws Exception {
		project = givenAProject();
		mockery = new Mockery();
		result = new MockResult();
		projectRepo = mockery.mock(ProjectRepository.class);
		interationRepo = mockery.mock(InteractionRepository.class);
		storyRepo = mockery.mock(StoryRepository.class);
		projectSession = mockery.mock(ProjectSession.class);
		sessionInteration = mockery.mock(InterationSession.class);
		result = new MockResult();
		controller = new InterationsController(interationRepo,projectRepo,projectSession, sessionInteration, storyRepo, result, new MockValidator());
	}
	
	@Test
	public void shouldCreateInteration() throws Exception{
		final Interation interationOne = givenAInteration("One Interation", project);
		willCreateANewInteration(interationOne);
		mockery.checking(new Expectations() {
			{
				one(interationRepo).add(interationOne);
			}
		});
		controller.save(interationOne);
		Assert.assertNotNull(result.included("successInteration"));
	}
	
	@Test
	public void shouldTestIndexMethd(){
		final Project p = givenAProject();
		mockery.checking(new Expectations() {
			{
				one(projectSession).getProject();
				will(returnValue(p));
				one(interationRepo).showAllInterations(p);
				List<Interation> listIteracoes = new ArrayList<Interation>();
				Interation i = givenAInteration("Teste", p);
				Interation i2 = givenAInteration("Teste", p);
				i2.setEndDate(null);
				i.setEndDate(new Date());
				listIteracoes.add(i);
				listIteracoes.add(i2);
				will(returnValue(((listIteracoes))));
				
			}
		});
		controller.index();
	}
	
	@Test
	public void shouldShowIteration(){
		final Project p = givenAProject();
		final Interation i = givenAInteration("Iteracao", p);
		mockery.checking(new Expectations() {
			{
				one(interationRepo).load(i);will(returnValue(i));
				one(projectSession).getProject();will(returnValue(p));
				one(storyRepo).showAllStories(p,i);
			}
		});
		controller.show(i);
	}
	
	@Test
	public void shouldRemoveIteration() throws Exception{
		Project p = givenAProject();
		final Interation i = givenAInteration("teste", p);
		mockery.checking(new Expectations() {
			{
				one(interationRepo).remove(i);
			}
		});
		controller.remove(i);
	}
	
	@Test
	public void shouldNOtRemoveIterationSQLException() throws Exception{
		Project p = givenAProject();
		final Interation i = givenAInteration("teste", p);
		mockery.checking(new Expectations() {
			{
				one(interationRepo).remove(i);will(returnValue(SQLException.class));
			}
		});
		controller.remove(i);
	}
	
	@Test
	public void shouldSetInterationIntoSession(){
		final Interation i = givenAInteration("Teste", givenAProject());
		mockery.checking(new Expectations() {
			{
				one(interationRepo).load(with(any(Interation.class)));will(returnValue(i));
				one(sessionInteration).setInteration(i);
			}
		});
		controller.setInteration(i);
	}
	
	
	
	private void willCreateANewInteration(final Interation interation) {
		mockery.checking(new Expectations() {
			{
				one(projectSession).getProject();will(returnValue(project));
				one(sessionInteration).setInteration(interation);
			}
		});
	}

	private Interation givenAInteration(String name, Project project) {
		Interation interation = new Interation();
		interation.setName(name);
		Date minhaData = new Date();  
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(minhaData);
		// incrementa minha data mais sete dias  
		calendar.add(Calendar.DAY_OF_MONTH, 1); 
		interation.setStartDate(calendar.getTime());
		Date minhaDataend = new Date();  
		Calendar calendarend = Calendar.getInstance();  
		calendar.setTime(minhaDataend);
		// incrementa minha data mais sete dias  
		calendar.add(Calendar.DAY_OF_MONTH, 2); 
		interation.setEndDate(calendarend.getTime());
		interation.setProject(project);
		return interation;
	}

	private Project givenAProject() {
		Project project = new Project();
		project.setId(1L);
		project.setName("Test Project");
		project.setDescription("Description of Test Project");
		return project;
	}
	
}