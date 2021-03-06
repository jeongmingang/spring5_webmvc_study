package spring5_webmvc_study.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import spring5_webmvc_study.spring.LoginCommand;
import spring5_webmvc_study.spring.RegisterRequest;

public class LoginCommandValidator implements Validator{	// 폼에 입력된 값이 올바른지 검사
	private static final String emailRegExp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern pattern;
	
	public LoginCommandValidator() {
		this.pattern = Pattern.compile(emailRegExp);
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterRequest.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
		LoginCommand loc = (LoginCommand) target;
		Matcher matcher = pattern.matcher(loc.getEmail());
		if (!matcher.matches()) {
			errors.rejectValue("email", "bad");
		}
		ValidationUtils.rejectIfEmpty(errors, "password", "required");
	}

}

