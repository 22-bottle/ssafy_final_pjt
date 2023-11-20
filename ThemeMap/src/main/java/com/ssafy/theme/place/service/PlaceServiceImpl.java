package com.ssafy.theme.place.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.theme.place.dto.PlaceDto;
import com.ssafy.theme.place.mapper.PlaceMapper;

@Service
public class PlaceServiceImpl implements PlaceService {

	private PlaceMapper placeMapper;
	
	@Autowired
	public PlaceServiceImpl(PlaceMapper placeMapper) {
		this.placeMapper =  placeMapper;
	}

	@Override
	public void createPlace(PlaceDto placeDto) throws Exception {
		placeMapper.createPlace(placeDto);
	}

	@Override
	public List<PlaceDto> hotPlace() throws Exception {
		return placeMapper.hotPlace();
	}

	@Override
	public List<PlaceDto> placesOfTheme(String themeId) throws Exception {
		return placeMapper.placesOfTheme(themeId);
	}

	@Override
	public void keepScore(String placeId, String score) throws Exception {
		placeMapper.keepScore(placeId, score);
	}
	
}
