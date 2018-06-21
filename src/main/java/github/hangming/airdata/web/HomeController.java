package github.hangming.airdata.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import github.hangming.airdata.dto.Pmdata;
import github.hangming.airdata.service.PmService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired PmService pmService;
	
	String [] sido =  "서울, 경기, 강원, 대전, 광주, 부산, 충북, 충남, 전북, 전남, 경북, 경남, 제주".split(", ");
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home( @RequestParam(required=false) String sidoOne, Model model) throws JsonProcessingException {
		

	//	model.addAttribute("sido", Arrays.asList(this.sido));

		System.out.println("sidoOne 확인"+ sidoOne);
		
		List<Pmdata> data =  pmService.findRealtimeDataByRegion(sidoOne);			
		System.out.println("data 확인"+ data);
		model.addAttribute("data", data);
		
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(data);
		json = "{ data : @v }".replace("@v", json);
		model.addAttribute("datajson", json);
		
		
		return "index";
	}
	
/*	
	@RequestMapping(value= "/sidoOneData/{sido_OneData}" , method=RequestMethod.GET)
	@ResponseBody
	public String sidoOneData(@PathVariable String sidoOne, Model model ){


		return sidoOne; 
	}*/
	
	@RequestMapping(value = "/demo", method = RequestMethod.GET)
	public String demo() {
		
		return "demo";
	}
	
}
