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
	
	String [] sido = "서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종".split(", ");
	
	@RequestMapping(value="/rt/{stationSeq}", method=RequestMethod.GET)
	public String realtimeData (@PathVariable Integer stationSeq, Model model, HttpSession session ) throws JsonProcessingException {
		
		
		List<Pmdata> data = pmService.findByStation( stationSeq );  // 최근 24시간 동안의 pmdata테이블의 station(시도seq값)이 같은 것의 전체를 select( pm10, pm25, time, station )
		System.out.println("콘트롤러 여기"+data);
		model.addAttribute("pmdata", data);
		
		Station station = pmService.findStationBySeq ( stationSeq );  // seq가 같은 것의 stations테이블 전체 select( seq, region, location, lat, lng )
		String region = station.getRegion();
		
		List<Station> stations = stationService.findByRegion(region); // region(관측소)이 같은 것의 stations테이블 전체 select
		
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
		
		
		
		// 관심등록 데이터 확인
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
		
		
		System.out.println("data 확인 : "+data);
		
		return "rt-by-sido";
	}
	
	
	/**
	 * 특정 시도의 
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value="/api/region/rt/{sido}", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	@ResponseBody // 지금 리턴하는 문자열을 가지고 jsp를 찾지 마세요. 이거 자체가 응답의 본문입니다.
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
		
		List<Pmdata> data = pmService.findByStation( stationId );  // 최근 24시간 동안의 pmdata테이블의 station(시도seq값)이 같은 것의 전체를 select( pm10, pm25, time, station )
		System.out.println("콘트롤러 여기 stationId"+stationId);
		//model.addAttribute("pmdata", data);

		
		if(data.isEmpty()){ // !!! 대구 -> 좌동 사라짐 
			System.out.println("이 관측소 의 데이터는 현재 사라졌습니다. ");
			
		}
		
		
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(data);
		json = "{ \"data\": @v }".replace("@v", json);
		System.out.println("콘트롤러 여기 json"+data);
		

		return json;
		
	}
	
	
	
	@RequestMapping(value="/api/station/{sseq}", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	@ResponseBody // 지금 리턴하는 문자열을 가지고 jsp를 찾지 마세요. 이거 자체가 응답의 본문입니다.  // ★★★★ producces =.... 꼭 써줘야 json형태로 return 한다. 안그럼 ajax에서 success로 안들어감 (오류)!!!!!!!! 
	public String dataByStation (@PathVariable Integer sseq ) throws JsonProcessingException {
		Station station = pmService.findStationBySeq ( sseq );
		/*
		 *차트를 구현 부분(rt-by-station.jsp).
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

