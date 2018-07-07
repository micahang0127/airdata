package github.hangming.airdata.dao;

import java.util.List;

import github.hangming.airdata.dto.FavorStationDto;
import github.hangming.airdata.dto.Pmdata;
import github.hangming.airdata.model.UserDto;

public interface IUserDao {
	
	UserDto getUser(String email, String password); // <--
	UserDto getEmailCheck( String vo);
	UserDto insertUser( UserDto vo); // OK
	UserDto changePw( UserDto vo);
	
	boolean addFavoriteStation( Long user, Integer station, Integer pm10_limit, Integer pm25_limit);
	List<Integer> getFavoriteStations( Long userSeq);
	List<FavorStationDto> getFavoriteStationDetail( Long userSeq);
	boolean changePmLimit(Integer pm10Limit, Integer pm25Limit, Long user, Integer station);
	boolean delectFavoriteStation( Long user, Integer station );
	/**
	 * �־��� �����Ҹ� ������������ �߰��� ������� �̸����� ��Ƽ� ��ȯ��
	 * @param pmdata
	 * @return
	 */
	List<String> getfavEmail(Pmdata pmdata );
	
	
	
/*	UserDto getUser(UserDto vo);
	
	UserDto getEmailCheck(UserDto vo);
	
	int insertUser(UserDto vo);
*/
}

