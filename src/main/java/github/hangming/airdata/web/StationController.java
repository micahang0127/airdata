package github.hangming.airdata.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import github.hangming.airdata.dto.Station;
import github.hangming.airdata.service.StationService;

@Controller
public class StationController {

	@Inject StationService stationService;
	
//	@RequestMapping(value="/stations/{region}", method=RequestMethod.GET, produces="application/json;charset=UTF-8" )
	@RequestMapping(value="/stations/{region}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE )
	@ResponseBody /* ���� ��ȯ�ϴ� ���ڿ��� ������ jsp �������� �������� ���� �׳� �����ּ��� */
	public String stationsbyregion(@PathVariable String region) throws JsonProcessingException {
		/*
		 * ���� => [�߱�, �Ѱ����]
		 */
		/*
		Station s0 = new Station();
		s0.setSeq(1000);
		s0.setLocation("�߱�");
		
		Station s1 = new Station();
		s1.setSeq(1001);
		s1.setLocation("�Ѱ����");
		
		List<Station> stations = Arrays.asList(s0, s1);
		*/
		List<Station> stations = stationService.findByRegion(region);
		/*
		 *  stations => [ 
		 *      {"seq":1000, "location":"�߱�"} , 
		 *      {"seq":1001, "location"="�Ѱ�"}
		 *  ] 
		 */
		ObjectMapper om = new ObjectMapper(); // ��ȯ �۾��� ���ִ� Ŭ����
		String json = om.writeValueAsString(stations);
		System.out.println("JSON string : " + json);
		return json;
	}
}
