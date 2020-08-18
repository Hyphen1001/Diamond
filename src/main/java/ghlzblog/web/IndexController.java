package ghlzblog.web;


import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ghlzblog.dao.UserRepository;
import ghlzblog.po.Session;
import ghlzblog.po.User;
import ghlzblog.service.SessionService;
import ghlzblog.service.UserService;



@Controller
@CrossOrigin
@ResponseBody
public class IndexController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/login")
	public Result login(@RequestParam String username,@RequestParam String password) {
		User user = userService.checkUser(username, password);
	    if (user != null) {	
	    	Long uid = user.getId();
	    	user.setUser_id(uid+10000);
	    	userRepository.saveAndFlush(user);
	    	Session s = new Session();
	    	s.setUid(uid);
	    	s.setTime(new Date());
	    	String sid =sessionService.getsid();
	    	s.setSid(sid);
	    	sessionService.DeleteSession(uid);
	    	sessionService.AddSession(s);
		   	user.setPassword(null);
		   	return Result.ok(sid,user);
	   } 
	   else
		   return Result.fail("用户名或密码错误");
	}
}