package ghlzblog.service;

import java.util.List;

import ghlzblog.po.Grouprecord;

public interface GrouprecordService {
	List<Grouprecord> findgrbyuid(Long uid);
	List<Grouprecord> findgrbygid(Long gid);
	
	void addgrouprecord(Long uid,Long gid);
	 Grouprecord findbygidanduid(Long gid,Long uid);
	void quitgroup(long gid ,long uid);
}
