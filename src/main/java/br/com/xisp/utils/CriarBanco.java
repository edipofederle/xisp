package br.com.xisp.utils;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class CriarBanco {
	public static void main(String[] args) {
		AnnotationConfiguration config = new AnnotationConfiguration();
		//config.addAnnotatedClass(Client.class);
		config.configure("hibernate_teste.cfg.xml");
		new SchemaExport(config).create(true, true);

	}
}
