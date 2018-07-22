package github.hangming.airdata.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class AirApiService {
	
	List<String> apiKeys = Arrays.asList(
			"obOHqOH23fIebpV3irUaPzNNmsJHYrQdIGCjkhYLZuDahubkfn4MNrwxSLCdPHGbgTlFkcpSL4phmqkddkQmJQ%3D%3D",
			"9gE3v9m4cPDEXdouyeF7p1Q2Y8Pl%2FddfZ7W9s%2B9L%2BbkG1kpdqFdfOWV5ZGIL7y%2BjchclC4QKDehMwhxOyNMllw%3D%3D");

	String template =  "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?serviceKey={KEY}&numOfRows=100&pageSize=10&pageNo=1&startPage=1&sidoName={}&ver=1.3";

	
	public Document load (String sido, int idx ) {
		
		
		String url = template.replace("{}", sido )
		 .replace("{KEY}", apiKeys.get(idx));
		
		Connection con = Jsoup.connect(url);		
		con.parser(Parser.xmlParser())
		.timeout(50 * 10000);
		
		
		
		Document doc;
		try{
			
			doc = con.get();
			
		}catch (IOException e) {
			// 50 ÃÊ ÃÊ°ú!!!!
			throw new RuntimeException(e);
		}	
		
		return doc;
	}
}
