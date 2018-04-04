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
	
	String [] sido = "����, �λ�, �뱸, ��õ, ����, ����, ���, ���, ����, ���, �泲, ����, ����, ���, �泲, ����, ����".split(", ");
	
	@RequestMapping(value="/rt/{stationSeq}", method=RequestMethod.GET)
	public String realtimeData (@PathVariable Integer stationSeq, Model model ) {
		List<Pmdata> data = pmService.findByStation( stationSeq );
		System.out.println("��Ʈ�ѷ� ����"+data);
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
		 * Arrays.asList ( array )  : �迭�� ����Ʈ�� ��ȯ�ؼ� ��ȯ���ݴϴ�(�б����� ����Ʈ)
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
	 * Ư�� �õ��� 
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="/api/region/rt/{sido}", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	@ResponseBody // ���� �����ϴ� ���ڿ��� ������ jsp�� ã�� ������. �̰� ��ü�� ������ �����Դϴ�.
	public String dataByStation (@PathVariable String sido ) throws JsonProcessingException {
		List<Pmdata> data = pmService.findRealtimeDataByRegion(sido);
		
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(data);
		
		return json;
	}
	
}
