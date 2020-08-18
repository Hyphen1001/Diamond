package ghlzblog.web;


import java.util.ArrayList;
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

import ghlzblog.dao.DocRepository;
import ghlzblog.po.Collection;
import ghlzblog.po.Comment;
import ghlzblog.po.Doc;
import ghlzblog.po.Docrank;
import ghlzblog.po.Grouprecord;
import ghlzblog.po.Inform;
import ghlzblog.po.User;
import ghlzblog.po.View;
import ghlzblog.service.CollectionService;
import ghlzblog.service.CommentService;
import ghlzblog.service.DocService;
import ghlzblog.service.DocrankService;
import ghlzblog.service.GrouprecordService;
import ghlzblog.service.InformService;
import ghlzblog.service.SessionService;
import ghlzblog.service.UserService;
import ghlzblog.service.ViewService;

@Controller
@CrossOrigin
@ResponseBody
public class DocController {
	@Autowired
	private DocService docService;
    @Autowired
    private DocRepository docRepository;
	@Autowired
	private ViewService viewService;
	@Autowired
	private CollectionService collectService;
	@Autowired
	private CommentService commentService;
    @Autowired 
	private GrouprecordService grouprecordService;
    @Autowired
	private DocrankService docrankService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;
    @Autowired
    private InformService informService;
    
	@PostMapping("/{sid}/createdoc/create")
	public Result createDoc(@RequestParam String title,
			@RequestParam String content,
			@PathVariable String sid,
		    HttpSession session,
		     RedirectAttributes attributes
			) {

		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
		Long uid = user.getId();
		Doc doc =new Doc();
		if(title.length()<=0||title.length()>16) {
			return Result.fail("标题应在0-16位之间");
		}
		else if(content.length()<=0) {
			return Result.fail("内容不能为空");
		}
		doc.setContent(content);
		doc.setCreateuid(uid);
		doc.setDrank(0);
		doc.setFlag(1);
		doc.setRankgid(0);
		doc.setChangcnt(0L);
		doc.setTime(new Date());
		doc.setTitle(title);
		doc.setnotchange();
		docService.AddDoc(doc);
		sessionService.setSession(sid);
		return Result.ok(sid,doc);
	}

	@PostMapping("/{sid}/{docid}/viewdoc")
	public Result viewDoc(@PathVariable String sid,@PathVariable long docid,HttpSession session,
						  RedirectAttributes attributes) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
		Long uid = user.getId();
		Doc doc = docService.checkDoc(docid);
		if(doc.getFlag()==0) {
			return Result.fail("文章已被删除");
		}
		Long rank=getQualified(uid, docid);
		if(rank<1L) {

			return Result.fail("权限不够");
		}
		List<Object> answer = new ArrayList<Object>();
		Userand useranddoc = new Userand();
		useranddoc.setObject(doc);
		useranddoc.setUser(userService.checkUser(uid));
		answer.add(useranddoc);
		List<Comment> commentlist = commentService.finddoccomment(docid);
		for(Comment c : commentlist) {
			Userand userandcomment = new Userand();
			userandcomment.setUser(userService.checkUser(uid));
			userandcomment.setObject(c);
			answer.add(userandcomment);
		}
		View tmpview = viewService.findbyUidAndDocid(uid,docid);
		if(tmpview !=null) {
			viewService.detele(tmpview);
		}
		View view = new View();
		view.setDocid(docid);
		view.setUid(uid);
		view.setTime(new Date());
		viewService.AddView(view);
		sessionService.setSession(sid);
		return Result.ok(sid,answer);
	}

	@PostMapping("/{sid}/{docid}/collectdoc")
	public Result collectdoc(@PathVariable String sid,@PathVariable long docid,HttpSession session,
							 RedirectAttributes attributes) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
		Long uid = user.getId();
		Doc doc = docService.checkDoc(docid);
		if(doc.getFlag()==0) {
			return Result.fail("文章已被删除");
		}
		Collection testcollection = collectService.findbyUidAndDocid(uid, docid);
		if(testcollection !=null) {
			return Result.fail("文章已被收藏");
		}
		Collection collection = new Collection();
		collection.setDocid(docid);
		collection.setUid(uid);
		collectService.AddCollection(collection);
		sessionService.setSession(sid);
		return Result.ok(sid,collection);
	}

	@PostMapping("/{sid}/{docid}/cancelcollectdoc")
	public Result cancelcollectdoc(@PathVariable String sid,@PathVariable Long docid,Model model) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
		Long uid = user.getId();
		Collection collection = collectService.findbyUidAndDocid(uid, docid);
		if(collection==null) {
			return Result.fail("没有收藏该文章");
		}
		collectService.delete(collection);
		return Result.ok(sid);
	}
	@PostMapping("/{sid}/{docid}/commentdoc")
	public Result commentdoc(@PathVariable String sid,@PathVariable long docid,HttpSession session,
			@RequestParam String content,RedirectAttributes attributes) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
		Long uid = user.getId();
		Comment comment = new Comment();
		if(content.length()<=0) {
			return Result.fail("评论不能为空");
		}
		Long rank=getQualified(uid, docid);
		if(rank<2L) {
			return Result.fail("权限不够");
		}
		comment.setContent(content);
		comment.setDocid(docid);
		comment.setUid(uid);
		comment.setTime(new Date());
		commentService.AddComment(comment);
		sessionService.setSession(sid);
		Doc doc = docService.findbydocid(docid);
		String message = user.getUsername() + "评论了你的文章" + doc.getTitle();
		Inform inform =  new Inform();
		inform.setUid(doc.getCreateuid());
		inform.setMessage(message);
		inform.setTime(new Date());
		inform.setFlag(1L);
		inform.setDocid(doc.getDocid());
		inform.setGid(0L);
		informService.addInform(inform);
		return Result.ok(sid,comment);
	}
	
	@PostMapping("/{sid}/{docid}/editdoc")
	public Result editdoc(@PathVariable String sid,@PathVariable long docid,HttpSession session) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
		Long uid = user.getId();
		Doc doc = docService.checkDoc(docid);
		if(doc.getFlag()==0) {
			return Result.fail("文章已被删除");
		}
		Long rank=getQualified(uid, docid);
		if(rank<3L) {

			return Result.fail("权限不够");
		}
		doc.setchange();
		docRepository.saveAndFlush(doc);
		List<Object> answer = new ArrayList<Object>();
		Userand useranddoc = new Userand();
		useranddoc.setObject(doc);
		useranddoc.setUser(userService.checkUser(uid));
		answer.add(useranddoc);
		List<Comment> commentlist = commentService.finddoccomment(docid);
		for(Comment c : commentlist) {
			Userand userandcomment = new Userand();
			userandcomment.setUser(userService.checkUser(uid));
			userandcomment.setObject(c);
			answer.add(userandcomment);
		}
		View tmpview = viewService.findbyUidAndDocid(uid,docid);
		if(tmpview !=null) {
			viewService.detele(tmpview);
		}
		View view = new View();
		view.setDocid(docid);
		view.setUid(uid);
		view.setTime(new Date());
		viewService.AddView(view);
		sessionService.setSession(sid);
		return Result.ok(sid,answer);
	}
	@PostMapping("/{sid}/{docid}/changedoc")
	public Result changedoc(@PathVariable String sid,@PathVariable long docid,HttpSession session,
			@RequestParam String content,RedirectAttributes attributes) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
		Long uid = user.getId();
		Doc doc = docService.findbydocid(docid);
		Long rank=getQualified(uid, docid);
		if(rank<3L) {
			return Result.fail("权限不够");
		}
		doc.setContent(content);
		doc.setChangcnt(doc.getChangcnt()+1);
		doc.setTime(new Date());
		doc.setnotchange();
		docRepository.save(doc);
		sessionService.setSession(sid);
		String message = user.getUsername() + "修改了你的文章" + doc.getTitle();
		Inform inform = new Inform();
		inform.setUid(doc.getCreateuid());
		inform.setMessage(message);
		inform.setTime(new Date());
		inform.setFlag(1L);
		inform.setDocid(docid);
		inform.setGid(0L);
		return Result.ok(sid,doc);
	}
	
	@PostMapping("/{sid}/{docid}/{gid}/setrank")
	public Result setrank(@PathVariable String sid,@PathVariable long docid,
			@PathVariable long gid,
			@RequestParam long drank,
			HttpSession session,
			RedirectAttributes attributes,Model model) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		User user = sessionService.getUser(sid);
		Long uid = user.getId();
			Doc doc = docService.findbydocid(docid);
			if(doc.getCreateuid()!=uid) {
				return Result.fail("该用户不能为该文章设置权限");
			}
			Docrank docrank = new Docrank();
			docrank.setDocid(docid);
			docrank.setGid(gid);
			docrank.setDrank(drank);
			docrankService.AddDocrank(docrank);
			sessionService.setSession(sid);
			return Result.ok(sid,"权限设置成功");
	}
	public Long getQualified(long uid,long docid) {
		Long rank = 0L;
		Doc doc = docService.findbydocid(docid);
		if(doc.getCreateuid()==uid)
			return 3L;
		List<Docrank> docranklist = docrankService.findDocrank(docid);
		if(docranklist==null)
			return 0L;

		List<Grouprecord> grouprecordlist =  grouprecordService.findgrbyuid(uid);
		if(grouprecordlist==null) {
			for(Docrank d:docranklist) {
				if(d.getGid()==0)
					rank = Math.max(rank, d.getDrank());
			}
		}
		else {
			for(Docrank d:docranklist) {
				for(Grouprecord g:grouprecordlist) {
					if(g.getGid()==d.getGid()) {
						rank =Math.max(rank, d.getDrank());
					}
					if(rank==3L)
						break;
				}
				if(rank==3L)
					break;
			}
		}
		return rank;
	}
	@PostMapping("/{sid}/{docid}/share")
	 public Result share(@PathVariable String sid,@PathVariable long docid,HttpSession session) {
		if(!sessionService.isAvailable(sid)) {
			return Result.fail("该用户未登陆或者登陆超时");
		}
		sessionService.setSession(sid);
	   return Result.ok(sid,docid);
	 }
	

	 

}

