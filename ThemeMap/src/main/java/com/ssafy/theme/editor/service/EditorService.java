package com.ssafy.theme.editor.service;

import java.util.List;

import com.ssafy.theme.editor.dto.EditorDto;

public interface EditorService {
	
	EditorDto login(EditorDto editorDto) throws Exception;
	EditorDto editorInfo(String id) throws Exception;
	int regist(EditorDto editorDto);
	void saveRefreshToken(String id, String token) throws Exception;
	Object getRefreshToken(String id) throws Exception;
	void deleteRefreshToken(String id) throws Exception;
	int modify(EditorDto editorDto);
	int resign(String id);
	String getSalt(String id);
	List<EditorDto> power() throws Exception;

}
