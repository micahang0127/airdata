package github.hangming.airdata.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import github.hangming.airdata.dto.Pmdata;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestPmService {

	@Autowired
	PmService service ;
	
	@Test
	@Ignore
	public void test() {
		List<Pmdata> data = service.loadSidoData("°­¿ø");
		for ( Pmdata pd : data) {
			System.out.println(pd);
		}
		System.out.println("done");
	}
	
	@Test
	public void testLoadAll() {
		service.loadData();
	}

}
