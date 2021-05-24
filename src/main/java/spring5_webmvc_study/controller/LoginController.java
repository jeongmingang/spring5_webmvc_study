package spring5_webmvc_study.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import spring5_webmvc_study.spring.AuthInfo;
import spring5_webmvc_study.spring.AuthService;
import spring5_webmvc_study.spring.LoginCommand;
import spring5_webmvc_study.spring.WrongIdPasswordException;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private AuthService authService;
	
	// 쿠키이름을 지정하여 이름이 "REMEMBER"인 쿠키를 Cookie 타입으로 전달 받고 지정한 이름을 가진 쿠키가 존재하지 않는다면 required 속성값을 false로 지정
	@GetMapping
	public String form(LoginCommand loginCommand, @CookieValue(value="REMEMBER", required = false) Cookie rCookie) {
		if (rCookie != null) {
			loginCommand.setEmail(rCookie.getValue());
			loginCommand.setRememberEmail(true);
		}
		return "/login/loginForm";
	}
	
	// 쿠키값으로 이메일 주소를 저장할 때 그대로 저장했으나 이메일 주소는 민감한 개인 정보이므로 실제 서비스에서는 암호화해서 보안을 높여야 한다.
	@PostMapping
	public String submit(LoginCommand loginCommand, Errors errors, HttpSession session, HttpServletResponse response) {
		System.out.println(loginCommand);
		new LoginCommandValidator().validate(loginCommand, errors);
		if (errors.hasErrors())
			return "/login/loginForm";
		try {	
			AuthInfo authInfo = authService.authenicate(loginCommand.getEmail(), loginCommand.getPassword());
			session.setAttribute("authInfo", authInfo);	// 세션에 authInfo 저장해야 함
			
			Cookie rememCookie = new Cookie("REMEMBER", loginCommand.getEmail());
			rememCookie.setPath("/");
			if(loginCommand.isRememberEmail()) {
				rememCookie.setMaxAge(60 * 60 * 24 * 30);	// 60초(1분) * 60 = 1시간 * 24 = 24시간 * 30 = 30일동안 유지
			} else {
				rememCookie.setMaxAge(0);
			}
			response.addCookie(rememCookie);
			
			return "login/loginSuccess";
		}catch (WrongIdPasswordException ex) {
			errors.reject("idPasswordNotMatching");
			return "login/loginForm";
		}
	}
}	
