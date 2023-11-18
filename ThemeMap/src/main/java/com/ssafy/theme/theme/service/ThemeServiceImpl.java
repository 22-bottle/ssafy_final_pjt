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
	public void createTheme(ThemeDto themeDto) throws Exception {
		themeMapper.createTheme(themeDto);
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

}
