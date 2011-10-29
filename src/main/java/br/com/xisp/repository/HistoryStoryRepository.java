package br.com.xisp.repository;
import java.util.List;

import br.com.xisp.models.History;
import br.com.xisp.models.Story;

public interface HistoryStoryRepository {

	void add(History historyStory);
	List<History> findHistory(Story s);
	
}