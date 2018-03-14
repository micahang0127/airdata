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
	
	static String [] sido = "����, �λ�, �뱸, ��õ, ����, ����, ���, ���, ����, ���, �泲, ����, ����, ���, �泲, ����, ����".split(", ");
 	
	
	
	static String template = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getMsrstnList?serviceKey=obOHqOH23fIebpV3irUaPzNNmsJHYrQdIGCjkhYLZuDahubkfn4MNrwxSLCdPHGbgTlFkcpSL4phmqkddkQmJQ%3D%3D&numOfRows=100&pageSize=100&pageNo=1&startPage=1&addr={}";
	
	static String query = "insert into stations (region, location ) values ( '0' ,'1');";
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
		
		new Thread(r).start();
		/*
		for(String name : sido){
//			System.out.println(name);
			String url = template.replace("{}", name);
			
			Connection con = Jsoup.connect(url);
			
			con.parser(Parser.xmlParser())
			   .timeout(10 * 1000 );
			
			Document doc = con.get();
			Elements names = doc.select("body items item stationName");
			for ( Element each : names) {
				
				System.out.println( query.replace("1", each.text()).replace("0", name) );
			}
			try {
				Thread.sleep(5*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		*/
	}
}
