package br.com.xisp.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entidade para historico de mundan�as de Status das Estorias de Usuarios
 * @author edipo
 *
 */

@Entity
public class History implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7011210228936788598L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String origin;
	private String destiny;
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyAd;

	@ManyToOne(optional=true)
	@JoinColumn
	private Story story;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestiny() {
		return destiny;
	}

	public void setDestiny(String destiny) {
		this.destiny = destiny;
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	public Date getModifyAd() {
		return modifyAd;
	}

	public void setModifyAd(Date modifyAd) {
		this.modifyAd = modifyAd;
	}
	
	
	
}