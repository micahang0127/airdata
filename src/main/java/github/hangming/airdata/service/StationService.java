package github.hangming.airdata.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import github.hangming.airdata.dao.StationDao;
import github.hangming.airdata.dto.Station;

@Service
public class StationService {

	@Inject StationDao stationDao ;
	
	/**
	 * ���� �� �����Ҹ� �˻���
	 * @param region
	 * @return
	 */
	public List<Station> findByRegion(String region) {
		return stationDao.findByRegion(region);
	}

}
