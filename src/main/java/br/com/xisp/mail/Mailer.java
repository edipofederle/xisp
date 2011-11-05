package br.com.xisp.mail;

import br.com.caelum.vraptor.ioc.Component;


@Component
public interface Mailer {
	
	void sendMail(String to, String from, String subject, String message);

}
