package ghlzblog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ghlzblog.po.Group;

public interface GroupRepository extends JpaRepository<Group,Long>{
	Group findByGid(Long gid);

	Group findByGroupname(String groupname);
}
