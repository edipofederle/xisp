package br.com.xisp.test.models;

import junit.framework.Assert;

import org.junit.Test;

import br.com.xisp.models.Status;

public class StatusTest {
	
	/**
	 * Metodo que testa o Enum Status
	 */
	@Test
	public void shouldReturnAllStatusOfAUs(){
		for(Status status : Status.values()){
			Assert.assertNotNull(status.getDesc());
		}
	}
	
	@Test
	public void shouldReturnStatusOfEnumRFD(){
		String statusDesc = Status.READY_FOR_DEV.getDesc();
		String status = Status.READY_FOR_DEV.getStatus();
		Assert.assertEquals("User Story pronta para desenvolvimento", statusDesc);
		Assert.assertEquals("RFD", status);
	}
	
	
	@Test
	public void shouldReturnStatusOfEnumRFT(){
		String statusDesc = Status.READY_FOR_TEST.getDesc();
		String status = Status.READY_FOR_TEST.getStatus();
		Assert.assertEquals("User Story pronta para testes", statusDesc);
		Assert.assertEquals("RFT", status);
	}
	
	@Test
	public void shouldReturnStatusOfEnumFinished(){
		String statusDesc = Status.FINISHED.getDesc();
		String status = Status.FINISHED.getStatus();
		Assert.assertEquals("User Story concluida", statusDesc);
		Assert.assertEquals("FINISHED", status);
	}
	
}
