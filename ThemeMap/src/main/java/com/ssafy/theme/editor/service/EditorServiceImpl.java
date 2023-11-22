package com.ssafy.theme.editor.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.theme.editor.dto.EditorDto;
import com.ssafy.theme.editor.mapper.EditorMapper;
import com.ssafy.theme.util.Encrypt;

@Service
public class EditorServiceImpl implements EditorService {

	private EditorMapper editorMapper;
	private Encrypt encrypt;
	
	@Autowired
	public EditorServiceImpl(EditorMapper editorMapper, Encrypt encrypt) {
		this.editorMapper = editorMapper;
		this.encrypt = encrypt;
	}
	
	@Override
	public EditorDto login(EditorDto editorDto) throws Exception {
		// 암호화로 확인
		String userPw = editorDto.getPw();
		editorDto = editorMapper.login(editorDto);

		return editorDto.getPw().equals(encrypt.getEncrypt(userPw, editorDto.getSalt())) ? editorDto : null;
	}

	@Override
	public EditorDto editorInfo(String id) throws Exception {
		return editorMapper.editorInfo(id);
	}

	@Override
	public EditorDto editorName(String id) throws SQLException {
		return editorMapper.editorName(id);
	}

	@Override
	public int regist(EditorDto editorDto) {
		String salt = encrypt.getSalt();
		editorDto.setPw(encrypt.getEncrypt(editorDto.getPw(), salt));
		editorDto.setSalt(salt);
		
		return editorMapper.regist(editorDto);
	}
	
	@Override
	public void saveRefreshToken(String id, String token) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("token", token);
		editorMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String id) throws Exception {
		return editorMapper.getRefreshToken(id);
	}

	@Override
	public void deleteRefreshToken(String id) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("token", null);
		editorMapper.deleteRefreshToken(map);
	}

	@Override
	public int modify(EditorDto editorDto) {
		String salt = getSalt(editorDto.getId());
		editorDto.setPw(encrypt.getEncrypt(editorDto.getPw(), salt));
		return editorMapper.modify(editorDto);
	}

	@Override
	public int resign(String id) {
		return editorMapper.resign(id);
	}

	@Override
	public String getSalt(String id) {
		return editorMapper.getSalt(id);
	}

	@Override
	public List<EditorDto> power() throws Exception {
		return editorMapper.power();
	}

}
