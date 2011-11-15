package br.com.xisp.repository;

import java.util.List;

import br.com.xisp.models.Project;
import br.com.xisp.models.Relyase;

public interface ReleaseRepository extends BaseRepository<Relyase> {
		List<Relyase> showAll(Project project);
}