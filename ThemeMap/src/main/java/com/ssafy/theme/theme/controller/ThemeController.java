package com.ssafy.theme.theme.controller;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.theme.theme.dto.ThemeDto;
import com.ssafy.theme.theme.service.ThemeService;

@RestController
@CrossOrigin("*")
@RequestMapping("/theme")
public class ThemeController {

	private ThemeService themeService;
	
	@Autowired
	public ThemeController(ThemeService themeService) {
		this.themeService = themeService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createTheme(@RequestBody ThemeDto themeDto) {
		try {
			themeService.createTheme(themeDto);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/hot")
	public ResponseEntity<?> hotTheme() {
		try {
			List<ThemeDto> themes = themeService.hotTheme();
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(themes);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// place가 등록되어 있는 theme를 검색
	@GetMapping("/place/{placeId}")
	public ResponseEntity<?> themesOfPlace(@PathVariable("placeId") String placeId) {
		try {
			List<ThemeDto> themes = themeService.themesOfPlace(placeId);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(themes);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// editor가 등록한 theme를 검색
	@GetMapping("/editor/{editorId}")
	public ResponseEntity<?> themesOfEditor(@PathVariable("editorId") String editorId) {
		try {
			List<ThemeDto> themes = themeService.themesOfEditor(editorId);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(themes);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
