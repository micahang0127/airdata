package github.hangming.airdata.dao;

import java.util.List;

import github.hangming.airdata.dto.Pmdata;
import github.hangming.airdata.dto.Station;

public interface IPmDao {

	List<Station> findAll(); //지역,위도경도

	Pmdata saveRealtimeData ( Pmdata data ); 
	
	Station findStationByName(String sido, String stationName); // 위치가 같은 곳을 찾는다
	/**
	 * 주어진 관측소에서 관측된 먼지값들을 반환합니다.
	 * @param stationSeq
	 * @return
	 */
	List<Pmdata> findDataByStation(Integer stationSeq); // 해당 한 지역의 24시간 동안 데이터를 보여준다.

	Station findStationBySeq(Integer stationSeq);  // 해당 한 지역의 여태까지의 (시간대별로)미세먼지수치를 보여줌

	List<Pmdata> findRealtimeDataByRegion(String sido);//각 시도별 가장최근데이터 보여줌
}
