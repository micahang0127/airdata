package github.hangming.airdata.dao;

import java.util.List;

import github.hangming.airdata.dto.Station;

public interface IStationDao {

	List<Station> findAll();
	
	Station findByName ( String stationName );
}
