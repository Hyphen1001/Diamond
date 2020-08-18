package ghlzblog.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ghlzblog.po.User;
import ghlzblog.service.UserService;


@Controller
@CrossOrigin
@ResponseBody
public class RegisterController {
	
	@Autowired
	private UserService userService;

	public boolean EmailLegal(String email)
	{
		String regex = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
		return email.matches(regex);
	}
	
	@PostMapping("/register")
	public Result regist(

			@RequestParam String username,
			@RequestParam String password,
			@RequestParam String password2,
			@RequestParam String email,
			@RequestParam String avatar,
	       HttpSession session,
	       RedirectAttributes attributes) {

/*		String username = registdata.getUsername();
		String password = registdata.getPassword();
		String password2 = registdata.getPassword2();
		String email = registdata.getEmail();
		String avatar = registdata.getAvatar();
*/
		if (username.length() < 6 || username.length() > 16)
		{
			return Result.fail("用户名应在6-16位之间");
		}
		else if (password.length() < 6 || password.length() > 16)
		{
			return Result.fail("密码应在6-16位之间");
		}
		else if (!password.equals(password2)) {
			return Result.fail("两次密码不一致");
		}
		else if (!EmailLegal(email))
		{
			return Result.fail("邮箱格式有误");
		}
		else {
			User user = userService.checkUser(username);
			if (user!=null)
			{
				return Result.fail("用户名已存在");
			}
		}
		userService.AddUser(username,password,avatar,email);
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setAvatar(avatar);
		user.setEmail(email);
	    return Result.ok(user);
	}
	

}
