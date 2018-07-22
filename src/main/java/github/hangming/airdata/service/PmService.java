package github.hangming.airdata.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import github.hangming.airdata.dao.IPmDao;
import github.hangming.airdata.dto.Pmdata;
import github.hangming.airdata.dto.Station;
import github.hangming.airdata.web.HomeController;

@Service
public class PmService {

	private static final Logger logger = LoggerFactory.getLogger(PmService.class);
	
	@Autowired
	IPmDao pmDao;
	@Autowired 
	AirApiService airApiService;

	@Autowired
	EmailService emailService ;
	
	
	int idx = 0;
	
	@Scheduled(cron="0 10,20,30,40 * * * ?")
	//              sec min        hour date, month  

	
	public void updatePmData() {
		System.out.println("do job!!");
		loadData();
	}
	
	/**
	 * xml ������ �о�鿩�� db�� insert �ϴ� �۾����� ���⼭ ������
	 */
	public void loadData () {
		
		String [] sido = "����, �λ�, �뱸, ��õ, ����, ����, ���, ���, ����, ���, �泲, ����, ����, ���, �泲, ����, ����".split(", ");
		for ( String name : sido ) {
			// 1. 
			List<Pmdata> data = loadSidoData(name);
			// 2. ����ڵ鿡�� ���������� ������ ������ �̸��Ϸ� ����!!
			emailService.sendNotification( data );
		}
	}
	
	public List<Pmdata> loadSidoData ( String sido ) {
		

		Document doc = airApiService.load( sido, idx );
		
	
			Elements rCode = doc.select("header > resultCode"); 
				/* ���� �����ڵ忡�� ������. 
				
				
				 * ERROR: org.springframework.scheduling.support.TaskUtils$LoggingErrorHandler - Unexpected error occurred in scheduled task.
					org.jsoup.select.Selector$SelectorParseException: Could not parse query '': unexpected token at '' 
				
					
					=>>  �ذ�   ("header > resultCode ") <X> �ڡڡ�  resultCode �� ���� �ϸ� �ȵ� !!!!!!! 
				*/
			
			
				// TODO �ϳ��� �޼ҵ尡 �� ���� ���� �ϰ� ���� . �̷��� �ϸ� ���߿� ������!
				// API �ѵ� �ʰ� ���� ���� ������ AirApiService ���� ó���ϰ� �� Ŭ���������� ������ �ϴ���� ���丸 ó���ϸ� ��
			
		
			
			System.out.println("Ȯ�� ťťť");
			if( !(rCode.text().equals("00"))) {
			
				idx += 1;
				System.out.println("PMService ��° key Ȯ��00 if�� �� ����");
				
				doc = airApiService.load( sido, idx );
				idx = 0;
				
				/* �ڡڡ� ���� ����� Ŭ����.java �ڵ尡 �� �۵��ϰ� �ִ��� Ȯ���� �� ����. ���� http://hyeok7524.tistory.com/13  */
				logger.debug("===> api key replaced!");  				
		
			}else{ 
				System.out.println("PmService ���� Ȯ��"); 
			}
			
			
			
			Elements item = doc.select("body > items > item");
			System.out.println("���⵵ Ȯ��");
			
			
			// ����ڵ鿡�� �̸����� ������ �����͵�
			ArrayList<Pmdata> data = new ArrayList<Pmdata>();
			
			for(Element each : item ){
				String stationName = each.select("stationName").text();
				String pm10Val = each.select("pm10Value").text();
				String pm25Val = each.select("pm25Value").text();
				String datatime = each.select("dataTime").text();
				
				System.out.println("loadSidoData �̸��Ͽ���"+stationName + ", " + pm10Val + ", " + pm25Val);
				// (datatime - station)
				Pmdata pd = new Pmdata();
				pd.setPm10( pm10Val );
				pd.setPm25( pm25Val);
				pd.setTime(datatime);
				pd.setStationName(stationName);
				
				System.out.println(sido + " : " + stationName);
				Station station = pmDao.findStationByName ( sido, stationName);
				if ( station == null ) {
					// ���ο� �����Ұ� ���峪?
					// TODO : ������ �̸��Ϸ� ���� ������ ��������!
				} else {
					pd.setStation( station.getSeq() );
					
					// 1. ���ο� �����͸� db�� �߰���! 
					Pmdata added = pmDao.saveRealtimeData( pd );
					if ( added != null) {
						// ���� ���� insert �Ǿ���
						data.add ( pd );
					} else {
						// �̰Ŵ� �̹� ����!
						;
					}
				}
			}
			
			return data;
	/*	} catch (IOException e) {
			// 30 �� �ʰ�!!!!
			throw new RuntimeException(e);
		}*/
		
	}
	/**
	 * Ư�� �������� �ǽð� ���� �����͸� ��ȯ�մϴ�.
	 */
	public List<Pmdata> findByStation (Integer stationSeq) {
		return pmDao.findDataByStation( stationSeq);
	}
	
	public Station findStationBySeq(Integer stationSeq){
		return pmDao.findStationBySeq( stationSeq);
	}

	public List<Pmdata> findRealtimeDataByRegion(String sido) {
	
		return pmDao.findRealtimeDataByRegion(sido);
	}
	
	
	public List<Pmdata> findMainRealtimeAvg(){
		return pmDao.findMainRealtimeAvg();
	}
	
	
	
}
