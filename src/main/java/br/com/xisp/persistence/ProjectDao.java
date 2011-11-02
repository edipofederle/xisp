package br.com.xisp.persistence;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.xisp.models.Project;
import br.com.xisp.models.User;
import br.com.xisp.repository.ProjectRepository;

/**
 * Implementacao para <code>ProjectRepository</code></br>
 * 
 * Esta classe é anotada como @Component para termos suporte a injeçao de dependencia entre esta classe
 * e outras que dependem dessa ou ProjectRepository.
 * 
 * @author Edipo Luis Federle <edipofedele@gmail.com>
 *
 */
@Component
public class ProjectDao implements ProjectRepository {
	
	//Sessao atual do hibernate
	private final Session session;

	/**
	 * Cria um nova instancai de ProjectDao<br />
	 * 
	 * @param session
	 */
	public ProjectDao(Session session) {
		this.session = session;
	}

	/**
	 * @param name 
	 * @return Project
	 * <br />
	 * Este metodo é responsavel por buscar um Projeto dado um nome
	 */
	public Project find(String name) {
		String sql = "from Project u where u.name = :name";
		Query query = this.session.createQuery(sql).setParameter("name", name);
		return (Project) query.uniqueResult();
	}

	/**
	 * @param project
	 * <br />
	 * Persiste um novo Project
	 */
	public void add(Project project) {
		this.session.save(project);
	}
	
	/**
	 * @param project
	 * <br/ >
	 * Atualiza uma entidade Project
	 */
	public void update(Project project) {
		this.session.update(project);
	}
	/**
	 * @param project
	 * <br />
	 * Remove um entidade project
	 */
	public void remove(Project project) {
	    this.session.createQuery("delete from Interation i where i.project = :project")
	    		.setParameter("project", project).executeUpdate();
	    this.session.delete(project);
	}
	
	/**
	 * @param user
	 * @return Project List.
	 * <br/>
	 * Dado um usuario devolve todos os projetos que esse usuario eh owner(Dono) ou projetos que o mesmo participa.
	 */
	@SuppressWarnings("unchecked")
	public List<Project> showAll(User user) {
		return this.session.createQuery("from Project p where p.owner = :user or :user in elements(p.users)")
                .setParameter("user", user).list();
	}
	
	/**
	 * Retorna todos os projetos cadastrados
	 */
	@SuppressWarnings("unchecked")
	public List<Project> showAll() {
		return this.session.createQuery("from Project").list();
	}
	
	/**
	 * @param project
	 * @return Project
	 * <br />
	 * Dado um project retorna o mesmo do banco.
	 */
	public Project load(Project project) {
		return (Project) session.get(Project.class, project.getId());
	}

	/**
	 * @param id
	 * @return Project
	 * <br />
	 * Dado um id retorna um projeto
	 */
	public Project get(Long id) {
		return (Project) session.get(Project.class, id);
	}

}