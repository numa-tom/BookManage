package com.example.demo.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/*
 * コントローラーの共通処理の記述
 */
@ControllerAdvice
public class WebMvcControllerAdvice {
	
	/*
	 * 空文字をnullに変換
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

}
