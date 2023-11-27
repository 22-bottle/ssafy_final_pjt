package com.ssafy.theme.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.theme.comment.dto.CommentDto;

@Mapper
public interface CommentMapper {

	void registComment(CommentDto commentDto) throws Exception;
	
	List<CommentDto> commentsOfPlace(String placeId) throws Exception;

}
