package ghlzblog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ghlzblog.po.Inform;


public interface InformRepository  extends JpaRepository<Inform,Long>{
	List<Inform> findByUid(Long uid);

	Inform findByInid(Long inid);
}
