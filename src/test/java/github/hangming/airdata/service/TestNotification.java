package github.hangming.airdata.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import github.hangming.airdata.dto.Pmdata;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestNotification {
	@Autowired
	EmailService es;
	
	@Test
	public void test() {
		Pmdata d0 = new Pmdata();
		d0.setTime("2018-07-04 10:00:00");
		d0.setPm10("55");
		d0.setPm25("30");
		d0.setRegion("서울");
		d0.setStation(360);
		d0.setStationName("중구");
		
		List<Pmdata> data = Arrays.asList(d0);
		es.sendNotification(data);
		
	}

}
