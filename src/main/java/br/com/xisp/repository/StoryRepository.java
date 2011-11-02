package br.com.xisp.repository;

import java.util.List;

import br.com.xisp.models.Interation;
import br.com.xisp.models.Project;
import br.com.xisp.models.Story;

public interface StoryRepository extends BaseRepository<Story>{
	List<Story> showAllStories(Project project, Interation iteration);
	List<Story> unrelatedStories(Project p);
	List<Story> showAllStories(Project p);
	List<Story> showAllStoriesNotFinished(Project projecte );
	Story find(Long id);
}
