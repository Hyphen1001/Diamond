package ghlzblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ghlzblog.dao.CommentRepository;
import ghlzblog.po.Comment;

@Service
public class CommentServiceimpl implements CommentService {

	@Autowired
    private CommentRepository commentRepository;
	public void AddComment(Comment comment) {
		commentRepository.saveAndFlush(comment);
		return;
	}
	@Override
	public List<Comment> finddoccomment(Long docid) {
		return commentRepository.findByDocid(docid);
	}
	@Override
	public List<Comment> findusercomment(Long uid){
		return commentRepository.findByUid(uid);
	}
}
