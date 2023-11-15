package com.ssafy.theme.editor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditorDto {
	private String id;
	private String pw;
	private String salt;
	private String editorName;
	private String likeSum;
	private String joinDate;
	private String token;
	
}
