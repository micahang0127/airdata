package github.hangming.airdata.dao;

import java.util.List;

import github.hangming.airdata.model.UserDto;

public interface IUserDao {
	
	List<UserDto> getUser(UserDto vo); // <--
	UserDto getEmailCheck( String vo);
	UserDto insertUser( UserDto vo); // OK
	/*	UserDto getUser(UserDto vo);
	
	UserDto getEmailCheck(UserDto vo);
	
	int insertUser(UserDto vo);
*/
}

