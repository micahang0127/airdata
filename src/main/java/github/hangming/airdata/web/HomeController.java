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

	String[] sido = "����, ���, ����, ����, ����, �λ�, ���, �泲, ����, ����, ���, �泲, ����".split(", ");

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
	 * ����� �� ������ �ƴ� �ǽð� �����͸� �������� ���������� �����͸� �޾� ��ũ��Ʈ���� ����� ������ ���� ���� �ڵ� => �ڵ尡 ������� ���������� ��������ϰ� ������ ���� ���� �ۼ��Ͽ� �����.
	 * 
	 * @RequestMapping(value = "/main_RealData", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String MainTable(Model model) throws JsonProcessingException {
		// @RequestParam(value="sidoOne", required = false, defaultValue = "1")
		// @RequestParam(required=false) String sidoOne => RequestParam ���Ƴ���
		// ��ó�� �Ἥ ���� sidoOne �� jsp�������� ������ �� �ִ�.
		// �� ��, required = true �̸�, jsp�� ${sidoOne} �� �� �־����!!(������ 400����, =��û
		// �ʵ尡 �־����)

		ArrayList res = new ArrayList();
		List<Pmdata> data = null;

		for (String sidoOne : sido) {

			data = pmService.findRealtimeDataByRegion(sidoOne);
			//model.addAttribute(sidoOne, data);
			res.add(data);

			// �ڡڡ� model.addAttribute("",data) �ϸ� �Ǹ������� add�Ȱ� (����) �͸� �����Ͱ�
			// ��!!!1
			// �׷��� List�� �־���.

		}

	
		model.addAttribute("data", res);

		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(res);
		// json = "{ data : @v }".replace("@v", json);
		model.addAttribute("datajson", json);
		System.out.println("json��" + json);

		return json;
	}
*/
	
	
	@RequestMapping(value = "/demo", method = RequestMethod.GET)
	public String demo() {

		return "demo";
	}

}
