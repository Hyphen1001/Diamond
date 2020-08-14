package ghlzblog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ghlzblog.po.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{

}
