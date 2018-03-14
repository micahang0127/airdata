package github.hangming.airdata.dao;

import github.hangming.airdata.model.UserDto;

public interface IUserDao {

	UserDto login ( String email, String password );
}
