package br.com.xisp.exps;

public class DuplicateUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2669011674871569302L;
	
	 public DuplicateUserException(String message){
	    super(message);
	  }
}
