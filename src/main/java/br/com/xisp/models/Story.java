package br.com.xisp.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

@Entity
public class Story {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Type(type="text")
	private String description;
	
	@Temporal(TemporalType.DATE)
	private Date startedAt;
	
	@Temporal(TemporalType.DATE)
	private Date endAt;
	
	@ManyToOne
	private TypeStory typeStory;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.NOSTARTED;
	
	@Enumerated(EnumType.STRING)
	private Complexity complexity;
	
	/**
	 * Atributo usado somente para fazer um nested form na criacao da estorias
	 * Este atributo sera persitido na tabela de Acceptence Tests.
	 * Eu nao sei se tem uma maneira melhor de se fazer isso, se tiver
	 * faça um pull request :).
	 */
	@Transient
	private String acceptsTest;
	
	public static enum Complexity{
		LOW, MEDIUM, HIGH
	}
	
	@ManyToOne
	private User createdBy;
	
	@ManyToOne
	private User assignedTo;
	
	@OneToOne
	@Cascade(org.hibernate.annotations.CascadeType.DELETE)
	@JoinColumn
	private AcceptenceTest test;
	
	@ManyToOne
	private Project project;

	@ManyToOne
	private Interation interation;
	
	@OneToMany(mappedBy = "story", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<History> listHistoryStory;
	
	private Integer points;
		
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
		
		if(!status.equals(Status.FINISHED))
			this.endAt = null;
		if(this.startedAt == null)
			if(status.equals(Status.IN_DEV))
				this.startedAt = new Date();
		if(status.equals(Status.FINISHED))
			this.endAt = new Date();
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
	public Date getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}
	public Date getEndAt() {
		return endAt;
	}
	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}
	
	public String getAcceptsTest() {
		return acceptsTest;
	}
	public void setAcceptsTest(String acceptsTest) {
		this.acceptsTest = acceptsTest;
	}
	public AcceptenceTest getTest() {
		return test;
	}
	public void setTest(AcceptenceTest test) {
		this.test = test;
	}

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}
	public List<History> getListHistoryStory() {
		return listHistoryStory;
	}
	public void setListHistoryStory(List<History> listHistoryStory) {
		this.listHistoryStory = listHistoryStory;
	}
	public Integer getPonits() {
		return points;
	}
	public void setPonits(Integer ponits) {
		this.points = ponits;
	}
	

	
	
}