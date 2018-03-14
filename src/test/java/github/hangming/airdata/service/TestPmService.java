package github.hangming.airdata.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import github.hangming.airdata.dto.Pmdata;

public class TestPmService {

	@Test
	public void test() {
		PmService service = new PmService();
		List<Pmdata> data = service.loadSidoData("°­¿ø");
		for ( Pmdata pd : data) {
			System.out.println(pd);
		}
		System.out.println("done");
	}

}
