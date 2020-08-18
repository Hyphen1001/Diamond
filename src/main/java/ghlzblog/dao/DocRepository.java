package ghlzblog.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ghlzblog.po.Doc;

public interface DocRepository extends JpaRepository<Doc,Long>{
		Doc findByDocid(Long Docid);

		List<Doc> findByCreateuid(Long uid);

		List<Doc> findByRankgid(Long gid);

}
