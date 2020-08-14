package ghlzblog.service;

import ghlzblog.po.User;

public interface UserService {
	 void AddUser(String username,String password,String avatar,String email);

	User checkUser(String username);

	User checkUser(String username, String password);
}
