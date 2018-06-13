package github.hangming.airdata.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import github.hangming.airdata.model.UserDto;

public class Util {
	public static String readTemplate ( String templateName ) {
		
		String path = "template/" + templateName + ".html";
		InputStream in = Util.class.getClassLoader().getResourceAsStream(path);
		BufferedReader reader = null ;
		try {
			reader = new BufferedReader( new InputStreamReader(in, "utf-8") );
			StringBuilder sb = new StringBuilder();
			String line;
			while ( (line = reader.readLine()) != null ) {
				sb.append(line);
			}
			
			String html = sb.toString();
			return html;
					
		} catch (IOException e) {
//			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
