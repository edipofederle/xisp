package br.com.xisp.repository;

import java.util.List;

import br.com.xisp.models.Project;
import br.com.xisp.models.User;

public interface ProjectRepository extends BaseRepository<Project> {
	Project load(Project project);
	Project get(Long id);
	List<Project> showAll(User user);
	List<Project> showAll();
}
