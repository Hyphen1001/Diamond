package ghlzblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ghlzblog.dao.GrouprecordRepository;
import ghlzblog.po.Grouprecord;

@Service
public class GrouprecordServiceimpl implements GrouprecordService {

	@Autowired
    private GrouprecordRepository grouprecordRepository;

	@Override
	public List<Grouprecord> findgrbyuid(Long uid) {
		return grouprecordRepository.findByUid(uid);
	}
	@Override
	public List<Grouprecord> findgrbygid(Long gid) {
		return grouprecordRepository.findByGid(gid);
	}
	@Override
	public Grouprecord findbygidanduid(Long gid,Long uid) {
		return grouprecordRepository.findByGidAndUid(gid, uid);
	}
	@Override
	public void addgrouprecord(Long uid, Long gid) {
		Grouprecord gr=new Grouprecord();
		gr.setGid(gid);
		gr.setUid(uid);
		grouprecordRepository.saveAndFlush(gr);
	}

	@Override
	public void quitgroup(long gid, long uid) {
		grouprecordRepository.delete(grouprecordRepository.findByGidAndUid(gid, uid));

	}

}
