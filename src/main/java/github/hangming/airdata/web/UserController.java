package github.hangming.airdata.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import github.hangming.airdata.dao.IUserDao;
import github.hangming.airdata.dto.Pmdata;
import github.hangming.airdata.dto.Station;
import github.hangming.airdata.model.UserDto;
import github.hangming.airdata.service.PmService;
import github.hangming.airdata.service.UserService;

@Controller
public class UserController {
	
	@Autowired UserService userService ;
	@Autowired PmService pmService;
	@Autowired IUserDao userDao;

	@RequestMapping(value="/register")
	public String register(Model model, HttpServletRequest req ){
		// 이게 워래는 서블릿 필터 또는 스프링 인터셉터에서 해줍니다!!
		model.addAttribute("ctxpath", req.getContextPath());
		return "register";
	}
	
	@RequestMapping(value="/doRegister", method=RequestMethod.POST)
	public String doregister(HttpServletRequest req){
		String seq = req.getParameter("seq");
		String email = req.getParameter("email");
		String pass = req.getParameter("pw");
		
		System.out.println(seq + ", " + email + ", " + pass);
		return "successregister";
	}
	
	
	@RequestMapping(value="/login")
	public String login(UserDto vo, HttpSession session){
		/*
		System.out.println("로그인 콘트롤러 진입 " + vo.getEmail());
		if(userService.getUser(vo) != null){
			if(userService.getUser(vo).getEmail().equals(vo.getEmail())){
				System.out.println("로그인 이 아뒤있어");
				session.setAttribute("LoginEmail", vo.getEmail());
			}
		}
		else{
			System.out.println("아뒤없어ㅠ");
			return "login";
		}
		*/
		return "login";
	}
	
	@RequestMapping(value = "/succLogin", method=RequestMethod.POST)
	@ResponseBody
	public String doLogin(
			HttpSession session,
			@RequestParam String email, 
			@RequestParam(name="pw") String pw) {
		// HttpSession session = req.getSession(); // servlet 
		UserDto user = userService.getUser(email, pw);
		if(user != null){
			session.setAttribute("LOGIN_USER", user);
			session.setAttribute("email", email);
			session.setAttribute("password", pw);
			return "{\"success\" : true }";
		}
		else{
			return "{\"success\" : false }";
		}
		//return "{}"; // "{}.jsp"
		 
	}
	
	
	@RequestMapping(value="/checkEmail", method=RequestMethod.GET)
	@ResponseBody 
	String checkEmail(@RequestParam String email ){ // 2
//		String email = req.getParameter("email"); // 1.
		
		System.out.println("이메일 중복확인부분 :" + email +"서비스이메일부분" );
		UserDto user = userService.getEmailCheck(email);
//		System.out.println("중복아이디 있다");
//		// {"dup" :false }, {"dup" : true } 
//		return 1;
		if(user != null || email.equals("")){ 
			return "{\"dup\" : true }";
		}else {
			return "{\"dup\" : false }";
		}
	}
	
	@RequestMapping(value="/succRegister")
	@ResponseBody
	String succRegister(UserDto vo){
		System.out.println("회원가입성공 콘트롤러 진입 : " + vo.getEmail() +"/ " + vo.getPassword());
		userService.insertUser(vo);
		System.out.println("새 유저 등록 아이디" + vo.getEmail());
		return "{\"success\" : true }";
	}
	

	
	@RequestMapping(value="/logout")
	String logout(HttpSession session){
		// session.removeAttribute("LOGIN_USER");
		session.invalidate();
		return "redirect:/index";
	}
	
	
	@RequestMapping(value="/myInfo")
	String myInfo(HttpSession session, Model model) throws JsonProcessingException{
		
		UserDto loginUser = (UserDto)session.getAttribute("LOGIN_USER");
		if(loginUser != null){
			
			//session.getAttribute("email");
			//session.getAttribute("password");
			System.out.println("email, 비번 확인:"+session.getAttribute("email")+","+session.getAttribute("password"));
			
			List<Integer> stationsSeq_fav = userDao.getFavoriteStations(loginUser.getSeq());
			String stationStr_fav = "관심지역이 없습니다"; 
			String favSet = ""; // ★★★★★ 로하면 초기 데이터가 안들어가있음. => null로 지정하면 null로 들어가있음. 
			
			
			/* !!!  findAll 로 stations테이블 전체를 가져올 수 있지만, stations 의 데이터가 대량인 경우 한번에 불필요한 데이터까지 다 가지고 오는 셈이므로
			 	seq가 맞는 것에서만 데이터를 가져 오게 함.  */
			for(int i=0; i < stationsSeq_fav.size(); i++){
				int station = stationsSeq_fav.get(i);
				
				Station find_location =  pmService.findStationBySeq(station);
				stationStr_fav = stationStr_fav + "," + find_location.getLocation();
				
				
				favSet = favSet + station + "," + find_location.getLocation() +"/";
				
				System.out.println("관심지역 확인"+stationStr_fav);
				
				
			}
			
			session.setAttribute("favorites", stationStr_fav);
			session.setAttribute("favSet", favSet);
			System.out.println("favSet확인"+favSet);
			
			
			/* !!! ★★★★★ session이나 model 집어넣은 키값은 스크립트에서 호출해 json형태로 파싱 할 때 
			 * 			 [ '${favSet} 이렇게 호출 하고 JSON.parse('${favSet}'); 이렇게 json파싱함 ]
			 	한글은 파싱이 안되고 오류남. => so, 아래처럼 java코드(controller)에서  먼저 파싱한 후 그것을 스크립트에서 다시 json파싱을 한다.  	*/
			ObjectMapper om = new ObjectMapper();
			String json = om.writeValueAsString(favSet.split("/"));
			model.addAttribute("favSetjson", json);
			
			System.out.println("favSetjson확인"+json);
			
			return "myInformation";

		}else{
			System.out.println("로그인이 안 되어있어요");
			return "";
		}
	}
	
	@RequestMapping(value="/changePassword")
	@ResponseBody
	String changePw(HttpSession session, UserDto vo){
		if(session.getAttribute("LOGIN_USER") != null){
			System.out.println("changePw 콘트롤러 진입, vo.getPassword : "+ vo.getPassword());
			userService.changePw(vo);
			System.out.println("새 비번 등록 성공?"+ vo.getPassword()+"email"+ vo.getEmail());
			return "{\"success\" : true}";
			
		}else{
			System.out.println("여기는 실패 ㅠ ");
			return "{\"fail\" : false}";
		}
	}

	@RequestMapping(value= "/favorstation/add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	String addStation(@RequestParam Integer station, HttpSession session){
		// 1 .loginUser !!! 
		UserDto loginUser = (UserDto)session.getAttribute("LOGIN_USER");
		if(loginUser == null){
			return "{\"success\" : false , \"cause\" : \"LOGIN_REQUIRED\"}";
		}
		// System.out.println("유저있음");
		System.out.println("add station!! : " + station);
		userService.addFavoriteStation ( loginUser.getSeq(), station);
		return "{\"success\" : true}";
	}
	
	
	@RequestMapping(value="/favorstation/remove", method=RequestMethod.POST,  produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	String removeStation(@RequestParam Integer station, HttpSession session){
		UserDto loginUser = (UserDto)session.getAttribute("LOGIN_USER");
		if(loginUser == null){
			System.out.println("로그인부터 하거라");
			return "{\"success\" : false, \"cause\" : \"LOGIN_REQUIRED\"}";
		}
		
		System.out.println("remove station"+ station);
		userService.delectFavoriteStation( loginUser.getSeq() , station);
		
		return "{\"success\" : true}";
		
		
	}
	
	
	
	
	
	
	
	

}
	
	

