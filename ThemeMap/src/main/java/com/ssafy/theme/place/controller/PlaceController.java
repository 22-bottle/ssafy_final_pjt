package com.ssafy.theme.place.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.theme.place.dto.PlaceDto;
import com.ssafy.theme.place.service.PlaceService;

@RestController
@CrossOrigin("*")
@RequestMapping("/place")
public class PlaceController {

	private PlaceService placeService;
	
	@Autowired
	public PlaceController(PlaceService placeService) {
		this.placeService = placeService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createPlace(@RequestBody PlaceDto placeDto) {
		try {
			placeService.createPlace(placeDto);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/hot")
	public ResponseEntity<?> hotPlace() {
		try {
			List<PlaceDto> places = placeService.hotPlace();
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(places);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// theme에 속해 있는 place를 검색
	@GetMapping("/theme/{themeId}")
	public ResponseEntity<?> placesOfTheme(@PathVariable("themeId") String themeId) {
		try {
			List<PlaceDto> places = placeService.placesOfTheme(themeId);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(places);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}