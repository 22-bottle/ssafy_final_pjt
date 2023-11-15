package com.ssafy.theme.theme.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.theme.theme.dto.ThemeDto;

@Mapper
public interface ThemeMapper {

	void createTheme(ThemeDto themeDto) throws Exception;

	List<ThemeDto> hotTheme() throws Exception;

	List<ThemeDto> themesOfPlace(String placeId) throws Exception;

	List<ThemeDto> themesOfEditor(String editorId) throws Exception;

}
