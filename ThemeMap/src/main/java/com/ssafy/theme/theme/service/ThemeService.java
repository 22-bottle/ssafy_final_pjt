package com.ssafy.theme.theme.service;

import java.util.List;

import com.ssafy.theme.theme.dto.ThemeDto;

public interface ThemeService {

	void createTheme(ThemeDto themeDto) throws Exception;

	List<ThemeDto> hotTheme() throws Exception;

	List<ThemeDto> themesOfPlace(String placeId) throws Exception;

	List<ThemeDto> themesOfEditor(String editorId) throws Exception;

}
