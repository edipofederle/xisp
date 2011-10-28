package br.com.xisp.models;

/**
 * Enum para indica o tipo Estoria de Usuario
 *  
 * @author edipo
 */
public enum Type {
	
	FEATURE("Funcionalidade"),
	BUG("Bug"),
	IMPROVE("Melhoria");
	
	private final String type;
	
	/**
	 * Construtor do Enum
	 * @param description
	 */
	Type(String type){
		this.type = type;
	}
	
	public String getType(){
		return this.type;
	}
	
}
