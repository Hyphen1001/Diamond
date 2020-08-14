package ghlzblog.web;

import javax.servlet.http.HttpSession;

import ghlzblog.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ghlzblog.po.User;
import ghlzblog.service.UserService;

class registdata {
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	private String password2;
	private String avatar;
	private String email;
}

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

			@RequestBody registdata registdata,
	       HttpSession session,
	       RedirectAttributes attributes) {

		String username = registdata.getUsername();
		String password = registdata.getPassword();
		String password2 = registdata.getPassword2();
		String email = registdata.getEmail();
		String avatar = registdata.getAvatar();
		System.out.println(avatar);
		if (username.length() < 6 || username.length() > 16)
		{
			return Result.fail(401,"用户名应在6-16位之间");
		}
		else if (password.length() < 6 || password.length() > 16)
		{
			return Result.fail(402,"密码应在6-16位之间");
		}
		else if (!password.equals(password2)) {
			return Result.fail(403,"两次密码不一致");
		}
		else if (!EmailLegal(email))
		{
			return Result.fail(405,"邮箱格式有误");
		}
		else {
			User user = userService.checkUser(username);
			if (user!=null)
			{
				return Result.fail(406,"用户名已存在");
			}
		}
		userService.AddUser(username,password,avatar,email);
	    return Result.succ();
	}
	

}
