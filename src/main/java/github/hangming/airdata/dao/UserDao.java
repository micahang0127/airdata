package github.hangming.airdata.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import github.hangming.airdata.model.UserDto;

@Repository
public class UserDao implements IUserDao {

	@Autowired
	private SqlSession session;
	
	public List<UserDto> getUser(UserDto vo) {
		//IUserDao dao = session.getMapper(IUserDao.class);
		return session.selectList("UserMapper.getUser", vo);
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


	


	
}
