package com.ssafy.theme.editor.service;

import org.springframework.stereotype.Service;

import com.ssafy.theme.editor.dto.EditorDto;

@Service
public class EditorServiceImpl implements EditorService {

	@Override
	public EditorDto login(EditorDto editorDto) throws Exception {
		return null;
	}

	@Override
	public EditorDto editorInfo(String id) throws Exception {
		return null;
	}

	@Override
	public void saveRefreshToken(String id, String token) throws Exception {
		
	}

	@Override
	public Object getRefreshToken(String id) throws Exception {
		return null;
	}

	@Override
	public void deleteRefreshToken(String id) throws Exception {
		
	}

}
