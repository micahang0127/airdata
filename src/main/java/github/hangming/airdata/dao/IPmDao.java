package github.hangming.airdata.dao;

import java.util.List;

import github.hangming.airdata.dto.Station;

public interface IPmDao {

	List<Station> findAll();
}
