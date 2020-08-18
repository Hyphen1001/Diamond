package ghlzblog.service;

import java.util.List;

import ghlzblog.po.Docrank;

public interface  DocrankService {

	void AddDocrank(Docrank docrank);

	List<Docrank> findDocrank(long docid);

}
