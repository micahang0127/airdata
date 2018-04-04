package github.hangming.airdata.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import github.hangming.airdata.dto.Station;

@Repository
public class StationDao {
	
	
	@Autowired
	private SqlSession session;
	

	public List<Station> findByRegion(String region) {
		return session.selectList("StationMapper.findStationByRegion", region);
	}

}
