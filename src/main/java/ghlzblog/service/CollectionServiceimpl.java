package ghlzblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ghlzblog.dao.CollectionRepository;
import ghlzblog.po.Collection;

@Service
public class CollectionServiceimpl implements CollectionService {

	@Autowired
	private CollectionRepository collectRepository;

	@Override
	public List<Collection> findusercollection(Long uid) {
		return collectRepository.findByUid(uid);
	}

	@Override
	public void AddCollection(Collection collection) {
		collectRepository.saveAndFlush(collection);
		return;
	}

	@Override
	public List<Collection> findbydocid(Long docid) {
		return collectRepository.findByDocid(docid);
	}

	@Override
	public Collection findbyUidAndDocid(Long createuid,Long docid) {
		return collectRepository.findByUidAndDocid(createuid,docid);
	}

	@Override
	public void delete(Collection collection) {
		collectRepository.delete(collection);
		return;
	}

	@Override
	public void deletebydocid(Long docid) {
		List<Collection> collections = collectRepository.findByDocid(docid);
		for (int i = 0; i < collections.size(); i++) {
			collectRepository.delete(collections.get(i));
		}
	}
}
