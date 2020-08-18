package ghlzblog.service;

import java.util.List;

import ghlzblog.po.Collection;

public interface CollectionService {
	List<Collection> findusercollection(Long uid);

	void AddCollection(Collection collection);

	List<Collection> findbydocid(Long docid);

	Collection findbyUidAndDocid(Long id, Long docid);

	void delete(Collection collection);

	void deletebydocid(Long docid);
}
