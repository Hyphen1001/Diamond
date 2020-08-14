package ghlzblog.web;

import javax.servlet.http.HttpSession;

import ghlzblog.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ghlzblog.po.User;
import ghlzblog.service.UserService;

import java.io.Serializable;

class logindata implements Serializable {
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

	private String username;
	private String password;

}

@Controller
@CrossOrigin
@ResponseBody
public class IndexController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public Result login(@RequestBody logindata logind,
	       HttpSession session,
	       RedirectAttributes attributes) {

		String username = logind.getUsername();
		String password = logind.getPassword();

	   User user = userService.checkUser(username, password);
	   if (user != null) {
		   System.out.println("succ");
	       session.setAttribute("user",user);
	       User u = (User) session.getAttribute("user");
		   System.out.println(u.getUsername());
	       return Result.succ();
	   } else {
		   System.out.println("fail");
	       return Result.fail();
	   }
	}
}