package github.hangming.airdata.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import github.hangming.airdata.model.UserDto;
import github.hangming.airdata.service.UserService;

@Controller
public class UserController {
	
	@Autowired UserService userService ;

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
		return "index";
	}
	
	
	@RequestMapping(value="/checkEmail", method=RequestMethod.GET)
	@ResponseBody String checkEmail(@RequestParam String email ){ // 2
//		String email = req.getParameter("email"); // 1.
		
		System.out.println("이메일 중복확인부분 :" + email +"서비스이메일부분" );
		UserDto user = userService.getEmailCheck(email);
//		System.out.println("중복아이디 있다");
//		// {"dup" :false }, {"dup" : true } 
//		return 1;
		if(user != null){
			return "{\"dup\" : true }";
		} else {
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


}
	
	

