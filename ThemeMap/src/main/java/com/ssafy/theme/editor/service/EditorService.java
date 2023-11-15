package com.ssafy.theme.editor.service;

import com.ssafy.theme.editor.dto.EditorDto;

public interface EditorService {
	EditorDto login(EditorDto editorDto) throws Exception;
	EditorDto editorInfo(String id) throws Exception;
	void saveRefreshToken(String id, String token) throws Exception;
	Object getRefreshToken(String id) throws Exception;
	void deleteRefreshToken(String id) throws Exception;
}
