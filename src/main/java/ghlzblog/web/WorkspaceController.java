package ghlzblog.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ghlzblog.dao.DocRepository;
import ghlzblog.dao.InformRepository;
import ghlzblog.po.Comment;
import ghlzblog.po.Doc;
import ghlzblog.po.Inform;
import ghlzblog.po.User;
import ghlzblog.po.View;
import ghlzblog.service.CollectionService;
import ghlzblog.service.CommentService;
import ghlzblog.service.DocService;
import ghlzblog.service.GroupService;
import ghlzblog.service.GrouprecordService;
import ghlzblog.service.InformService;
import ghlzblog.service.SessionService;
import ghlzblog.service.UserService;
import ghlzblog.service.ViewService;


@Controller
@CrossOrigin
@ResponseBody
public class WorkspaceController {
	
	@Autowired
	private DocService docService;
	@Autowired
	private DocRepository docRepository;
	
	@Autowired
	private CollectionService collectService;
	
	@Autowired
	private ViewService viewService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
	@Autowired
	private GrouprecordService grouprecordService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private InformService informService;
	@Autowired
	private InformRepository informRepository;
	
	@PostMapping("/{sid}/createdoc")
	public Result createdoc(@PathVariable String sid,Model model) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		else
			return Result.ok();
	}
	
	@PostMapping("/{sid}/vieweddoc")
	public Result vieweddoc(@PathVariable String sid,Model model) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
		Long uid = user.getId();
		List<View> viewlist=viewService.findrecentviewbyuid(uid, 5);
		List<Doc> doclist=docService.findrecentviewdoc(viewlist);
		if(doclist.size()==0)
			return Result.fail("该用户无浏览记录或者浏览的文档已删除");
		List<Userand> useranddoc=new ArrayList<Userand>();
		for(int i=0;i<doclist.size();i++) {
			Userand ud=new Userand();
			ud.setUser(userService.checkUser(doclist.get(i).getCreateuid()));
			ud.setObject(doclist.get(i));
			useranddoc.add(ud);
		}
		sessionService.setSession(sid);
		return Result.ok(sid,doclist);
	}
	
	@PostMapping("/{sid}/collectdoc")
	public Result  collectdoc(@PathVariable String sid,Model model) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
		Long uid = user.getId();
		List<Doc> doclist=docService.findcollectdoc(collectService.findusercollection(uid));
		if(doclist.size()==0)
			return Result.fail("该用户无收藏文档或者收藏的文档已删除");
		List<Userand> useranddoc=new ArrayList<Userand>();
		for(int i=0;i<doclist.size();i++) {
			Userand ud=new Userand();
			ud.setUser(userService.checkUser(doclist.get(i).getCreateuid()));
			ud.setObject(doclist.get(i));
			useranddoc.add(ud);
		}
		sessionService.setSession(sid);
		return Result.ok(sid,doclist);
	}
	
	@PostMapping("/{sid}/mygroup")
	public Result getmygroup(@PathVariable String sid,Model model) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}

		User user = sessionService.getUser(sid);
		Long uid = user.getId();
		model.addAttribute("mygrouplist",groupService.findgrouplist(grouprecordService.findgrbyuid(uid)));
		sessionService.setSession(sid);
		return Result.ok(sid,groupService.findgrouplist(grouprecordService.findgrbyuid(uid)));
	}
	
	@PostMapping("/{sid}/mycreatedoc")
	 public Result mydoc(@PathVariable String sid,Model model) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
		Long uid = user.getId();
		List<Doc> doclist=docService.findmydoc(uid);
		if(doclist.size()==0)
			return Result.fail("该用户无文档或者文档已删除");
		List<Userand> useranddoc=new ArrayList<Userand>();
		for(int i=0;i<doclist.size();i++) {
			Userand ud=new Userand();
			ud.setUser(userService.checkUser(doclist.get(i).getCreateuid()));
			ud.setObject(doclist.get(i));
			useranddoc.add(ud);
		}
		sessionService.setSession(sid);
		return Result.ok(sid,doclist);
	 }
	 
	 @PostMapping("/{sid}/{docid}/delete")
	 public Result mydocdelete(@PathVariable Long docid,@PathVariable String sid,Model model) {
			if(!sessionService.isAvailable(sid)) {
				return Result.fail("该用户未登陆或者登陆超时");
			}

	  Doc doc=docService.findbydocid(docid);
	  doc.setFlag(0);
	  docRepository.saveAndFlush(doc);
		 viewService.deletebydocid(docid);
		 collectService.deletebydocid(docid);
	  sessionService.setSession(sid);
	  return Result.ok(sid,"删除成功");
	 }
	 
	 @PostMapping("/{sid}/{docid}/back")
	 public Result docback(@PathVariable Long docid,@PathVariable String sid,Model model) {
			if(!sessionService.isAvailable(sid)) {
				return Result.fail("该用户未登陆或者登陆超时");
			}
		
	  Doc doc=docService.findbydocid(docid);
	  doc.setFlag(1);
	  docRepository.saveAndFlush(doc);
	  sessionService.setSession(sid);
	  return Result.ok(sid,"恢复成功");
	 }
	 
	 @PostMapping("/{sid}/recycle")
	 public Result recycle(@PathVariable String sid,Model model) {
			if(!sessionService.isAvailable(sid)) {
				return Result.fail("该用户未登陆或者登陆超时");
			}
			User user = sessionService.getUser(sid);
			Long uid = user.getId();
			List<Doc> doclist=docService.findrecycledoc(uid);
			if(doclist.size()==0)
				return Result.fail("回收站无文档");
			List<Userand> useranddoc=new ArrayList<Userand>();
			for(int i=0;i<doclist.size();i++) {
				Userand ud=new Userand();
				ud.setUser(userService.checkUser(doclist.get(i).getCreateuid()));
				ud.setObject(doclist.get(i));
				useranddoc.add(ud);
			}
			sessionService.setSession(sid);
			return Result.ok(sid,doclist);
	 }

	 @PostMapping("/{sid}/mycomment")
	 public Result comment(@PathVariable String sid,Model model) {
			if(!sessionService.isAvailable(sid)) {
				return Result.fail("该用户未登陆或者登陆超时");
			}
			User user = sessionService.getUser(sid);
			Long uid = user.getId();
			List<Comment> commentlist=commentService.findusercomment(uid);
			if(commentlist.size()==0)
				return Result.fail("该用户无评论");
			List<Userand> userandcomment=new ArrayList<Userand>();
			for(int i=0;i<commentlist.size();i++) {
				Userand ud=new Userand();
				ud.setUser(userService.checkUser(commentlist.get(i).getUid()));
				ud.setObject(commentlist.get(i));
				userandcomment.add(ud);
			}
			sessionService.setSession(sid);
			return Result.ok(sid,userandcomment);
	 }

	@PostMapping("/{sid}/mycomment/{docid}")
	public Result comment(@PathVariable String sid,
						  @PathVariable String docid, Model model) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
		Long uid = user.getId();
		List<Comment> commentlist=commentService.finddoccomment(Long.parseLong(docid));

		List<Userand> userandcomment=new ArrayList<Userand>();
		for(int i=0;i<commentlist.size();i++) {
			Userand ud=new Userand();
			ud.setUser(userService.checkUser(commentlist.get(i).getUid()));
			ud.setObject(commentlist.get(i));
			userandcomment.add(ud);
		}
		sessionService.setSession(sid);
		return Result.ok(sid,userandcomment);
	}

	 @PostMapping("/{sid}/recycle/{docid}")
	  public Result delete(@PathVariable String sid,@PathVariable Long docid,Model model) {
			if(!sessionService.isAvailable(sid)) {
				return Result.fail("该用户未登陆或者登陆超时");
			}
			User user = sessionService.getUser(sid);
			Long uid = user.getId();
			sessionService.setSession(sid);
	   if(docService.deletedoc(uid, docid)==1)
	    return Result.ok();
	   else
	    return Result.fail();
	  }
	  
	  @PostMapping("/{sid}/recycle/deleteall")
	  public Result deleteall(@PathVariable String sid,Model model) {
			if(!sessionService.isAvailable(sid)) {
				return Result.fail("该用户未登陆或者登陆超时");
			}
			User user = sessionService.getUser(sid);
			Long uid = user.getId();
			sessionService.setSession(sid);
			docService.deleteall(uid);
			return Result.ok();
	  }
	  
	  @PostMapping("/{sid}/inform")
	  public Result inform(@PathVariable String sid) {
			if(!sessionService.isAvailable(sid)) {
				return Result.fail("该用户未登陆或者登陆超时");
			}
			User user = sessionService.getUser(sid);
			Long uid = user.getId();
			sessionService.setSession(sid);
			List<Inform> informlist = informService.findbyUid(uid);
			System.out.println(informlist.size());
			return Result.ok(sid, informlist);
	  }
	  @PostMapping("/{sid}/{inid}/viewinform")
	  public Result viewinform(@PathVariable String sid,@PathVariable Long inid) {
			if(!sessionService.isAvailable(sid)) {
				return Result.fail("该用户未登陆或者登陆超时");
			}
			sessionService.setSession(sid);
			Inform inform = informService.findbyInid(inid);
			inform.setFlag(0L);
			informRepository.saveAndFlush(inform);
			return Result.ok(sid);
	  }
	  @PostMapping("/{sid}/viewallinform")
	  public Result viewallinform(@PathVariable String sid) {
			if(!sessionService.isAvailable(sid)) {
				return Result.fail("该用户未登陆或者登陆超时");
			}
			User user = sessionService.getUser(sid);
			Long uid = user.getId();
			sessionService.setSession(sid);
			List<Inform> informlist = informService.findbyUid(uid);
			for(Inform i : informlist) {
				i.setFlag(0L);
				informRepository.saveAndFlush(i);
			}
			return Result.ok(sid);
	  }
}
