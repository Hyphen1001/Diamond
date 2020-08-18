package ghlzblog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ghlzblog.dao.CollectionRepository;
import ghlzblog.dao.CommentRepository;
import ghlzblog.dao.DocRepository;
import ghlzblog.dao.DocrankRepository;
import ghlzblog.dao.GroupRepository;
import ghlzblog.dao.GrouprecordRepository;
import ghlzblog.po.Collection;
import ghlzblog.po.Comment;
import ghlzblog.po.Doc;
import ghlzblog.po.Docrank;
import ghlzblog.po.Group;
import ghlzblog.po.Grouprecord;

@Service
public class GroupServiceimpl implements GroupService {
	
	@Autowired
    private GroupRepository groupRepository;
	
	@Autowired
	private CommentService commentService;
	
    @Autowired
    private DocRepository docRepository;
    
    @Autowired
    private DocrankRepository docrankRepository;
    
    @Autowired
    private DocrankService docrankService;
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private CollectionService collectService;
    
    @Autowired
    private GrouprecordService grouprecordService;
    
    @Autowired
    private CollectionRepository collectRepository;
    
    @Autowired
    private GrouprecordRepository grouprecordRepository;
    
	@Override
	public List<Group> findgrouplist(List<Grouprecord> grouprecordlist) {
		List<Group> grouplist=new ArrayList<Group>();

		for(int i=0;i<grouprecordlist.size();i++) {
			grouplist.add(groupRepository.findByGid(grouprecordlist.get(i).getGid()));
		}
		return grouplist;
	}

	@Override
	public Group findgroupbyid(Long gid) {
		return groupRepository.findByGid(gid);
	}

	@Override
	public void AddGroup(Long uid, String name, String message) {
		Group g=new Group();
		g.setCreateid(uid);
		g.setGroupname(name);
		g.setMeassage(message);
		groupRepository.saveAndFlush(g);
	}

	@Override
	public Group findbygroupname( String groupname) {
		return groupRepository.findByGroupname( groupname);
	}

	@Override
	public void deletegroup(Long gid) {
		List<Doc> dl=docRepository.findByRankgid(gid);
		for(int i=0;i<dl.size();i++) {
			List<Comment> cl=commentService.finddoccomment(dl.get(i).getDocid());
			for(int j=0;j<cl.size();j++) {
					commentRepository.delete(cl.get(j).getCommentid());
			}
			List<Docrank> dr=docrankService.findDocrank(dl.get(i).getDocid());
			for(int j=0;j<dr.size();j++) {
				docrankRepository.delete(dr.get(j));
			}
			List<Collection> clist=collectService.findbydocid(dl.get(i).getDocid());
			for(int j=0;j<clist.size();j++) {
				collectRepository.delete(clist.get(j));
			}
			docRepository.delete(dl.get(i).getDocid());
			
		}
		List<Grouprecord> gl=grouprecordService.findgrbygid(gid);
		for(int i=0;i<gl.size();i++) {
			grouprecordRepository.delete(gl.get(i));
		}
		groupRepository.delete(groupRepository.findByGid(gid));
	}
	


}
