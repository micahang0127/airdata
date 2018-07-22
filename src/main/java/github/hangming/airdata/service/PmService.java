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
	 * xml 데이터 읽어들여서 db에 insert 하는 작업까지 여기서 시작함
	 */
	public void loadData () {
		
		String [] sido = "서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종".split(", ");
		for ( String name : sido ) {
			// 1. 
			List<Pmdata> data = loadSidoData(name);
			// 2. 사용자들에게 관심지역의 데이터 정보를 이메일로 쏴줌!!
			emailService.sendNotification( data );
		}
	}
	
	public List<Pmdata> loadSidoData ( String sido ) {
		

		Document doc = airApiService.load( sido, idx );
		
	
			Elements rCode = doc.select("header > resultCode"); 
				/* 여기 위에코드에서 에러남. 
				
				
				 * ERROR: org.springframework.scheduling.support.TaskUtils$LoggingErrorHandler - Unexpected error occurred in scheduled task.
					org.jsoup.select.Selector$SelectorParseException: Could not parse query '': unexpected token at '' 
				
					
					=>>  해결   ("header > resultCode ") <X> ★★★  resultCode 옆 띄어쓰기 하면 안됨 !!!!!!! 
				*/
			
			
				// TODO 하나의 메소드가 두 가지 일을 하고 있음 . 이렇게 하면 나중에 안좋음!
				// API 한도 초과 등의 예와 사항은 AirApiService 에서 처리하고 이 클래스에서는 기존에 하던대로 응답만 처리하면 됨
			
		
			
			System.out.println("확인 큐큐큐");
			if( !(rCode.text().equals("00"))) {
			
				idx += 1;
				System.out.println("PMService 둘째 key 확인00 if문 잘 들어옴");
				
				doc = airApiService.load( sido, idx );
				idx = 0;
				
				/* ★★★ 위에 명시한 클래스.java 코드가 잘 작동하고 있는지 확인할 수 있음. 참고 http://hyeok7524.tistory.com/13  */
				logger.debug("===> api key replaced!");  				
		
			}else{ 
				System.out.println("PmService 여기 확인"); 
			}
			
			
			
			Elements item = doc.select("body > items > item");
			System.out.println("여기도 확인");
			
			
			// 사용자들에게 이메일을 전송할 데이터들
			ArrayList<Pmdata> data = new ArrayList<Pmdata>();
			
			for(Element each : item ){
				String stationName = each.select("stationName").text();
				String pm10Val = each.select("pm10Value").text();
				String pm25Val = each.select("pm25Value").text();
				String datatime = each.select("dataTime").text();
				
				System.out.println("loadSidoData 이메일연결"+stationName + ", " + pm10Val + ", " + pm25Val);
				// (datatime - station)
				Pmdata pd = new Pmdata();
				pd.setPm10( pm10Val );
				pd.setPm25( pm25Val);
				pd.setTime(datatime);
				pd.setStationName(stationName);
				
				System.out.println(sido + " : " + stationName);
				Station station = pmDao.findStationByName ( sido, stationName);
				if ( station == null ) {
					// 새로운 관측소가 생겼나?
					// TODO : 관리자 이메일로 예외 사항을 전파해줌!
				} else {
					pd.setStation( station.getSeq() );
					
					// 1. 새로운 데이터를 db에 추가함! 
					Pmdata added = pmDao.saveRealtimeData( pd );
					if ( added != null) {
						// 지금 새로 insert 되었음
						data.add ( pd );
					} else {
						// 이거는 이미 있음!
						;
					}
				}
			}
			
			return data;
	/*	} catch (IOException e) {
			// 30 초 초과!!!!
			throw new RuntimeException(e);
		}*/
		
	}
	/**
	 * 특정 관측소의 실시간 관측 데이터를 반환합니다.
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
