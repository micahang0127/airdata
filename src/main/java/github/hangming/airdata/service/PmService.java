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
	EmailService emailService ;
	
	List<String> apiKeys = Arrays.asList(
			"obOHqOH23fIebpV3irUaPzNNmsJHYrQdIGCjkhYLZuDahubkfn4MNrwxSLCdPHGbgTlFkcpSL4phmqkddkQmJQ%3D%3D",
			"bbbbb");
	int idx = 0;
	
	@Scheduled(cron="0 10,20,30,40 * * * ?")
	//               sec min hour date, month  
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
			
			// pmDao.insertPmData ( data );
		}
	}
	
	public List<Pmdata> loadSidoData ( String sido ) {
		String template =  "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?serviceKey={KEY}&numOfRows=100&pageSize=10&pageNo=1&startPage=1&sidoName={}&ver=1.3";
		
		String url = template.replace("{}", sido )
				             .replace("{KEY}", apiKeys.get(idx));
		
		
		Connection con = Jsoup.connect(url);
		
		con.parser(Parser.xmlParser())
		.timeout(30 * 1000);
		
		Document doc;
		try {
			doc = con.get();
			Elements rCode = doc.select("header > resultCode ");
			// TODO �ϳ��� �޼ҵ尡 �� ���� ���� �ϰ� ���� . �̷��� �ϸ� ���߿� ������!
			// API �ѵ� �ʰ� ���� ���� ������ AirApiService ���� ó���ϰ� �� Ŭ���������� ������ �ϴ���� ���丸 ó���ϸ� ��
			if( rCode.text().equals("22")) {
				// TODO �ڵ尡 �ߺ���
				idx += 1;
				logger.debug("api key replaced!");
				url = template.replace("{}", sido )
			             .replace("{KEY}", apiKeys.get(idx));
				con = Jsoup.connect(url);
				con.parser(Parser.xmlParser())
				   .timeout(30 * 1000);
				doc = con.get();
			}
			Elements item = doc.select("body > items > item");
			
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
		} catch (IOException e) {
			// 30 �� �ʰ�!!!!
			throw new RuntimeException(e);
		}
		
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
