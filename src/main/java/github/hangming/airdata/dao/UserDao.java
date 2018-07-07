package github.hangming.airdata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import github.hangming.airdata.dto.FavorStationDto;
import github.hangming.airdata.dto.Pmdata;
import github.hangming.airdata.model.UserDto;

@Repository
public class UserDao implements IUserDao {

	@Autowired
	private SqlSession session;
	
	public UserDto getUser(String email, String password) {
		//IUserDao dao = session.getMapper(IUserDao.class);
		// 1. email+ password = > userDto
		// 2. HashMap 
//		UserDto u = new UserDto();
//		u.setEmail(email);
//		u.setPassword(password);
		Map<String, String> u = new HashMap<String, String>();
		u.put("e", email);        // �ڡڡ�  Mapper���� #{e} �ȿ� �� ���ڿ��� ���ƾ� �Ѵ�!!! 
		u.put("p", password);
		return session.selectOne("UserMapper.getUser", u);
	}
	

	
	public UserDto getEmailCheck(String email){  // ȸ�����Խ� �̸��� �ߺ�Ȯ��
		//IUserDao dao = session.getMapper(IUserDao.class);
		return session.selectOne("UserMapper.getEmailCheck", email);
	}
	
	public UserDto insertUser(UserDto vo){
		//IUserDao dao = session.getMapper(IUserDao.class);
		if ( getEmailCheck(vo.getEmail()) != null ) {
			// existing email!
			throw new RuntimeException("�����ϴ� �̸���: " + vo.getEmail());
		}
		session.insert("UserMapper.insertUser", vo);
		return vo;
	}


	
	public UserDto changePw(UserDto vo) {
		session.update("UserMapper.changePw", vo);
		return vo;
	}



	@Override
	public boolean addFavoriteStation( Long user, Integer station,Integer pm10_limit, Integer pm25_limit) {
		// user.getSeq();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user", user);
		param.put("station", station);
		
		System.out.println("���ɵ�� �߰�"+ param.entrySet() );
		
		param.put("pm10_limit", pm10_limit);
		param.put("pm25_limit", pm25_limit);
		session.insert("addFavoriteStation", param);
		return true;
	}



	@Override
	public List<Integer> getFavoriteStations(Long userSeq) {
		
		return session.selectList("UserMapper.getFavoriteStations", userSeq) ;
	}

	
	@Override
	public List<FavorStationDto> getFavoriteStationDetail(Long userSeq){
		
		return session.selectList("UserMapper.getFavoriteStationDetail", userSeq);
	}

	@Override
	public boolean changePmLimit(Integer pm10Limit, Integer pm25Limit, Long user, Integer station){
		
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("pm10Limit", pm10Limit);
		param.put("pm25Limit", pm25Limit);
		param.put("user", user);
		param.put("station", station);
		
		session.update("UserMapper.changePmLimit", param);
		
		return true;
	}
	
	

	@Override
	public boolean delectFavoriteStation(Long user, Integer station) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		System.out.println("���ɵ�� ����remove UserDao�κ�"+ user+","+ station);
		
		
	
		if(param.isEmpty()== true){
			System.out.println("Map ����ִ� ");
		}else{
			System.out.println("Map �� �������");
		}
		
		param.put("user", user);
		param.put("station", station);   // �ٽ� �־���� �ϳ���....(?)
		
		session.delete("delectFavoriteStation", param);
		
		System.out.println("���ɵ�� ����remove UserDao�κ�(remove��)"+ param.entrySet() );
		
		
		return true;
	}



	@Override
	public List<String> getfavEmail(Pmdata pmdata) {
		return session.selectList("StationMapper.findFavUser", pmdata);
	}
}
