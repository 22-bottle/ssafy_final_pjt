package com.ssafy.theme.theme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
}
