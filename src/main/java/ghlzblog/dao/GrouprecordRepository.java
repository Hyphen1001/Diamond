package ghlzblog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ghlzblog.po.Grouprecord;

public interface GrouprecordRepository extends JpaRepository<Grouprecord,Long>{
		List<Grouprecord> findByUid(Long uid);

		List<Grouprecord> findByGid(Long gid);

		Grouprecord findByGidAndUid(long gid, long uid);
}
