package github.hangming.airdata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
		u.put("e", email);
		u.put("p", password);
		return session.selectOne("UserMapper.getUser", u);
	}
	

	
	public UserDto getEmailCheck(String email){  // 회원가입시 이메일 중복확인
		//IUserDao dao = session.getMapper(IUserDao.class);
		return session.selectOne("UserMapper.getEmailCheck", email);
	}
	
	public UserDto insertUser(UserDto vo){
		//IUserDao dao = session.getMapper(IUserDao.class);
		session.insert("UserMapper.insertUser", vo);
		return vo;
	}


	
	public UserDto changePw(UserDto vo) {
		session.update("UserMapper.changePw", vo);
		return vo;
	}



	@Override
	public boolean addFavoriteStation( Long user, Integer station) {
		// user.getSeq();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user", user);
		param.put("station", station);
		// TODO 사용자한테서 미세먼지 기준값을 입력받아야함
		param.put("pm10_limit", 80);
		param.put("pm25_limit", 40);
		session.insert("addFavoriteStation", param);
		return true;
	}



	@Override
	public List<Integer> getFavoriteStations(Long userSeq) {
		
		return session.selectList("UserMapper.getFavoriteStations", userSeq) ;
	}
}
