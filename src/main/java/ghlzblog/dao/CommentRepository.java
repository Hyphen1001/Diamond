package ghlzblog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ghlzblog.po.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{

	List<Comment> findByDocid(Long docid);

	List<Comment> findByUid(Long uid);

}
