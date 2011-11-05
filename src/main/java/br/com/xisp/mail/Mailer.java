package br.com.xisp.mail;


public interface Mailer {
	
	void sendMail(String to, String from, String subject, String message);

}
