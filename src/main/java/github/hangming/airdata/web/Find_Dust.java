package github.hangming.airdata.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Find_Dust {

	@RequestMapping(value="/find_dust")
	public String find_dust(HttpServletRequest req){
		
		return "find_dust";
	}
	
}
