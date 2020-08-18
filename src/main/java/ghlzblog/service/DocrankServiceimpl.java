package ghlzblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ghlzblog.dao.DocrankRepository;
import ghlzblog.po.Docrank;


@Service
public class DocrankServiceimpl implements DocrankService{
	@Autowired
    private DocrankRepository docrankRepository;
	
	@Override
	public 	void AddDocrank(Docrank docrank){
		docrankRepository.saveAndFlush(docrank);
		return;
	}
	@Override
	public List<Docrank> findDocrank(long docid){
		return docrankRepository.findByDocid(docid);
	}
}
