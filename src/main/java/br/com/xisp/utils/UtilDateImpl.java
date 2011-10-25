package br.com.xisp.utils;

import java.util.Date;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class UtilDateImpl implements UtilDate {

	public Date currentDate() {
		return new Date();
	}
	
}