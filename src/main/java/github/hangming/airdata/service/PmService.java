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
	 * xml ������ �о�鿩�� db�� insert �ϴ� �۾����� ���⼭ ������
	 */
	public void loadData () {
		
		String [] sido = "����, �λ�, �뱸, ��õ, ����, ����, ���, ���, ����, ���, �泲, ����, ����, ���, �泲, ����, ����".split(", ");
		for ( String name : sido ) {
			List<Pmdata> data = loadSidoData(name);
			// db�� �־����!
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
			// 30 �� �ʰ�!!!!
			throw new RuntimeException(e);
		}
		
	}
	
	
}
