package ghlzblog.service;

import java.util.List;

import ghlzblog.po.Inform;

public interface InformService {

	void addInform(Inform inform);
	
	List<Inform> findbyUid(Long uid);

	Inform findbyInid(Long inid);

}
