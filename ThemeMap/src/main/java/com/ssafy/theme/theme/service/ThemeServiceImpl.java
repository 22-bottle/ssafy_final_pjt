package com.ssafy.theme.theme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.theme.theme.dto.TagDto;
import com.ssafy.theme.theme.dto.ThemeDto;
import com.ssafy.theme.theme.mapper.ThemeMapper;

@Service
public class ThemeServiceImpl implements ThemeService {

	private ThemeMapper themeMapper;

	@Autowired
	public ThemeServiceImpl(ThemeMapper themMapper) {
		this.themeMapper = themMapper;
	}

	@Override
	public String createTheme(ThemeDto themeDto) throws Exception {
		themeMapper.createTheme(themeDto);
		return themeMapper.returnTheme();
	}

	@Override
	public List<ThemeDto> hotTheme() throws Exception {
		return themeMapper.hotTheme();
	}

	@Override
	public List<ThemeDto> themesOfPlace(String placeId) throws Exception {
		return themeMapper.themesOfPlace(placeId);
	}

	@Override
	public List<ThemeDto> themesOfEditor(String editorId) throws Exception {
		return themeMapper.themesOfEditor(editorId);
	}

	@Override
	public List<ThemeDto> visibleThemesOfEditor(String editorId) throws Exception {
		return themeMapper.visibleThemesOfEditor(editorId);
	}

	@Override
	public List<ThemeDto> themesOfLike(String editorId) throws Exception {
		return themeMapper.themesOfLike(editorId);
	}

	@Override
	public void updateTheme(ThemeDto themeDto) throws Exception {
		themeMapper.updateTheme(themeDto);
	}

	@Override
	public void deleteTheme(String themeId) throws Exception {
		themeMapper.deleteTheme(themeId);
	}

	@Override
	public List<ThemeDto> themesOfTag(List<TagDto> tags) throws Exception {
		return themeMapper.themesOfTag(tags);
	}

	@Override
	public List<ThemeDto> allThemes() throws Exception {
		return themeMapper.allThemes();
	}

	@Override
	public List<TagDto> allTags() throws Exception {
		return themeMapper.allTags();
	}

	@Override
	public ThemeDto getTheme(String themeId) throws Exception {
		return themeMapper.getTheme(themeId);
	}

	@Override
	public int didLike(String editorId, String themeId) throws Exception {
		return themeMapper.didLike(editorId, themeId);
	}

	@Override
	public void postLike(String editorId, String themeId) throws Exception {
		themeMapper.postLike(editorId, themeId);
	}
	
	@Override
	public void increaseThemeLike(String themeId) throws Exception {
		themeMapper.increaseThemeLike(themeId);
	}

	@Override
	public void increaseEditorLike(String editorId) throws Exception {
		themeMapper.increaseEditorLike(editorId);
	}
	
	@Override
	public void disLike(String editorId, String themeId) throws Exception {
		themeMapper.disLike(editorId, themeId);
	}

	@Override
	public void decreaseThemeLike(String themeId) throws Exception {
		themeMapper.decreaseThemeLike(themeId);
	}

	@Override
	public void decreaseEditorLike(String editorId) throws Exception {
		themeMapper.decreaseEditorLike(editorId);
	}

	@Override
	public String findEditor(String themeId) throws Exception {
		return themeMapper.findEditor(themeId);
	}

}
