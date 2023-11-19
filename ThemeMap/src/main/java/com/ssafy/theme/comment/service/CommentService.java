package com.ssafy.theme.comment.service;

import java.util.List;

import com.ssafy.theme.comment.dto.CommentDto;

public interface CommentService {
	void registComment(CommentDto commentDto) throws Exception;
	
	List<CommentDto> commentsOfPlace(String placeId) throws Exception;
	
}
