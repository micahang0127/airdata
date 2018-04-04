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
 * ����, �λ�, �뱸, ��õ, ����, ����, ���, ���, ����, ���, �泲, ����, ����, ���, �泲, ����, ����
 * @author SangA
 *
 */
public class StationList {
	
//	static String [] sido = "����, �λ�, �뱸, ��õ, ����, ����, ���, ���, ����, ���, �泲, ����, ����, ���, �泲, ����, ����".split(", ");
	static String [] sido = { "���" };
 	
	
	
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
			
			// body �ؿ� �ڽ����� items �� �־�� �ϰ�, �ٽ� items �ؿ� �ڽ����� item �� �ִ� ��츸 ã��
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
