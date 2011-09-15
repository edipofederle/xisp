package br.com.xisp.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Project implements Serializable {

	private static final long serialVersionUID = -8247738829229089316L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
    private User owner;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn
    private Client client;

	@ManyToMany
    private List<User> users;

	@Column
	private String name;
	private String description;
	
	//Atributo somente usado para poder setar os clients num project para ser usado no edit e new(COMO: select de clientes)
	@Transient
	private List<Client> listaClients;

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
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public List<Client> getListaClients() {
		return listaClients;
	}
	public void setListaClients(List<Client> listaClients) {
		this.listaClients = listaClients;
	}
	
	
	
}

