package com.ssafy.theme.editor.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.theme.editor.dto.EditorDto;

@Mapper
public interface EditorMapper {
	
	EditorDto login(EditorDto editorDto) throws SQLException;
	EditorDto editorInfo(String id) throws SQLException;
	int regist(EditorDto editorDto);
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String id) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	int modify(EditorDto editorDto);
	int resign(String id);
	String getSalt(String id);

}
