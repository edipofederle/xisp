package br.com.xisp.session;

import br.com.xisp.models.Project;

public interface ProjectSession {
	Project getProject();
	void setProject(Project project);
}
