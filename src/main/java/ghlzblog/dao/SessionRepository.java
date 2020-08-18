package ghlzblog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ghlzblog.po.Session;


public interface SessionRepository extends JpaRepository<Session,Long>{

	Session findBySid(String sid);

	Session findByUid(Long uid);

}
