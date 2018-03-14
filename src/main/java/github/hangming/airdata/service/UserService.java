package github.hangming.airdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import github.hangming.airdata.dao.IUserDao;
import github.hangming.airdata.model.UserDto;

@Service
public class UserService implements IUserService{

//	@Autowired
	private IUserDao userDao = new FakeUserDao();
	
	@Override
	public UserDto login(String email, String password) {
		
		return userDao.login(email, password);
	}
	
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
	}

}
