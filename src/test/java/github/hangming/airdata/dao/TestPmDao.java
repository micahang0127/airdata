package github.hangming.airdata.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import github.hangming.airdata.dto.Pmdata;
import github.hangming.airdata.dto.Station;
/**
 * @author SangA
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestPmDao {

	@Autowired SqlSession session ;
	@Autowired IPmDao pmDao ;
	
	@Test
	@Ignore
	public void test_realtime_data() {
		Pmdata data = new Pmdata();
		data.setStation(360);
		data.setTime("2018-03-21 08:00:00");
		Pmdata existing = session.selectOne("StationMapper.findRealTimeData", data);
//		System.out.println(existing);
		assertNotNull( existing ); // null이 아니어야함 !
	}
	
	
	@Ignore @Test
	public void test_find_stations(){
		Station s = pmDao.findStationByName("서울", "중구");
		System.out.println(s);
	}
	
	@Test
	public void test_realtimedate_by_sido () {
		List<Pmdata> data = pmDao.findRealtimeDataByRegion("제주");
		for ( Pmdata each : data) {
			System.out.println(each);
		}
	}

}
