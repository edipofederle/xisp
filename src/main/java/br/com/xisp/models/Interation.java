package br.com.xisp.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Entidade que representa uma intera�ao de um projeto XP
 * 
 * @author edipo
 * 
 */

@Entity
public class Interation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Project project;

	@ManyToOne
	private Relyase relyase;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Transient
	private int days;

	private String name;
	private boolean done;

	private boolean current;
	
	private boolean hasRelease;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCurrent() {
		if (this.startDate != null
				&& this.startDate.compareTo(new Date()) <= 0
				&& (this.endDate == null || this.endDate.compareTo(new Date()) >= 0)) {
			return true;
		} else {
			return false;
		}
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	// TODO final tem que ser o dia do endDate+ 1 FIX
	public boolean isDone() {
		try {
			if (!this.endDate.equals(null))
				return true;
		} catch (NullPointerException e) {
			return false;
		}
		return false;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Relyase getRelyase() {
		return relyase;
	}

	public void setRelyase(Relyase relyase) {
		this.relyase = relyase;
	}

	public boolean isHasReleas() {
		return hasRelease;
	}

	public void setHasReleas(boolean hasReleas) {
		this.hasRelease = hasReleas;
	}
	
}
