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
	@ResponseBody /* 지금 반환하는 문자열을 가지고 jsp 페이지로 맵핑하지 말고 그냥 보내주세요 */
	public String stationsbyregion(@PathVariable String region) throws JsonProcessingException {
		/*
		 * 서울 => [중구, 한강대로]
		 */
		/*
		Station s0 = new Station();
		s0.setSeq(1000);
		s0.setLocation("중구");
		
		Station s1 = new Station();
		s1.setSeq(1001);
		s1.setLocation("한강대로");
		
		List<Station> stations = Arrays.asList(s0, s1);
		*/
		List<Station> stations = stationService.findByRegion(region);
		/*
		 *  stations => [ 
		 *      {"seq":1000, "location":"중구"} , 
		 *      {"seq":1001, "location"="한강"}
		 *  ] 
		 */
		ObjectMapper om = new ObjectMapper(); // 변환 작업을 해주는 클래스
		String json = om.writeValueAsString(stations);
		System.out.println("JSON string : " + json);
		return json;
	}
}
