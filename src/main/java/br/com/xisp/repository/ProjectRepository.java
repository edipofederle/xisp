package br.com.xisp.repository;

import java.util.List;

import br.com.xisp.models.Project;
import br.com.xisp.models.User;
/**
 * Repository Pattern para Entidade Project
 * 
 * @autor Edipo Luis Federle <edipofedele@gmail.com>
 *
 */
public interface ProjectRepository extends BaseRepository<Project> {
	
	/**
	 * Carrega um object <code>Project</code><br />
	 * 
	 * @param project
	 * @return Project
	 */
	Project load(Project project);
	
	/**
	 * 
	 * Carrega um object <code>Project></code> pelo id</br>
	 * @param id
	 * @return Project
	 */
	Project get(Long id);
	
	/**
	 *Retorna uma lista de  Projects dado um <code>User</code><br/>
	 *
	 * @param user
	 * @return Project List.
	 */
	List<Project> showAll(User user);
	
	/**
	 * Retorna uma lista de Projects</br>
	 * 
	 * @return Project List.
	 */
	List<Project> showAll();
}
