package br.com.xisp.repository;

import java.util.List;

import br.com.xisp.models.Project;
import br.com.xisp.models.Story;

public interface StoryRepository extends BaseRepository<Story>{
	List<Story> showAllStoriesNotFinished(Project project);
}
