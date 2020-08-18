package ghlzblog.service;

import java.util.List;
import ghlzblog.po.Comment;

public interface CommentService {

	void AddComment(Comment comment);


	List<Comment> finddoccomment(Long docid);


	List<Comment> findusercomment(Long uid);

}
