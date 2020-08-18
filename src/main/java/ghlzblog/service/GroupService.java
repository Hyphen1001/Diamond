package ghlzblog.service;

import java.util.List;

import ghlzblog.po.Group;
import ghlzblog.po.Grouprecord;

public interface GroupService {
	List<Group> findgrouplist(List<Grouprecord> grouplist);
	Group findgroupbyid(Long gid);
	
	void AddGroup(Long uid,String name,String message);
	
	Group findbygroupname(String groupname);
	
	void deletegroup(Long gid);
}
