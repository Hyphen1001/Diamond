package ghlzblog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ghlzblog.po.Doc;

public interface DocRepository extends JpaRepository<Doc,Long>{

}
