package br.com.xisp.models;

/**
 * Enum responsavel pelos status de um Story.
 * 
 * @author edipo
 *
 */
public enum Status {

	//Declaraçoes das constante que mostram o status de um Story e tambem sua descricao.
	READY_FOR_DEV("RFD", "User Story pronta para desenvolvimento"),
	READY_FOR_TEST("RFT", "User Story pronta para testes"),
	IN_DEV("INDEV", "User Story em Desenvolvimento"),
	FINISHED("FINISHED","User Story concluida"),
	NOSTARTED("NOSTARTED", "User Story nao iniciada");
	
	private final String description;
	private final String status;
	
	/**
	 * Construtor do Enum
	 * @param description
	 */
	Status(String statusUs , String descriptionUs){
		this.description = descriptionUs;
		this.status = statusUs;
	}
	
	public String getDesc(){
		return description;
	}
	
	public String getStatus(){
		return status;
	}

}
