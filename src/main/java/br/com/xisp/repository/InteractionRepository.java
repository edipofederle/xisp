package br.com.xisp.repository;

import java.util.List;

import br.com.xisp.models.Interation;
import br.com.xisp.models.Project;

public interface InteractionRepository extends BaseRepository<Interation> {
	
	List<Interation> showAllInterations(Project project);
	Interation load(Interation interation);
	
}
