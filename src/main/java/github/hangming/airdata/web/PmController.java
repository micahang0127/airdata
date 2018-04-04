package github.hangming.airdata.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import github.hangming.airdata.dto.Pmdata;
import github.hangming.airdata.dto.Station;
import github.hangming.airdata.service.PmService;
import github.hangming.airdata.service.StationService;

@Controller
public class PmController {

	@Autowired PmService pmService;
	
	@Autowired StationService stationService;
	
	String [] sido = "서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종".split(", ");
	
	@RequestMapping(value="/rt/{stationSeq}", method=RequestMethod.GET)
	public String realtimeData (@PathVariable Integer stationSeq, Model model ) {
		List<Pmdata> data = pmService.findByStation( stationSeq );
		System.out.println("콘트롤러 여기"+data);
		model.addAttribute("pmdata", data);
		
		Station station = pmService.findStationBySeq ( stationSeq );
		String region = station.getRegion();
		
		List<Station> stations = stationService.findByRegion(region);
		
		model.addAttribute("stations",stations);
		/*
		model.addAttribute("sido", station.getRegion());
		model.addAttribute("station", station.getLocation());
		*/
		model.addAttribute("station", station);
		/*
		 * Arrays.asList ( array )  : 배열을 리스트로 변환해서 반환해줍니다(읽기전용 리스트)
		 */
		model.addAttribute("sido", Arrays.asList(sido) );
		return "rt-by-station";
	}
	
	
	@RequestMapping(value="/region/rt/{sido}", method=RequestMethod.GET)
	public String dataBysido(@PathVariable String sido, Model model) {
		List<Pmdata> data = pmService.findRealtimeDataByRegion(sido);
		model.addAttribute("data", data);
		model.addAttribute("sido", Arrays.asList(this.sido));
		model.addAttribute("sidoName", sido);
		return "rt-by-sido";
	}
	/**
	 * 특정 시도의 
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="/api/region/rt/{sido}", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	@ResponseBody // 지금 리턴하는 문자열을 가지고 jsp를 찾지 마세요. 이거 자체가 응답의 본문입니다.
	public String dataByStation (@PathVariable String sido ) throws JsonProcessingException {
		List<Pmdata> data = pmService.findRealtimeDataByRegion(sido);
		
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(data);
		
		return json;
	}
	
}
