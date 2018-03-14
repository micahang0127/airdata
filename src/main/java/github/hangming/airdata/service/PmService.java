package github.hangming.airdata.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import github.hangming.airdata.dao.IPmDao;
import github.hangming.airdata.dto.Pmdata;

@Service
public class PmService {

	IPmDao pmDao;
	
	@Scheduled(cron="0 20 * * * ?")
	//               sec min hour date, month  
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
			List<Pmdata> data = loadSidoData(name);
			// db에 넣어야함!
			// pmDao.insertPmData ( data );
		}
	}
	
	public List<Pmdata> loadSidoData ( String sido ) {
		String template =  "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?serviceKey=obOHqOH23fIebpV3irUaPzNNmsJHYrQdIGCjkhYLZuDahubkfn4MNrwxSLCdPHGbgTlFkcpSL4phmqkddkQmJQ%3D%3D&numOfRows=100&pageSize=10&pageNo=1&startPage=1&sidoName={}&ver=1.3";
		String url = template.replace("{}", sido );
		
		Connection con = Jsoup.connect(url);
		
		con.parser(Parser.xmlParser())
		.timeout(30 * 1000);
		
		Document doc;
		try {
			doc = con.get();
			Elements item = doc.select("body items item");
			
			ArrayList<Pmdata> data = new ArrayList<Pmdata>();
			
			for(Element each : item ){
				String stationName = each.select("stationName").text();
				String pm10Val = each.select("pm10Value").text();
				String pm25Val = each.select("pm25Value").text();
				String datatime = each.select("dataTime").text();
				
				// System.out.println(stationName + ", " + pm10Val + ", " + pm25Val);
				
				Pmdata pd = new Pmdata();
				pd.setPm10( pm10Val );
				pd.setPm25( pm25Val);
				pd.setTime(datatime);
				
				data.add ( pd );
			}
			
			return data;
		} catch (IOException e) {
			// 30 초 초과!!!!
			throw new RuntimeException(e);
		}
		
	}
	
	
}
