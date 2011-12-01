package br.com.xisp.test.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.xisp.controllers.ReleasesController;
import br.com.xisp.models.Interation;
import br.com.xisp.models.Project;
import br.com.xisp.models.Relyase;
import br.com.xisp.repository.InteractionRepository;
import br.com.xisp.repository.ReleaseRepository;
import br.com.xisp.session.ProjectSession;

public class ReleaseControllerTest {
	
	private Mockery mockery;
	private MockResult result;
	private ReleaseRepository releaseRepository;
	private InteractionRepository interactionRepository;
	private ProjectSession sessionProject;
	private ReleasesController controller;
	
	@Before
	public void setUp() throws Exception {
		mockery = new Mockery();
		result = new MockResult();
		releaseRepository = mockery.mock(ReleaseRepository.class);
		interactionRepository = mockery.mock(InteractionRepository.class);
		sessionProject = mockery.mock(ProjectSession.class);
		
		controller = new ReleasesController(result, interactionRepository, releaseRepository, sessionProject);
	}
	
	@Test
	public void shouldTestIndex(){
		final Project project = givenAProject();
		final List<Interation> listIterations = new ArrayList<Interation>();
		Interation i1 = givenAInteration("teste", project);
		i1.setHasReleas(false);
		i1.setDone(true);
		listIterations.add(i1);
		
		mockery.checking(new Expectations() {
			{
				allowing(sessionProject).getProject();
				will(returnValue(project));
				one(interactionRepository).showAllInterations(project);
				will(returnValue(listIterations));
				one(releaseRepository).showAll(project);
			}
		});
		controller.index();
	}
	
	@Test
	public void shouldTestIndex1(){
		final Project project = givenAProject();
		final List<Interation> listIterations = new ArrayList<Interation>();
		Interation i1 = givenAInteration("teste", project);
		i1.setHasReleas(true);
		i1.setDone(false);
		listIterations.add(i1);
		
		mockery.checking(new Expectations() {
			{
				allowing(sessionProject).getProject();
				will(returnValue(project));
				one(interactionRepository).showAllInterations(project);
				will(returnValue(listIterations));
				one(releaseRepository).showAll(project);
			}
		});
		controller.index();
	}
	
	@Test
	public void shouldCreateARelease(){
		List<Long> listInterationsIds = new ArrayList<Long>();
		final Project p = givenAProject();
		final Interation i = givenAInteration("Teste",p);
		listInterationsIds.add(1L);
		listInterationsIds.add(2L);
		final Relyase r = givenRelease(i,p);
		mockery.checking(new Expectations() {
			{
				one(releaseRepository).add(r);
				allowing(interactionRepository).loadById(with(any(Long.class)));
				will(returnValue(i));
				allowing(interactionRepository).update(i);
				one(sessionProject).getProject();will(returnValue(p));
			}
		});
		controller.create(listInterationsIds, r);
	}
	
	private Relyase givenRelease(Interation i, Project p) {
		List<Interation> list = new ArrayList<Interation>();
		list.add(i);
		Relyase r = new Relyase();
		r.setIterations(list);
		r.setProject(p);
		r.setName("Release 1");
		r.setTag("1.10");
		return r;
	}

	private Project givenAProject() {
		Project project = new Project();
		project.setId(1L);
		project.setName("Test Project");
		project.setDescription("Description of Test Project");
		return project;
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
		interation.setHasReleas(false);
		interation.setDone(true);
		return interation;
	}

	
}
