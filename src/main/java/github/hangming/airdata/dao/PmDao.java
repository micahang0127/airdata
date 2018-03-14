package github.hangming.airdata.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import github.hangming.airdata.dto.Station;

@Repository
public class PmDao implements IPmDao {

	@Autowired
	private SqlSession session;
	
	@Override
	public List<Station> findAll() {
		return session.selectList("StationMapper.findAll");
	}
	

}
