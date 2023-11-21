package com.ssafy.theme.theme.controller;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.theme.theme.dto.LikeDto;
import com.ssafy.theme.theme.dto.TagDto;
import com.ssafy.theme.theme.dto.TagListDto;
import com.ssafy.theme.theme.dto.ThemeDto;
import com.ssafy.theme.theme.service.ThemeService;

@RestController
@CrossOrigin("*")
@RequestMapping("/theme")
public class ThemeController {

	private ThemeService themeService;

	@Autowired
	public ThemeController(ThemeService themeService) {
		this.themeService = themeService;
	}

	@PostMapping("/create")
	public ResponseEntity<?> createTheme(@RequestBody ThemeDto themeDto) {
		try {
			String themeId = themeService.createTheme(themeDto);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(themeId);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/hot")
	public ResponseEntity<?> hotTheme() {
		try {
			List<ThemeDto> themes = themeService.hotTheme();
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(themes);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// place가 속해 있는 theme를 검색
	@GetMapping("/place/{placeId}")
	public ResponseEntity<?> themesOfPlace(@PathVariable("placeId") String placeId) {
		try {
			List<ThemeDto> themes = themeService.themesOfPlace(placeId);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(themes);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// editor가 만든 theme를 검색
	@GetMapping("/editor/{editorId}")
	public ResponseEntity<?> themesOfEditor(@PathVariable("editorId") String editorId) {
		try {
			List<ThemeDto> themes = themeService.themesOfEditor(editorId);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(themes);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// editor가 좋아요 누른 theme를 검색
	@GetMapping("/like/{editorId}")
	public ResponseEntity<?> themesOfLike(@PathVariable("editorId") String editorId) {
		try {
			List<ThemeDto> themes = themeService.themesOfLike(editorId);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(themes);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateTheme(@RequestBody ThemeDto themeDto) {
		try {
			themeService.updateTheme(themeDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@DeleteMapping("/delete/{themeId}")
	public ResponseEntity<?> deleteTheme(@PathVariable("themeId") String themeId) {
		try {
			themeService.deleteTheme(themeId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	// tag 리스트를 입력받아서 해당하는 theme 검색
	// {
	// "tags": [{"tagId":"1", "tagName":"tag1"}, {"tagId":"2", "tagName":"tag2"}]
	// }
	@PostMapping("/tag")
	public ResponseEntity<?> themesOfTag(@RequestBody TagListDto tagListDto) {
		try {
			List<ThemeDto> themes = themeService.themesOfTag(tagListDto.getTags());
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(themes);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> allThemes() {
		try {
			List<ThemeDto> themes = themeService.allThemes();
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(themes);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/allTags")
	public ResponseEntity<?> allTags() {
		try {
			List<TagDto> tags = themeService.allTags(); 
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(tags);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/get/{themeId}")
	public ResponseEntity<?> getTheme(@PathVariable("themeId") String themeId) {
		try {
			ThemeDto theme = themeService.getTheme(themeId);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(theme);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/didLike/{editorId}/{themeId}")
	public ResponseEntity<?> didLike(@PathVariable("editorId") String editorId, @PathVariable("themeId") String themeId) {
		try {
			boolean did = themeService.didLike(editorId, themeId) == 1 ? true : false;
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(did);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Transactional
	@PostMapping("/postLike")
	public ResponseEntity<?> postLike(@RequestBody LikeDto likeDto) {
		System.out.println(likeDto);
		try {
			// like_theme에 좋아요 등록
			themeService.postLike(likeDto.getEditorId(), likeDto.getThemeId());
			// theme에 like_sum 1 증가
			themeService.increaseThemeLike(likeDto.getThemeId());
			// editor에 like_sum 1 증가
			themeService.increaseEditorLike(themeService.findEditor(likeDto.getThemeId()));

			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Transactional
	@PostMapping("/disLike")
	public ResponseEntity<?> disLike(@RequestBody LikeDto likeDto) {
		try {
			// like_theme에 좋아요 삭제
			themeService.disLike(likeDto.getEditorId(), likeDto.getThemeId());
			// theme에 like_sum 1 감소
			themeService.decreaseThemeLike(likeDto.getThemeId());
			// editor에 like_sum 1 감소
			themeService.decreaseEditorLike(themeService.findEditor(likeDto.getThemeId()));
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
