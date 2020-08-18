package ghlzblog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ghlzblog.po.Collection;

public interface CollectionRepository extends JpaRepository<Collection,Long>{
	List<Collection> findByUid(Long uid);

	List<Collection> findByDocid(Long docid);

	Collection findByUidAndDocid(Long uid, Long docid);


}
