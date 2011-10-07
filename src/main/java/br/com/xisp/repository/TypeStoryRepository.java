package br.com.xisp.repository;

import java.util.List;

import br.com.xisp.models.TypeStory;

public interface TypeStoryRepository  extends BaseRepository<TypeStory>{
	List<TypeStory> findAll();
}
