package github.hangming.airdata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import github.hangming.airdata.dto.Pmdata;
import github.hangming.airdata.dto.Station;

@Repository
public class PmDao implements IPmDao {

	@Autowired
	private SqlSession session;
	
	@Override
	public List<Station> findAll() {
		return session.selectList("StationMapper.findAll");
	}
	
	/**
	 * db에 데이터가 들어갔으면 들어간 데이터를 반환함.
	 * 이미 존재하면 null을 반환함
	 */
	public Pmdata saveRealtimeData ( Pmdata data ) {
		
		Pmdata existing = session.selectOne("StationMapper.findRealTimeData", data);
		
		// TODO bug 3
		if ( existing != null ) {
			return null;
		} else {
			// TODO bug 5
			session.insert("StationMapper.insertRealTimeData", data);
			return data;
		}
	}

	@Override
	public Station findStationByName(String sido, String stationName) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("sido", sido);
		param.put("name", stationName);
		Station satation = session.selectOne("StationMapper.findStationByName", param);
		
		return satation;
	}

	@Override
	public List<Pmdata> findDataByStation(Integer stationSeq) {
		return session.selectList("StationMapper.findDataByStation",stationSeq);
	}

	@Override
	public Station findStationBySeq(Integer stationSeq) {
		
		return session.selectOne("StationMapper.findStationBySeq", stationSeq);
	}

	@Override
	public List<Pmdata> findRealtimeDataByRegion(String sido) {
		return session.selectList("StationMapper.findRealtimeDataByRegion", sido);
	}

	@Override
	public List<Pmdata> findMainRealtimeAvg() {
		return session.selectList("StationMapper.findMainRealtimeAvg");
	}
	

}
