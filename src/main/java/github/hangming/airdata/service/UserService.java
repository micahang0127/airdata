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

	// �α��ο� �޼ҵ�
	public UserDto getUser(String email, String password) {
		return dao.getUser(email, password);
	}
	
	
	public UserDto getEmailCheck(String email){  // ȸ�����Խ� �̸��� �ߺ�Ȯ��
		return dao.getEmailCheck(email);
	}
	
	public UserDto insertUser(UserDto vo){				
		
		String html = Util.readTemplate("newmember");				// ȸ������ �� "newmember.html"(template�� ����) �������� ������ ������ 
		html = html.replace("{email}", vo.getEmail());
		html = html.replace("{pass}", vo.getPassword());
		
		String email = vo.getEmail();
		String title = "[ȸ������ �Ϸ�]";
		/*
		 * TODO �̸����� ���� �����ϴ��� Ȯ���ϴ� ��� 
		 *      https://stackoverflow.com/questions/13514005/how-to-check-mail-address-is-exists-or-not ����
		 */
		emailService.sendTestMail(email, title, html);
		
		// ���߿� Ʈ����� �������� �� ������ �ذ��ϴ� �Ǵٸ� ����� ����
		UserDto user = dao.insertUser(vo);
		
		return user;		
	}
	
	public UserDto changePw(UserDto vo){
		return dao.changePw(vo);
	}


	public void addFavoriteStation(Long user, Integer station, Integer pm10_limit, Integer pm25_limit) {
		dao.addFavoriteStation(user, station, pm10_limit, pm25_limit);
		
		
	}
	
	public void changePmLimit(Integer pm10Limit, Integer pm25Limit, Long user, Integer station){
		dao.changePmLimit(pm10Limit, pm25Limit, user, station);
	}
	
	
	public void delectFavoriteStation(Long user, Integer station){
		
		System.out.println("remove Servic�κ�"+ user+ ", " +station);
		dao.delectFavoriteStation(user, station);
	}
	

}
