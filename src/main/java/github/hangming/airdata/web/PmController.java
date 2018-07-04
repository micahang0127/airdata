package github.hangming.airdata.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import github.hangming.airdata.dao.IUserDao;
import github.hangming.airdata.dto.Pmdata;
import github.hangming.airdata.dto.Station;
import github.hangming.airdata.model.UserDto;
import github.hangming.airdata.service.PmService;
import github.hangming.airdata.service.StationService;

@Controller
public class PmController {

	@Autowired PmService pmService;
	@Autowired IUserDao userDao;
	
	@Autowired StationService stationService;
	
	String [] sido = "����, �λ�, �뱸, ��õ, ����, ����, ���, ���, ����, ���, �泲, ����, ����, ���, �泲, ����, ����".split(", ");
	
	@RequestMapping(value="/rt/{stationSeq}", method=RequestMethod.GET)
	public String realtimeData (@PathVariable Integer stationSeq, Model model, HttpSession session ) throws JsonProcessingException {
		
		
		List<Pmdata> data = pmService.findByStation( stationSeq );  // �ֱ� 24�ð� ������ pmdata���̺��� station(�õ�seq��)�� ���� ���� ��ü�� select( pm10, pm25, time, station )
		System.out.println("��Ʈ�ѷ� ����"+data);
		model.addAttribute("pmdata", data);
		
		Station station = pmService.findStationBySeq ( stationSeq );  // seq�� ���� ���� stations���̺� ��ü select( seq, region, location, lat, lng )
		String region = station.getRegion();
		
		List<Station> stations = stationService.findByRegion(region); // region(������)�� ���� ���� stations���̺� ��ü select
		
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
		
		
		
		// ���ɵ�� ������ Ȯ��
		UserDto loginUser = (UserDto)session.getAttribute("LOGIN_USER"); 
		if( loginUser != null){
			List<Integer> stations_fav = userDao.getFavoriteStations(loginUser.getSeq());
			
			model.addAttribute("favorites", stations_fav);
		}
		else{
			model.addAttribute("favorites", new ArrayList<Integer>());
		}
		

		
		
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(data);
		json = "{ \"data\": @v }".replace("@v", json);
		model.addAttribute("pmjson", json);
		return "rt-by-station";
	}
	
	
	
	
	@RequestMapping(value="/region/rt/{sido}", method=RequestMethod.GET)
	public String dataBysido(@PathVariable String sido, Model model) {
		List<Pmdata> data = pmService.findRealtimeDataByRegion(sido);
		model.addAttribute("data", data);
		model.addAttribute("sido", Arrays.asList(this.sido));
		model.addAttribute("sidoName", sido);
		
		
		System.out.println("data Ȯ�� : "+data);
		
		return "rt-by-sido";
	}
	
	
	/**
	 * Ư�� �õ��� 
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="/api/region/rt/{sido}", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	@ResponseBody // ���� �����ϴ� ���ڿ��� ������ jsp�� ã�� ������. �̰� ��ü�� ������ �����Դϴ�.
	public String dataByStation (@PathVariable String sido, HttpSession session ) throws JsonProcessingException {
		List<Pmdata> data = pmService.findRealtimeDataByRegion(sido);
	
		Map<String, Object> res = new HashMap<String, Object>();
		
		UserDto loginUser = (UserDto)session.getAttribute("LOGIN_USER"); 
		if( loginUser != null){
			List<Integer> stations = userDao.getFavoriteStations(loginUser.getSeq());
			
			res.put("favorites", stations);
		}
		else{
			res.put("favorites", new ArrayList<Integer>());
		}
		res.put("data", data);
		
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(res);
		System.out.println(sido + "=> " + json);
		return json;
	}
	
	
	
	@RequestMapping(value="/api/station/rt/{stationId}", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	@ResponseBody
	public String dataByLocation (@PathVariable Integer stationId) throws JsonProcessingException {
		
		List<Pmdata> data = pmService.findByStation( stationId );  // �ֱ� 24�ð� ������ pmdata���̺��� station(�õ�seq��)�� ���� ���� ��ü�� select( pm10, pm25, time, station )
		System.out.println("��Ʈ�ѷ� ���� stationId"+stationId);
		//model.addAttribute("pmdata", data);

		
		if(data.isEmpty()){ // !!! �뱸 -> �µ� ����� 
			System.out.println("�� ������ �� �����ʹ� ���� ��������ϴ�. ");
			
		}
		
		
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(data);
		json = "{ \"data\": @v }".replace("@v", json);
		System.out.println("��Ʈ�ѷ� ���� json"+data);
		

		return json;
		
	}
	
	
	
	@RequestMapping(value="/api/station/{sseq}", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	@ResponseBody // ���� �����ϴ� ���ڿ��� ������ jsp�� ã�� ������. �̰� ��ü�� ������ �����Դϴ�.  // �ڡڡڡ� producces =.... �� ����� json���·� return �Ѵ�. �ȱ׷� ajax���� success�� �ȵ� (����)!!!!!!!! 
	public String dataByStation (@PathVariable Integer sseq ) throws JsonProcessingException {
		Station station = pmService.findStationBySeq ( sseq );
		/*
		 *��Ʈ�� ���� �κ�(rt-by-station.jsp).
		 * 
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		sb.append ("lat : " + station.getLat());
		
		sb.append('}');
		*/
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(station);
//		System.out.println(" >> " + json);
		return json;
	}
	
	
	
	
	

	
}

