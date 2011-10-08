package br.com.xisp.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Story {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	
	@ManyToOne
	private TypeStory typeStory;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.READY_FOR_DEV;
	
	@Enumerated(EnumType.STRING)
	private Complexity complexity;
	
	public static enum Complexity{
		LOW, MEDIUM, HIGH
	}
	
	@ManyToOne
	private User createdBy;
	
	@ManyToOne
	private Project project;

	@ManyToOne
	private Interation interation;
	
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public void markAsCompleted() {
		this.status = Status.FINISHED;
	}
	public Interation getInteration() {
		return interation;
	}
	public void setInteration(Interation interation) {
		this.interation = interation;
	}
	public TypeStory getTypeStory() {
		return typeStory;
	}
	public void setTypeStory(TypeStory typeStory) {
		this.typeStory = typeStory;
	}
	public Complexity getComplexity() {
		return complexity;
	}
	public void setComplexity(Complexity complexity) {
		this.complexity = complexity;
	}

}
