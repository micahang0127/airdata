package github.hangming.airdata.dao;

import java.util.List;

import github.hangming.airdata.model.UserDto;

public interface IUserDao {
	
	UserDto getUser(String email, String password); // <--
	UserDto getEmailCheck( String vo);
	UserDto insertUser( UserDto vo); // OK
	UserDto changePw( UserDto vo);
	
	boolean addFavoriteStation( Long user, Integer station);
	List<Integer> getFavoriteStations( Long userSeq);
	
/*	UserDto getUser(UserDto vo);
	
	UserDto getEmailCheck(UserDto vo);
	
	int insertUser(UserDto vo);
*/
}

