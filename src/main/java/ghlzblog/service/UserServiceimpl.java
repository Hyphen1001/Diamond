package ghlzblog.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ghlzblog.dao.UserRepository;
import ghlzblog.po.Grouprecord;
import ghlzblog.po.User;
import ghlzblog.util.MD5Utils;

@Service
public class UserServiceimpl implements UserService {
	
	@Autowired
    private UserRepository userRepository;

	@Override
	public void AddUser(String username,String password,String avatar,String email) {
		User user1 = new User();
	    user1.setUsername(username);
	    user1.setPassword(MD5Utils.code(password));
	    user1.setAvatar(avatar);
	    user1.setEmail(email);
	    user1.setUmessage("这个人很懒，什么也没有写");
	    user1.setloginTime(new Date());
	    userRepository.saveAndFlush(user1);
	    return;
	 }

	@Override
	public User checkUser(String username) {
		User user = userRepository.findByUsername(username);
        return user;
    }
	
	@Override
	public User checkUser(String username, String password) {
		User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
		return user;
	}
	
	@Override
    public User checkUser(Long id) {
        User user = userRepository.findByUid(id);
        return user;
    }
	
	@Override
	public List<User> findgroupuser(List<Grouprecord> gr) {
		List<User> userlist=new ArrayList<User>();
		for(int i=0;i<gr.size();i++) {
			userlist.add(userRepository.findByUid(gr.get(i).getUid()));
		}
		return userlist;
	}
}
