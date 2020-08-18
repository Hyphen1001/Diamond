package ghlzblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ghlzblog.dao.InformRepository;
import ghlzblog.po.Inform;
@Service
public class InformServiceimpl implements InformService{
	@Autowired
	private InformRepository informRepository;
	@Override
	public void addInform(Inform inform) {
		informRepository.saveAndFlush(inform);
		return;
	}
	@Override
	public List<Inform> findbyUid(Long uid){
		return informRepository.findByUid(uid);
	}
	@Override
	public Inform findbyInid(Long inid) {
		return informRepository.findByInid(inid);
	}
}
