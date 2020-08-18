package ghlzblog.service;

import java.util.List;

import ghlzblog.po.Grouprecord;
import ghlzblog.po.User;

public interface UserService {
	void AddUser(String username,String password,String avatar,String email);
	User checkUser(String username);
	
	User checkUser(Long id);
	User checkUser(String username, String password);
	
	List<User> findgroupuser(List<Grouprecord> gr);
}
