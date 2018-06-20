package github.hangming.airdata.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;

import github.hangming.airdata.dao.StationDao;
import github.hangming.airdata.dto.Station;
import github.hangming.airdata.model.UserDto;
import github.hangming.airdata.service.UserService;

@Controller
public class UserController {
	
	@Autowired UserService userService ;

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
		return "redirect:/";
	}
	
	
	@RequestMapping(value="/myInfo")
	String myInfo(HttpSession session){
		
		if(session.getAttribute("LOGIN_USER") != null){
			
			//session.getAttribute("email");
			//session.getAttribute("password");
			System.out.println("email, ��� Ȯ��:"+session.getAttribute("email")+","+session.getAttribute("password"));
			
		
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
	

	
	
	
	
	
	
	
	

}
	
	

