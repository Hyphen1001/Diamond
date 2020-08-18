package ghlzblog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ghlzblog.po.View;

public interface ViewRepository extends JpaRepository<View,Long> {
	List<View> findByUid(Long uid);

	View findByUidAndDocid(long uid, long docid);

	List<View> findByDocid(long docid);
}
