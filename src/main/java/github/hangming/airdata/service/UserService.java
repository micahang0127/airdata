package github.hangming.airdata.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.hangming.airdata.dao.IUserDao;
import github.hangming.airdata.dao.UserDao;
import github.hangming.airdata.model.UserDto;

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
	
	
	@Autowired UserDao dao;

	// 로그인용 메소드
	public UserDto getUser(String email, String password) {
		return dao.getUser(email, password);
	}
	
	
	public UserDto getEmailCheck(String email){  // 회원가입시 이메일 중복확인
		return dao.getEmailCheck(email);
	}
	
	public UserDto insertUser(UserDto vo){
		return  dao.insertUser(vo);
	}
	
	

}
