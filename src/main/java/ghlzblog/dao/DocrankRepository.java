package ghlzblog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ghlzblog.po.Docrank;


public interface DocrankRepository extends JpaRepository<Docrank,Long>{

	List<Docrank> findByDocid(long docid);

}
