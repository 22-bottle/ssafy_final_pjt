package com.ssafy.theme.editor.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.theme.editor.dto.EditorDto;
import com.ssafy.theme.editor.service.EditorService;
import com.ssafy.theme.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
@RequestMapping("/editor")
@Slf4j
public class EditorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EditorService service;
	private JWTUtil jwtUtil;

	@Autowired
	public EditorController(EditorService service, JWTUtil jwtUtil) {
		super();
		this.service = service;
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody EditorDto editorDto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			EditorDto editor = service.login(editorDto);
			if(editor != null) {
				String accessToken = jwtUtil.createAccessToken(editor.getId());
				String refreshToken = jwtUtil.createRefreshToken(editor.getId());
				
				service.saveRefreshToken(editor.getId(), refreshToken);
				
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				
				status = HttpStatus.CREATED;
			} else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/info/{id}")
	public ResponseEntity<Map<String, Object>> getEditorInfo(@PathVariable("id") String id, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			try {
				EditorDto editorDto = service.editorInfo(id);
				resultMap.put("editorInfo", editorDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				log.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			log.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody EditorDto editorDto) throws IOException {
    	Map<String, Object> resultMap = new HashMap<>();
    	HttpStatus status = HttpStatus.OK;
    	try {
    		service.regist(editorDto);
    		resultMap.put("message", "회원가입 성공!!");
    		status = HttpStatus.OK;
    	} catch (Exception e) {
    		resultMap.put("message", e.getMessage());
    		status = HttpStatus.CONFLICT;
		}
    	return new ResponseEntity<Map<String, Object>>(resultMap, status);      
    }
	
	@GetMapping("/logout/{id}")
	public ResponseEntity<?> removeToken(@PathVariable("id") String id) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			service.deleteRefreshToken(id);
			status = HttpStatus.OK;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
    @PatchMapping("/modify")
    public ResponseEntity<?> modify(@RequestBody EditorDto editorDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
    	try {
	        int r = service.modify(editorDto);
	        status = r > 0 ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
	        resultMap.put("message", r > 0 ? "회원정보 수정 성공!!" : "회원정보 수정 실패..");
    	} catch (Exception e) {
    		status = HttpStatus.INTERNAL_SERVER_ERROR;
    		resultMap.put("message", "회원정보 수정 오류..");
    	}
    	return new ResponseEntity<Map<String, Object>>(resultMap, status);  
    }
    
    @DeleteMapping("/resign")
    public ResponseEntity<?> resign(@RequestBody String id) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
    	try {
    		int r = service.resign(id);
    		status = r > 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
	        resultMap.put("message", r > 0 ? "회원 탈퇴 성공!!" : "회원 탈퇴 실패..");
    	} catch (Exception e) {
    		status = HttpStatus.INTERNAL_SERVER_ERROR;
    		resultMap.put("message", "서버 오류..");
    	}
    	return new ResponseEntity<Map<String, Object>>(resultMap, status);  
    }
	
}
