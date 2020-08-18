package ghlzblog.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ghlzblog.po.Doc;
import ghlzblog.po.Group;
import ghlzblog.po.Grouprecord;
import ghlzblog.po.User;
import ghlzblog.po.Inform;
import ghlzblog.service.DocService;
import ghlzblog.service.GroupService;
import ghlzblog.service.GrouprecordService;
import ghlzblog.service.InformService;
import ghlzblog.service.SessionService;
import ghlzblog.service.UserService;

@Controller
@CrossOrigin
@ResponseBody
public class GroupController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GrouprecordService grouprecordService;
	@Autowired
	private DocService docService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private InformService informService;
	
	@PostMapping("{sid}/{gid}/group")
	 public Result getmygroupmate(@PathVariable String sid,@PathVariable Long gid,Model model) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
        sessionService.setSession(sid);
	  return Result.ok(new Object() {
	   private List<User> user=userService.findgroupuser(grouprecordService.findgrbygid(gid));
	   private Group group=groupService.findgroupbyid(gid);
	   private List<Doc> doc=docService.findgid(gid);
	  });
	  
	 }
	@PostMapping("{sid}/{gid}/invite/{user_id}")
	public Result invite(@PathVariable String sid,@PathVariable Long gid,@PathVariable Long user_id,Model model) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user1=sessionService.getUser(sid);
		User user2=userService.checkUser(user_id-10000); 
		sessionService.setSession(sid);
		if(user2==null) {
			return Result.fail(sid,"邀请的用户不存在");
		}
		if(grouprecordService.findbygidanduid(gid, user2.getId())!=null) {
			return Result.fail(sid,"邀请的用户已在该组");
		}
		Inform inform=new Inform();
		String message=user1.getUsername()+"邀请你加入"+groupService.findgroupbyid(gid).getGroupname();
		inform.setMessage(message);
		inform.setDocid(0L);
		inform.setFlag(1L);
		inform.setGid(gid);
		inform.setTime(new Date());
		inform.setUid(user2.getId());
		informService.addInform(inform);
		return Result.ok(sid,gid);
	}
/*	@PostMapping("{sid}/{gid}/{user_id}/createdjoin")
	public Result createdjoin(@PathVariable String sid,@PathVariable Long gid,@RequestParam Long user_id,Model model,HttpSession session) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
        sessionService.setSession(sid);
        User user1 = userService.finduser_id(Long user_id){
        	
        }
	}
	*/
	@PostMapping("{sid}/{gid}/join")
	public Result join(@PathVariable String sid,@PathVariable Long gid,Model model,HttpSession session) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
        sessionService.setSession(sid);
		Long uid = user.getId();
		grouprecordService.addgrouprecord(user.getId(),gid);
		Group group = groupService.findgroupbyid(gid);
		Inform inform1 = new Inform();
		inform1.setFlag(1L);
		inform1.setTime(new Date());
		inform1.setUid(uid);
		inform1.setDocid(0L);
		inform1.setGid(0L);
		String message1 = "你成功加入组" + group.getGroupname();
		inform1.setMessage(message1);
		Inform inform2 = new Inform();
		inform2.setFlag(1L);
		inform2.setTime(new Date());
		inform2.setUid(group.getCreateid());
		inform2.setDocid(0L);
		inform2.setGid(0L);
		String message2 = user.getUsername() + "成功加入你创建的组" + group.getGroupname();
		inform2.setMessage(message2);
		informService.addInform(inform1);
		informService.addInform(inform2);
		return Result.ok("加入成功");
	}
	
	@PostMapping("{sid}/{gid}/manage/{uid}")
	 public Result managedc(@PathVariable String sid,@PathVariable Long gid,@PathVariable Long uid) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
        sessionService.setSession(sid);
		Long uid1 = user.getId();
		Group group = groupService.findgroupbyid(gid);
		if(uid1!=group.getCreateid()) {
			return Result.fail("该用户无权利踢人");
		}
	  grouprecordService.quitgroup(gid, uid);
		Inform inform = new Inform();
		inform.setFlag(1L);
		inform.setTime(new Date());
		inform.setUid(uid);
		String message = group.getGroupname() + "将你踢出小组";
		inform.setMessage(message);
		inform.setDocid(0L);
		inform.setGid(0L);
		informService.addInform(inform);
	  return Result.ok();
	 }
	
	@PostMapping("{sid}/{gid}/quit")
	public Result quit(@PathVariable String sid,@PathVariable Long gid,HttpSession session){
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
        sessionService.setSession(sid);
		
		Group g=groupService.findgroupbyid(gid);
		if(user.getId()==g.getCreateid()) {
			List<Grouprecord> recordlist = grouprecordService.findgrbygid(gid);
			for(Grouprecord r : recordlist) {
				Long tmpuid =r.getUid();
				User tmpuser = userService.checkUser(tmpuid);
				Inform inform = new Inform();
				inform.setFlag(1L);
				inform.setTime(new Date());
				inform.setUid(tmpuser.getId());
				String message = g.getGroupname() + "小组已解散";
				inform.setMessage(message);
				inform.setDocid(0L);
				inform.setGid(0L);
				informService.addInform(inform);
			}
			groupService.deletegroup(gid);
			return Result.ok("解散团队");
		}
		else {
			grouprecordService.quitgroup(gid, user.getId());
			Inform inform = new Inform();
			inform.setFlag(1L);
			inform.setTime(new Date());
			inform.setUid(g.getCreateid());
			String message = user.getUsername()+ "退出小组" + g.getGroupname();
			inform.setMessage(message);
			inform.setDocid(0L);
			inform.setGid(0L);
			informService.addInform(inform);
			return Result.ok("退出团队");
		}
	}
	
	@PostMapping("/{sid}/mygroup/new")
	public Result getmygroup(@PathVariable String sid, @RequestParam String name, @RequestParam String message,RedirectAttributes attributes) 
	{
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
		Long uid = user.getId();
		sessionService.setSession(sid);
		if(groupService.findbygroupname(name)==null) {
			groupService.AddGroup(uid, name, message);
			Group group = new Group();
			group.setCreateid(uid);
			group.setGroupname(name);
			group.setMeassage(message);
			grouprecordService.addgrouprecord(uid, groupService.findbygroupname(name).getGid());
			return Result.ok(sid,group);
		}	
		else {
			return Result.fail("组名已存在");
		}
	}
	@PostMapping("/{sid}/{docid}/{gid}/upload")
	public Result upload(@PathVariable String sid,@PathVariable long docid,@PathVariable long gid) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		Doc d=docService.findbydocid(docid);
		Doc dc=new Doc();
		dc.setContent(d.getContent());
		dc.setCreateuid(groupService.findgroupbyid(gid).getCreateid());
		dc.setRankgid(gid);
		dc.setTitle(d.getTitle());
		dc.setFlag(1);
		docService.AddDoc(dc);
		sessionService.setSession(sid);
		return Result.ok("上传成功");
	 }
}
