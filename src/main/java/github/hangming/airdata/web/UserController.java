package github.hangming.airdata.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

	@RequestMapping(value="/register")
	public String register(Model model, HttpServletRequest req ){
		// �̰� ������ ���� ���� �Ǵ� ������ ���ͼ��Ϳ��� ���ݴϴ�!!
		model.addAttribute("ctxpath", req.getContextPath());
		return "register";
	}
	
	@RequestMapping(value="/doRegister", method=RequestMethod.POST)
	public String doregister(HttpServletRequest req){
		String id = req.getParameter("id");
		String email = req.getParameter("email");
		String pass = req.getParameter("pw");
		
		System.out.println(id + ", " + email + ", " + pass);
		return "sucessregister";
	}
	
}
