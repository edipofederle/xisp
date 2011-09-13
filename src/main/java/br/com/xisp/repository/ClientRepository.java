package br.com.xisp.repository;

import java.util.List;

import br.com.xisp.models.Client;

public interface ClientRepository  extends BaseRepository<Client>{
	Client load(Client client);
	List<Client> showAll();
}
