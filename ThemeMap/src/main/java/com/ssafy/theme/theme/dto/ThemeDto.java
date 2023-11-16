package com.ssafy.theme.theme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDto {
	
	private String themeId;
	private String themeName;
	private String description;
	private String editorId;
	private String type;
	private String visible;
	private String likeSum;

}
