package com.example.demo.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/*
 * �R���g���[���[�̋��ʏ����̋L�q
 */
@ControllerAdvice
public class WebMvcControllerAdvice {
	
	/*
	 * �󕶎���null�ɕϊ�
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

}
