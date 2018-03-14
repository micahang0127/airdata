package github.hangming.airdata.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class JsoupDemo {

	public static void main(String[] args) throws IOException {
		InputStream in = JsoupDemo.class.getResourceAsStream("demo.xml");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		copy ( in , bos );
		String xml = new String ( bos.toByteArray(), "UTF-8");
//		System.out.println(xml);
		Document doc = Jsoup.parse(xml, "UTF-8", Parser.xmlParser());
		for (Element e : doc.select("response body items item")) {
		    System.out.println(e.toString());
		}
	}

	private static void copy(InputStream in, OutputStream bos) throws IOException {
		int ch ;
		while ( (ch = in.read()) != -1 ) {
			bos.write(ch);
		}
	}
}
