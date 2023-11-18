package com.ssafy.theme.theme.service;

import java.util.List;

import com.ssafy.theme.theme.dto.TagDto;
import com.ssafy.theme.theme.dto.ThemeDto;

public interface ThemeService {

	void createTheme(ThemeDto themeDto) throws Exception;

	List<ThemeDto> hotTheme() throws Exception;

	List<ThemeDto> themesOfPlace(String placeId) throws Exception;

	List<ThemeDto> themesOfEditor(String editorId) throws Exception;

	List<ThemeDto> themesOfLike(String editorId) throws Exception;

	void updateTheme(ThemeDto themeDto) throws Exception;

	void deleteTheme(String themeId) throws Exception;

	List<ThemeDto> themesOfTag(List<TagDto> tags) throws Exception;

	List<ThemeDto> allThemes() throws Exception;

	List<TagDto> allTags() throws Exception;

}
