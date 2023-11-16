package com.ssafy.theme.place.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.theme.place.dto.PlaceDto;

@Mapper
public interface PlaceMapper {

	void createPlace(PlaceDto placeDto) throws Exception;

	List<PlaceDto> hotPlace() throws Exception;

	List<PlaceDto> placesOfTheme(String themeId) throws Exception;

}
