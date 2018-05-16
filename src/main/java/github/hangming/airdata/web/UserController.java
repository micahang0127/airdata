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
		return "index";
	}
	
	
	@RequestMapping(value="/checkEmail", method=RequestMethod.GET)
	@ResponseBody String checkEmail(@RequestParam String email ){ // 2
//		String email = req.getParameter("email"); // 1.
		
		System.out.println("�̸��� �ߺ�Ȯ�κκ� :" + email +"�����̸��Ϻκ�" );
		UserDto user = userService.getEmailCheck(email);
//		System.out.println("�ߺ����̵� �ִ�");
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
		System.out.println("ȸ�����Լ��� ��Ʈ�ѷ� ���� : " + vo.getEmail() +"/ " + vo.getPassword());
		userService.insertUser(vo);
		System.out.println("�� ���� ��� ���̵�" + vo.getEmail());
		return "{\"success\" : true }";
	}


}
	
	

