package github.hangming.airdata.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.hangming.airdata.dao.IUserDao;
import github.hangming.airdata.dao.UserDao;
import github.hangming.airdata.dto.Station;
import github.hangming.airdata.model.UserDto;
import github.hangming.airdata.util.Util;

@Service
public class UserService{

/*	@Autowired
	private IUserDao userDao = new FakeUserDao();
	
	static class FakeUserDao implements IUserDao {

		private UserDto fakeUser = new UserDto(10001L, "a@a.a", "1111");
		
		@Override
		public UserDto login(String email, String password) {
			if ( email.equals(fakeUser.getEmail()) && password.equals(fakeUser.getPassword())) {
				return fakeUser;
			} else {
				return null;
			}
//			return new UserDto (100000L, email, password);
		}
	}*/
	
	
	@Autowired IUserDao dao;
	@Autowired EmailService emailService ;

	// 로그인용 메소드
	public UserDto getUser(String email, String password) {
		return dao.getUser(email, password);
	}
	
	
	public UserDto getEmailCheck(String email){  // 회원가입시 이메일 중복확인
		return dao.getEmailCheck(email);
	}
	
	public UserDto insertUser(UserDto vo){
		// 1. db에 넣고
		UserDto user = dao.insertUser(vo);
		
		String html = Util.readTemplate("newmember");
		html = html.replace("{email}", vo.getEmail());
		html = html.replace("{pass}", vo.getPassword());
		
		String email = vo.getEmail();
		String title = "[회원가입 완료]";
		emailService.sendTestMail(email, title, html);
		
		return user;		
	}
	
	public UserDto changePw(UserDto vo){
		return dao.changePw(vo);
	}


	public void addFavoriteStation(Long user, Integer station) {
		dao.addFavoriteStation(user, station);
		
		
	}
	
	public void delectFavoriteStation(Long user, Integer station){
		
		System.out.println("remove Servic부분"+ user+ ", " +station);
		dao.delectFavoriteStation(user, station);
	}
	

}
