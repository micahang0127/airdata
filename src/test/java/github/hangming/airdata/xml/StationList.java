package github.hangming.airdata.xml;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

/**
 * 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종
 * @author SangA
 *
 */
public class StationList {
	
//	static String [] sido = "서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종".split(", ");
	static String [] sido = { "경기" };
 	
	
	
	static String template = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getMsrstnList?serviceKey=obOHqOH23fIebpV3irUaPzNNmsJHYrQdIGCjkhYLZuDahubkfn4MNrwxSLCdPHGbgTlFkcpSL4phmqkddkQmJQ%3D%3D&numOfRows=100&pageSize=100&pageNo=1&startPage=1&addr={}";
	
	static String query = "insert into stations (region, location, lat, lng ) values ( ':0' ,':1', :2, :3);";
	public static void main(String[] args) throws IOException {
		
		Runnable r = new Runnable() {
			public void run() {
				while ( true) {
					System.out.println("yes...");
					try {
						Thread.sleep( 1000 );
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		};
		
		//new Thread(r).start();
		
		for(String name : sido){
//			System.out.println(name);
			String url = template.replace("{}", name);
			
			Connection con = Jsoup.connect(url);
			
			con.parser(Parser.xmlParser())
			   .timeout(10 * 1000 );
			
			Document doc = con.get();
			
			// body 밑에 자식으로 items 가 있어야 하고, 다시 items 밑에 자식으로 item 이 있는 경우만 찾음
			Elements items = doc.select("body > items > item");
			
			for ( Element each : items) {
				String stationName = each.select("stationName").text().trim();
				String lat = each.select("dmX").text().trim(); // "" => 0
				String lng = each.select("dmY").text().trim(); // "" => 0
				if(lat.equals("")){
					lat ="0";
				}
				if(lng.equals("")){
					lng ="0";
				}
			
				System.out.println( query.replace(":1", stationName)
						                 .replace(":0", name)
						                 .replace(":2", lat)
						                 .replace(":3", lng));
			}
			try {
				Thread.sleep(5*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
