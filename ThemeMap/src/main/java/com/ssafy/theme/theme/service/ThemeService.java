package com.ssafy.theme.theme.service;

import java.util.List;

import com.ssafy.theme.theme.dto.TagDto;
import com.ssafy.theme.theme.dto.ThemeDto;

public interface ThemeService {

	String createTheme(ThemeDto themeDto) throws Exception;

	List<ThemeDto> hotTheme() throws Exception;

	List<ThemeDto> themesOfPlace(String placeId) throws Exception;

	List<ThemeDto> themesOfEditor(String editorId) throws Exception;

	List<ThemeDto> visibleThemesOfEditor(String editorId) throws Exception;
	
	List<ThemeDto> themesOfLike(String editorId) throws Exception;

	void updateTheme(ThemeDto themeDto) throws Exception;

	void deleteTheme(String themeId) throws Exception;

	List<ThemeDto> themesOfTag(List<TagDto> tags) throws Exception;

	List<ThemeDto> allThemes() throws Exception;

	List<TagDto> allTags() throws Exception;

	ThemeDto getTheme(String themeId) throws Exception;

	int didLike(String editorId, String themeId) throws Exception;

	void postLike(String editorId, String themeId) throws Exception;
	
	void increaseThemeLike(String themeId) throws Exception;
	
	void increaseEditorLike(String editorId) throws Exception;

	void disLike(String editorId, String themeId) throws Exception;
	
	void decreaseThemeLike(String themeId) throws Exception;
	
	void decreaseEditorLike(String editorId) throws Exception;

	String findEditor(String themeId) throws Exception;

	List<TagDto> tagsOfTheme(String themeId) throws Exception;

	void deleteTags(String themeId) throws Exception;

	void insertTags(String themeId, List<TagDto> tags) throws Exception;

}
