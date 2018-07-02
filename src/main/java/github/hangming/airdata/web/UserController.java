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
		// �̰� ������ ���� ���� �Ǵ� ������ ���ͼ��Ϳ��� ���ݴϴ�!!
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
		System.out.println("�α��� ��Ʈ�ѷ� ���� " + vo.getEmail());
		if(userService.getUser(vo) != null){
			if(userService.getUser(vo).getEmail().equals(vo.getEmail())){
				System.out.println("�α��� �� �Ƶ��־�");
				session.setAttribute("LoginEmail", vo.getEmail());
			}
		}
		else{
			System.out.println("�Ƶھ����");
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
		
		System.out.println("�̸��� �ߺ�Ȯ�κκ� :" + email +"�����̸��Ϻκ�" );
		UserDto user = userService.getEmailCheck(email);
//		System.out.println("�ߺ����̵� �ִ�");
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
		System.out.println("ȸ�����Լ��� ��Ʈ�ѷ� ���� : " + vo.getEmail() +"/ " + vo.getPassword());
		userService.insertUser(vo);
		System.out.println("�� ���� ��� ���̵�" + vo.getEmail());
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
			System.out.println("email, ��� Ȯ��:"+session.getAttribute("email")+","+session.getAttribute("password"));
			
			List<Integer> stationsSeq_fav = userDao.getFavoriteStations(loginUser.getSeq());
			String stationStr_fav = "���������� �����ϴ�"; 
			String favSet = ""; // �ڡڡڡڡ� ���ϸ� �ʱ� �����Ͱ� �ȵ�����. => null�� �����ϸ� null�� ������. 
			
			
			/* !!!  findAll �� stations���̺� ��ü�� ������ �� ������, stations �� �����Ͱ� �뷮�� ��� �ѹ��� ���ʿ��� �����ͱ��� �� ������ ���� ���̹Ƿ�
			 	seq�� �´� �Ϳ����� �����͸� ���� ���� ��.  */
			for(int i=0; i < stationsSeq_fav.size(); i++){
				int station = stationsSeq_fav.get(i);
				
				Station find_location =  pmService.findStationBySeq(station);
				stationStr_fav = stationStr_fav + "," + find_location.getLocation();
				
				
				favSet = favSet + station + "," + find_location.getLocation() +"/";
				
				System.out.println("�������� Ȯ��"+stationStr_fav);
				
				
			}
			
			session.setAttribute("favorites", stationStr_fav);
			session.setAttribute("favSet", favSet);
			System.out.println("favSetȮ��"+favSet);
			
			
			/* !!! �ڡڡڡڡ� session�̳� model ������� Ű���� ��ũ��Ʈ���� ȣ���� json���·� �Ľ� �� �� 
			 * 			 [ '${favSet} �̷��� ȣ�� �ϰ� JSON.parse('${favSet}'); �̷��� json�Ľ��� ]
			 	�ѱ��� �Ľ��� �ȵǰ� ������. => so, �Ʒ�ó�� java�ڵ�(controller)����  ���� �Ľ��� �� �װ��� ��ũ��Ʈ���� �ٽ� json�Ľ��� �Ѵ�.  	*/
			ObjectMapper om = new ObjectMapper();
			String json = om.writeValueAsString(favSet.split("/"));
			model.addAttribute("favSetjson", json);
			
			System.out.println("favSetjsonȮ��"+json);
			
			return "myInformation";

		}else{
			System.out.println("�α����� �� �Ǿ��־��");
			return "";
		}
	}
	
	@RequestMapping(value="/changePassword")
	@ResponseBody
	String changePw(HttpSession session, UserDto vo){
		if(session.getAttribute("LOGIN_USER") != null){
			System.out.println("changePw ��Ʈ�ѷ� ����, vo.getPassword : "+ vo.getPassword());
			userService.changePw(vo);
			System.out.println("�� ��� ��� ����?"+ vo.getPassword()+"email"+ vo.getEmail());
			return "{\"success\" : true}";
			
		}else{
			System.out.println("����� ���� �� ");
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
		// System.out.println("��������");
		System.out.println("add station!! : " + station);
		userService.addFavoriteStation ( loginUser.getSeq(), station);
		return "{\"success\" : true}";
	}
	
	
	@RequestMapping(value="/favorstation/remove", method=RequestMethod.POST,  produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	String removeStation(@RequestParam Integer station, HttpSession session){
		UserDto loginUser = (UserDto)session.getAttribute("LOGIN_USER");
		if(loginUser == null){
			System.out.println("�α��κ��� �ϰŶ�");
			return "{\"success\" : false, \"cause\" : \"LOGIN_REQUIRED\"}";
		}
		
		System.out.println("remove station"+ station);
		userService.delectFavoriteStation( loginUser.getSeq() , station);
		
		return "{\"success\" : true}";
		
		
	}
	
	
	
	
	
	
	
	

}
	
	

