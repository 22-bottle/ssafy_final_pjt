package com.ssafy.theme.place.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
}
