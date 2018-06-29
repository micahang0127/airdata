package github.hangming.airdata.web;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import github.hangming.airdata.dto.Pmdata;
import github.hangming.airdata.service.PmService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	PmService pmService;

	String[] sido = "서울, 경기, 강원, 대전, 광주, 부산, 충북, 충남, 전북, 전남, 경북, 경남, 제주".split(", ");

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws JsonProcessingException
	 */

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String home(Model model) throws JsonProcessingException  {
		
		
		
		
		List<Pmdata> data = pmService.findMainRealtimeAvg();
		
	
		
		model.addAttribute("data", data);
		
		
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(data);
		model.addAttribute("datajson", json);
		
		//System.out.println("data_main"+ data);
		//System.out.println("datajson_main"+ json);
		
	
		return "index";
	}

	/*
	 * 평균을 낸 쿼리가 아닌 실시간 데이터만 가져오는 쿼리문으로 데이터를 받아 스크립트에서 평균을 내려고 했을 때의 코드 => 코드가 길어지고 복잡해져서 사용포기하고 적합한 쿼리 새로 작성하여 사용함.
	 * 
	 * @RequestMapping(value = "/main_RealData", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String MainTable(Model model) throws JsonProcessingException {
		// @RequestParam(value="sidoOne", required = false, defaultValue = "1")
		// @RequestParam(required=false) String sidoOne => RequestParam 막아놓기
		// 위처럼 써서 여기 sidoOne 을 jsp페이지로 전달할 수 있다.
		// ★ 단, required = true 이면, jsp에 ${sidoOne} 가 꼭 있어야함!!(없으면 400에러, =요청
		// 필드가 있어야함)

		ArrayList res = new ArrayList();
		List<Pmdata> data = null;

		for (String sidoOne : sido) {

			data = pmService.findRealtimeDataByRegion(sidoOne);
			//model.addAttribute(sidoOne, data);
			res.add(data);

			// ★★★ model.addAttribute("",data) 하면 맨마지막에 add된것 (제주) 것만 데이터가
			// 들어감!!!1
			// 그래서 List에 넣어줌.

		}

	
		model.addAttribute("data", res);

		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(res);
		// json = "{ data : @v }".replace("@v", json);
		model.addAttribute("datajson", json);
		System.out.println("json은" + json);

		return json;
	}
*/
	
	
	@RequestMapping(value = "/demo", method = RequestMethod.GET)
	public String demo() {

		return "demo";
	}

}
