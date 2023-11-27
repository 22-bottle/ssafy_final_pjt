package com.ssafy.theme.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.theme.comment.dto.CommentDto;
import com.ssafy.theme.comment.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentMapper commentMapper;

	@Autowired
	public CommentServiceImpl(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}
	
	@Override
	public void registComment(CommentDto commentDto) throws Exception {
		commentMapper.registComment(commentDto);
	}
	
	@Override
	public List<CommentDto> commentsOfPlace(String placeId) throws Exception {
		return commentMapper.commentsOfPlace(placeId);
	}

}
