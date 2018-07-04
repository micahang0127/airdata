package github.hangming.airdata.dao;

import java.util.List;

import github.hangming.airdata.dto.Pmdata;
import github.hangming.airdata.model.UserDto;

public interface IUserDao {
	
	UserDto getUser(String email, String password); // <--
	UserDto getEmailCheck( String vo);
	UserDto insertUser( UserDto vo); // OK
	UserDto changePw( UserDto vo);
	
	boolean addFavoriteStation( Long user, Integer station);
	List<Integer> getFavoriteStations( Long userSeq);
	boolean delectFavoriteStation( Long user, Integer station );
	/**
	 * 주어진 관측소를 관심지역으로 추가한 사람들의 이메일을 모아서 반환함
	 * @param pmdata
	 * @return
	 */
	List<String> getfavEmail(Pmdata pmdata );
	
	
/*	UserDto getUser(UserDto vo);
	
	UserDto getEmailCheck(UserDto vo);
	
	int insertUser(UserDto vo);
*/
}

