package github.hangming.airdata.dao;

import java.util.List;

import github.hangming.airdata.dto.Pmdata;
import github.hangming.airdata.dto.Station;

public interface IPmDao {

	List<Station> findAll(); //����,�����浵

	Pmdata saveRealtimeData ( Pmdata data ); 
	
	Station findStationByName(String sido, String stationName); // ��ġ�� ���� ���� ã�´�
	/**
	 * �־��� �����ҿ��� ������ ���������� ��ȯ�մϴ�.
	 * @param stationSeq
	 * @return
	 */
	List<Pmdata> findDataByStation(Integer stationSeq); // �ش� �� ������ 24�ð� ���� �����͸� �����ش�.

	Station findStationBySeq(Integer stationSeq);  // �ش� �� ������ ���±����� (�ð��뺰��)�̼�������ġ�� ������

	List<Pmdata> findRealtimeDataByRegion(String sido);//�� �õ��� �����ֱٵ����� ������
}
