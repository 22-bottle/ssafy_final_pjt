package com.ssafy.theme.place.service;

import java.util.List;

import com.ssafy.theme.place.dto.LinkDto;
import com.ssafy.theme.place.dto.PlaceDto;

public interface PlaceService {

	void createPlace(PlaceDto placeDto) throws Exception;

	List<PlaceDto> hotPlace() throws Exception;

	List<PlaceDto> placesOfTheme(String themeId) throws Exception;

	void linkPlace(LinkDto linkDto) throws Exception;
	
	void keepScore(String placeId, String score) throws Exception;

	int isThere(String placeId) throws Exception;

	int isInTheme(String themeId, String placeId) throws Exception;

}
