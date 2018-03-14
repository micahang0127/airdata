package github.hangming.airdata.service;

import github.hangming.airdata.model.UserDto;

public interface IUserService {

	UserDto login ( String email, String password );
}
