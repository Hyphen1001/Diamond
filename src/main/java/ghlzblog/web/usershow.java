package ghlzblog.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ghlzblog.dao.UserRepository;
import ghlzblog.po.User;
import ghlzblog.service.SessionService;
import ghlzblog.service.UserService;
import ghlzblog.util.MD5Utils;

@Controller
@CrossOrigin
@ResponseBody
public class usershow {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
	private SessionService sessionService;
    
    @GetMapping("/{sid}/<uid>/usershow")
    public Result show(@PathVariable String sid, @PathVariable Long uid, HttpSession session,Model model,Pageable pageable) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user1 = sessionService.getUser(sid);
		Long uid1 = user1.getId();
        sessionService.setSession(sid);
        model.addAttribute("user",userService.checkUser(uid));
        User user = userService.checkUser(uid);

		return Result.ok(sid,user);
    }
    
    
    public boolean EmailLegal(String email)
    {
    	String regex = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
    	return email.matches(regex);
    }
    
    
    @PostMapping("{sid}/usershow/change/changenickname")
    public Result changenickname(@RequestParam String nickname,
      @PathVariable String sid,
      HttpSession session,
      RedirectAttributes attributes) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user1 = sessionService.getUser(sid);
		Long uid = user1.getId();
		if (nickname.length()>16||nickname.length()<6)
		{
			return Result.fail("用户名应在6~16为之间");
		}
		if (userService.checkUser(nickname)!=null)
		{
			return Result.fail("用户名已存在");
		}
     if (nickname.length()>16)
     {
    	 attributes.addFlashAttribute("message","请保证用户名小于16位");
      	 return Result.fail("请保证用户名小于16位");
     }

     User user = userService.checkUser(uid); 
     user.setUsername(nickname);
     userRepository.save(user);
     session.setAttribute("user",user);
     sessionService.setSession(sid);
        return Result.ok(sid,user);
    }
    
    
    @PostMapping("{sid}/usershow/change/changeemail")
    public Result changeemail(@RequestParam String email,
      @PathVariable String sid,
      HttpSession session,
      RedirectAttributes attributes) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user1 = sessionService.getUser(sid);
		Long uid = user1.getId();
     if (!EmailLegal(email)) {
    	 attributes.addFlashAttribute("message","邮箱格式有误");
      	 return Result.fail("邮箱格式有误");
     }
     User user = userService.checkUser(uid); 
     user.setEmail(email);
     userRepository.save(user);
     session.setAttribute("user",user);
     sessionService.setSession(sid);
        return Result.ok(sid,user);
    }
    @PostMapping("{sid}/usershow/change/changeavatar")
    public Result changeavatar(@RequestParam String avatar,
      @PathVariable String sid,
      HttpSession session,
      RedirectAttributes attributes) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user1 = sessionService.getUser(sid);
		Long uid = user1.getId();
     User user = userService.checkUser(uid); 
     user.setAvatar(avatar);
     userRepository.save(user);
     session.setAttribute("user",user);
     sessionService.setSession(sid);
        return Result.ok(sid,user);
    }

	@PostMapping("{sid}/usershow/change/changemessage")
	public Result changemessage(@RequestParam String message,
								@PathVariable String sid,
								HttpSession session,
								RedirectAttributes attributes) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user1 = sessionService.getUser(sid);
		Long uid = user1.getId();
		if(message==null) {
			attributes.addFlashAttribute("message", "输入为空");
			return Result.fail("输入不能为空");
		}
		User user = userService.checkUser(uid);
		user.setUmessage(message);
		userRepository.save(user);
		session.setAttribute("user",user);
		sessionService.setSession(sid);
		return Result.ok(sid,user);
	}
    
    
    @PostMapping("{sid}/usershow/change/changepass")
    public Result changepass(@RequestParam String oldpw,
    		@RequestParam String newpw,
    		@RequestParam String newpw2,
      @PathVariable String sid,
      RedirectAttributes attributes) { 
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user1 = sessionService.getUser(sid);
		Long uid = user1.getId();
    	User user = userService.checkUser(uid); 
    	if (!user.getPassword().equals(MD5Utils.code(oldpw)))
    	{
    		attributes.addFlashAttribute("message","原密码错误");
    		return Result.fail("原密码错误");
    	}
    	else if (!newpw.equals(newpw2))
    	{
    		attributes.addFlashAttribute("message","两次密码不一致");
    		return Result.fail("两次密码不一致");
    	}
    	else if (newpw.length()<6||newpw.length()>16)
    	{
    		attributes.addFlashAttribute("message","密码应在6-16位之间");
    		return Result.fail("密码应在6-16位之间");
    	}
    	user.setPassword(MD5Utils.code(newpw));
    	userRepository.save(user); 
    	attributes.addFlashAttribute("message","密码修改成功");
    	sessionService.setSession(sid);
		return Result.ok(sid,"密码修改成功");
    }
}

